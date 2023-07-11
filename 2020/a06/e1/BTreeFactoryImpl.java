package a06.e1;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class BTreeFactoryImpl implements BTreeFactory {

    @Override
    public <L> BTree<L> leaf(L value) {
        return new BTree<L>() {

            @Override
            public boolean isLeaf() {
                return true;
            }

            @Override
            public L getLeaf() {
                return value;
            }

            @Override
            public BTree<L> getLeft() {
                throw new NoSuchElementException();
            }

            @Override
            public BTree<L> getRight() {
                throw new NoSuchElementException();
            }

            @Override
            public L compute(BinaryOperator<L> function) {
                return this.getLeaf();
            }

            @Override
            public <O> BTree<O> map(Function<L, O> mapper) {
                return leaf(mapper.apply(value));
            }

            @Override
            public BTree<L> symmetric() {
                return this;
            }

        };
    }

    @Override
    public <L> BTree<L> compose(BTree<L> left, BTree<L> right) {
        return new BTree<L>() {

            @Override
            public boolean isLeaf() {
                return false;
            }

            @Override
            public L getLeaf() {
                throw new NoSuchElementException();
            }

            @Override
            public BTree<L> getLeft() {
                return left;
            }

            @Override
            public BTree<L> getRight() {
                return right;
            }

            @Override
            public L compute(BinaryOperator<L> function) {
                return function.apply(left.compute(function), right.compute(function));
            }

            @Override
            public <O> BTree<O> map(Function<L, O> mapper) {
                return compose(left.map(mapper), right.map(mapper));
            }

            @Override
            public BTree<L> symmetric() {
                return compose(right.symmetric(), left.symmetric());
            }

        };
    }

    @Override
    public <L> BTree<L> nested(List<L> leafs) {
        var iterator = leafs.iterator();
        BTree<L> tree = leaf(iterator.next());
        while (iterator.hasNext()) {
            tree = compose(tree, leaf(iterator.next()));
        }
        return tree;
    }

}
