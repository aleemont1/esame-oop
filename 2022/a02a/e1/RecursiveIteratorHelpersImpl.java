package a02a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.InitialContext;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers {

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        return (list == null || list.isEmpty())
                ? null
                : new RecursiveIterator<X>() {
                    private int count = 0;

                    @Override
                    public X getElement() {
                        final X element = list.get(count);
                        return element != null ? element : null;
                    }

                    @Override
                    public RecursiveIterator<X> next() {
                        return (++count < list.size() && list.iterator().hasNext()) ? this : null;
                    }

                };
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {

        final List<X> outList = new ArrayList<>();
        outList.add(input.getElement());
        for (int i = 1; i < max && input.next() != null; i++) {
            outList.add(input.getElement());
        }
        return outList;

    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        return new RecursiveIterator<Pair<X, Y>>() {

            @Override
            public Pair<X, Y> getElement() {
                final var fe = first.getElement();
                final var se = second.getElement();

                return (fe != null && se != null) ? new Pair<X, Y>(fe, se) : null;
            }

            @Override
            public RecursiveIterator<Pair<X, Y>> next() {
                final var fi = first.next();
                final var si = second.next();
                return (fi != null && si != null) ? zip(fi,si) : null;
            }

        };
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return new RecursiveIterator<Pair<X,Integer>>() {
            private static int count = 0;
            @Override
            public Pair<X, Integer> getElement() {
                final var e = iterator.getElement();
                return e != null ? new Pair<X,Integer>(e, count) : null;
            }

            @Override
            public RecursiveIterator<Pair<X, Integer>> next() {
                ++count;
                final var next = iterator.next();
                return (next != null) ? zipWithIndex(next) : null;
            }
        };
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alternate'");
    }

}
