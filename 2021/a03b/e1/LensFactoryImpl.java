package a03b.e1;

import java.util.ArrayList;
import java.util.List;

public class LensFactoryImpl implements LensFactory {

    private final <E> List<E> copyAndReplace(E a, List<E> s, int i) {
        var outList = new ArrayList<>(s);
        outList.set(i, a);
        return outList;
    }

    @Override
    public <E> Lens<List<E>, E> indexer(int i) {
        return new Lens<List<E>, E>() {

            @Override
            public E get(List<E> s) {
                return s.get(i);
            }

            @Override
            public List<E> set(E a, List<E> s) {
                return copyAndReplace(a, s, i);
            }

        };
    }

    @Override
    public <E> Lens<List<List<E>>, E> doubleIndexer(int i, int j) {
        return new Lens<List<List<E>>, E>() {

            @Override
            public E get(List<List<E>> s) {
                return s.get(i).get(j);
            }

            @Override
            public List<List<E>> set(E a, List<List<E>> s) {
                return copyAndReplace(copyAndReplace(a, s.get(i), j), s, i);
            }

        };
    }

    @Override
    public <A, B> Lens<Pair<A, B>, A> left() {
        return new Lens<Pair<A, B>, A>() {

            @Override
            public A get(Pair<A, B> s) {
                return s.get1();
            }

            @Override
            public Pair<A, B> set(A a, Pair<A, B> s) {
                return new Pair<A, B>(a, s.get2());
            }

        };
    }

    @Override
    public <A, B> Lens<Pair<A, B>, B> right() {
        return new Lens<Pair<A, B>, B>() {
            @Override
            public B get(Pair<A, B> s) {
                return s.get2();
            }

            @Override
            public Pair<A, B> set(B b, Pair<A, B> s) {
                return new Pair<A, B>(s.get1(), b);
            }
        };
    }

    @Override
    public <A, B, C> Lens<List<Pair<A, Pair<B, C>>>, C> rightRightAtPos(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rightRightAtPos'");
    }

}
