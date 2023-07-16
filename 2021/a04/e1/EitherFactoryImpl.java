package a04.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class EitherFactoryImpl implements EitherFactory {

    @Override
    public <A, B> Either<A, B> success(B b) {
        return new Either<A, B>() {

            @Override
            public boolean isFailure() {
                return false;
            }

            @Override
            public boolean isSuccess() {
                return true;
            }

            @Override
            public Optional<A> getFailure() {
                return Optional.empty();
            }

            @Override
            public Optional<B> getSuccess() {
                return Optional.of(b);
            }

            @Override
            public B orElse(B other) {
                return Optional.of(b).isPresent() ? b : other;
            }

            @Override
            public <B1> Either<A, B1> map(Function<B, B1> function) {
                return success(function.apply(b));
            }

            @Override
            public <B1> Either<A, B1> flatMap(Function<B, Either<A, B1>> function) {
                return function.apply(b);
            }

            @Override
            public <A1> Either<A1, B> filterOrElse(Predicate<B> predicate, A1 failure) {
                return predicate.test(b) ? success(b) : failure(failure);
            }

            @Override
            public <C> C fold(Function<A, C> funFailure, Function<B, C> funSuccess) {
                return funSuccess.apply(b);
            }

        };
    }

    @Override
    public <A, B> Either<A, B> failure(A a) {
        return new Either<A, B>() {

            @Override
            public boolean isFailure() {
                return true;
            }

            @Override
            public boolean isSuccess() {
                return false;
            }

            @Override
            public Optional<A> getFailure() {
                return Optional.of(a);
            }

            @Override
            public Optional<B> getSuccess() {
                return Optional.empty();
            }

            @Override
            public B orElse(B other) {
                return other;
            }

            @Override
            public <B1> Either<A, B1> map(Function<B, B1> function) {
                return failure(a);
            }

            @Override
            public <B1> Either<A, B1> flatMap(Function<B, Either<A, B1>> function) {
                return failure(a);
            }

            @Override
            public <A1> Either<A1, B> filterOrElse(Predicate<B> predicate, A1 failure) {
                return failure(failure);
            }

            @Override
            public <C> C fold(Function<A, C> funFailure, Function<B, C> funSuccess) {
                return funFailure.apply(a);
            }

        };
    }

    @Override
    public <A> Either<Exception, A> of(Supplier<A> computation) {
        try {
            return success(computation.get());
        } catch (Exception e) {
            return failure(e);
        }
    }

    @Override
    public <A, B, C> Either<A, List<C>> traverse(List<B> list, Function<B, Either<A, C>> function) {
        final var it = list.iterator();
        final var successList = new ArrayList<C>();
        while (it.hasNext()) {
            final var res = function.apply(it.next());
            if (res.isSuccess()) {
                successList.add(res.getSuccess().get());
            } else {
                return failure(res.getFailure().get());
            }
        }
        return success(successList);
    }

}
