package a03a.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DecisionChainFactoryImpl implements DecisionChainFactory {

    @Override
    public <A, B> DecisionChain<A, B> oneResult(B b) {
        return new DecisionChain<A, B>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.of(b);
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return this;
            }

        };
    }

    @Override
    public <A, B> DecisionChain<A, B> simpleTwoWay(Predicate<A> predicate, B positive, B negative) {
        return twoWay(predicate, oneResult(positive), oneResult(negative));
    }

    @Override
    public <A, B> DecisionChain<A, B> enumerationLike(List<Pair<A, B>> mapList, B defaultReply) {
        return switchChain(mapList.stream().map(e -> new Pair<Predicate<A>, B>(a -> a.equals(e.get1()), e.get2()))
                .collect(Collectors.toList()), defaultReply);
    }

    @Override
    public <A, B> DecisionChain<A, B> twoWay(Predicate<A> predicate, DecisionChain<A, B> positive,
            DecisionChain<A, B> negative) {
        return new DecisionChain<A, B>() {

            @Override
            public Optional<B> result(A a) {
                return Optional.empty();
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return predicate.test(a) ? positive : negative;
            }
        };
    }

    @Override
    public <A, B> DecisionChain<A, B> switchChain(List<Pair<Predicate<A>, B>> cases, B defaultReply) {
        return new DecisionChain<A, B>() {

            @Override
            public Optional<B> result(A a) {
                if (cases.isEmpty()) {
                    return Optional.of(defaultReply);
                }
                if (cases.get(0).get1().test(a)) {
                    return Optional.of(cases.get(0).get2());
                }
                return Optional.empty();
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                var recursiveCases = new LinkedList<>(cases);
                recursiveCases.poll();
                return switchChain(recursiveCases, defaultReply);
            }

        };
    }

}
