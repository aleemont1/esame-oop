package a03b.e1;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LazyTreeFactoryImpl implements LazyTreeFactory {

    @Override
    public <X> LazyTree<X> constantInfinite(X value) {
        return fromTwoIterations(value, v -> v, v -> v);
    }

    @Override
    public <X> LazyTree<X> fromMap(X root, Map<X, Pair<X, X>> map) {
        final var p = map.get(root);
        return cons(Optional.of(root),
                () -> p == null ? empty() : fromMap(p.getX(), map),
                () -> p == null ? empty() : fromMap(p.getY(), map));
        /*
         * return new LazyTree<X>() {
         * 
         * @Override
         * public boolean hasRoot() {
         * return root != null;
         * }
         * 
         * @Override
         * public X root() {
         * return root;
         * }
         * 
         * private final Pair<X, X> p = map.get(root);
         * 
         * @Override
         * public LazyTree<X> left() {
         * 
         * return p != null ? fromMap(p.getX(), map) : empty();
         * }
         * 
         * @Override
         * public LazyTree<X> right() {
         * return p != null ? fromMap(p.getY(), map) : empty();
         * }
         * 
         * };
         */
    }

    private final <X> LazyTree<X> empty() {
        return cons(Optional.empty(), null, null);
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
                if (hasRoot())
                    return root.get();
                return null;
            }

            private LazyTree<X> fromSupp(final Supplier<LazyTree<X>> supp) {
                return hasRoot() ? supp.get() : null;
            }

            @Override
            public LazyTree<X> left() {
                return fromSupp(leftSupp);
            }

            @Override
            public LazyTree<X> right() {
                return fromSupp(rightSupp);
            }

        };
    }

    @Override
    public <X> LazyTree<X> fromTwoIterations(X root, UnaryOperator<X> leftOp, UnaryOperator<X> rightOp) {
        return cons(Optional.of(root),
                () -> fromTwoIterations(leftOp.apply(root), leftOp, rightOp),
                () -> fromTwoIterations(rightOp.apply(root), leftOp, rightOp));
        /*
         * return new LazyTree<X>() {
         * 
         * @Override
         * public boolean hasRoot() {
         * return root != null;
         * }
         * 
         * @Override
         * public X root() {
         * return root;
         * }
         * 
         * @Override
         * public LazyTree<X> left() {
         * return fromTwoIterations(leftOp.apply(root), leftOp, rightOp);
         * }
         * 
         * @Override
         * public LazyTree<X> right() {
         * return fromTwoIterations(rightOp.apply(root), leftOp, rightOp);
         * }
         * 
         * };
         */
    }

    @Override
    public <X> LazyTree<X> fromTreeWithBound(LazyTree<X> tree, int bound) {
        return bound == 0 || !tree.hasRoot() ? empty()
                : cons(Optional.of(tree.root()),
                        () -> fromTreeWithBound(tree.left(), bound - 1),
                        () -> fromTreeWithBound(tree.right(), bound - 1));
        /*
         * new LazyTree<X>() {
         * 
         *  @Override
         *  public boolean hasRoot() {
         *      return tree.hasRoot();
         *  }
         * 
         *  @Override
         *  public X root() {
         *      return tree.root();
         *  }
         * 
         * @Override
         *  public LazyTree<X> left() {
         *      return fromTreeWithBound(tree.left(), bound - 1);
         *  }
         * 
         *  @Override
         *  public LazyTree<X> right() {
         *      return fromTreeWithBound(tree.right(), bound - 1);
         *  }
         * 
         * };
         */

    }

}
