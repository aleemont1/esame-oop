package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AcceptorFactoryAdvancedImpl implements AcceptorFactory {

    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
        return generalised(0, (str, i) -> Optional.of(i + (str.isEmpty() ? 1 : 0)), Optional::of);
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return new Acceptor<Integer, String>() {
            private String string = "";
            private Optional<Integer> last = Optional.empty();

            @Override
            public boolean accept(Integer e) {
                if (this.string.length() > 0 && (this.last.isEmpty() || e <= this.last.get())) {
                    this.last = Optional.empty();
                    return false;
                }
                this.last = Optional.of(e);
                string = string + (string.length() > 0 ? ":" : "") + e;
                return true;
            }

            @Override
            public Optional<String> end() {
                return this.last.map(i -> string);
            }
        };
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return new Acceptor<Integer, Integer>() {
            private List<Integer> list = new ArrayList<>();

            @Override
            public boolean accept(Integer e) {
                if (list.size() > 3) {
                    return false;
                }
                list.add(e);
                return list.size() <= 3;
            }

            @Override
            public Optional<Integer> end() {
                return Optional.of(list).filter(l -> l.size() == 3)
                        .map(l -> l.stream().collect(Collectors.summingInt(i -> i)));
            }

        };
    }

    @Override
    public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(Acceptor<E, O1> a1, Acceptor<E, O2> a2) {
        return new Acceptor<E, Pair<O1, O2>>() {

            @Override
            public boolean accept(E e) {
                return a1.accept(e) && a2.accept(e);
            }

            @Override
            public Optional<Pair<O1, O2>> end() {
                return a1.end().flatMap(o1 -> a2.end().map(o2 -> new Pair<>(o1, o2)));
            }

        };
    }

    @Override
    public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun,
            Function<S, Optional<O>> outputFun) {
        return new Acceptor<E, O>() {
            private Optional<S> state = Optional.of(initial);

            @Override
            public boolean accept(E e) {
                if (state.isEmpty()) {
                    return false;
                }
                state = stateFun.apply(e, state.get());
                return state.isPresent();
            }

            @Override
            public Optional<O> end() {
                return state.isEmpty() ? Optional.empty() : outputFun.apply(state.get());
            }

        };
    }

}
