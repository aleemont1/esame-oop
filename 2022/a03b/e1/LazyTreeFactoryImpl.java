package a03b.e1;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LazyTreeFactoryImpl implements LazyTreeFactory {

    @Override
    public <X> LazyTree<X> constantInfinite(X value) {
        return fromTwoIterations(value, v -> value, v -> value);
    }

    private <X> LazyTree<X> empty() {
        return cons(Optional.empty(), null, null);
    }

    @Override
    public <X> LazyTree<X> fromMap(X root, Map<X, Pair<X, X>> map) {
        final Pair<X, X> p = map.get(root);
        return cons(Optional.of(root),
                () -> p == null ? empty() : fromMap(p.getX(), map),
                () -> p == null ? empty() : fromMap(p.getY(), map));
    }

    @Override
    public <X> LazyTree<X> cons(Optional<X> root, Supplier<LazyTree<X>> leftSupp, Supplier<LazyTree<X>> rightSupp) {
        return new LazyTree<X>() {

            @Override
            public boolean hasRoot() {
                return root.isPresent();
            }

            @Override
            public X root() {
                if (hasRoot()) {
                    return root.get();
                }
                return null;
            }

            @Override
            public LazyTree<X> left() {

                if (hasRoot()) {
                    return leftSupp.get();
                }
                throw new NoSuchElementException();
            }

            @Override
            public LazyTree<X> right() {
                if (hasRoot()) {
                    return rightSupp.get();
                }
                throw new NoSuchElementException();
            }

        };
    }

    @Override
    public <X> LazyTree<X> fromTwoIterations(X root, UnaryOperator<X> leftOp, UnaryOperator<X> rightOp) {
        return cons(Optional.of(root),
                () -> fromTwoIterations(leftOp.apply(root), leftOp, rightOp),
                () -> fromTwoIterations(rightOp.apply(root), leftOp, rightOp));
    }

    @Override
    public <X> LazyTree<X> fromTreeWithBound(LazyTree<X> tree, int bound) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromTreeWithBound'");
    }

}
