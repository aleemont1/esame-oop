package a05.e1;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class StateFactoryImpl implements StateFactory {

    @Override
    public <S, A> State<S, A> fromFunction(Function<S, Pair<S, A>> fun) {
        return new State<S, A>() {

            @Override
            public S nextState(S s) {
                return fun.apply(s).get1();
            }

            @Override
            public A value(S s) {
                return fun.apply(s).get2();
            }

            @Override
            public <B> State<S, B> map(Function<A, B> fun) {
                return fromFunction(s -> new Pair<>(nextState(s), fun.apply(value(s))));
            }

            @Override
            public Iterator<A> iterator(S s0) {
                return new Iterator<A>() {
                    private S val = s0;

                    @Override
                    public boolean hasNext() {
                        return nextState(val) != null;
                    }

                    @Override
                    public A next() {
                        A result = value(val);
                        val = nextState(val);
                        return result;
                    }

                };
            }

        };
    }

    @Override
    public <S, A, B> State<S, B> compose(State<S, A> state1, State<S, B> state2) {
        return fromFunction(s -> {
            var p1 = new Pair<>(state1.nextState(s), state1.value(s));
            var p2 = new Pair<>(state2.nextState(p1.get1()), state2.value(p1.get1()));
            return new Pair<>(p2.get1(), p2.get2());
        });
    }

    @Override
    public State<Integer, String> incSquareHalve() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'incSquareHalve'");
    }

    @Override
    public State<Integer, Integer> counterOp(CounterOp op) {
        return fromFunction(
                op == CounterOp.GET ? i -> new Pair<>(i, i)
                        : op == CounterOp.INC ? i -> new Pair<>(i + 1, null)
                                : op == CounterOp.RESET ? i -> new Pair<>(0, null) : null);
    }

}
