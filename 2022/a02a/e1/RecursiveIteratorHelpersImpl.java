package a02a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers {

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        if (list.isEmpty())
            return null;

        return new RecursiveIterator<X>() {

            private final Iterator<X> it = list.iterator();
            private X element = it.next();

            @Override
            public X getElement() {
                return element;
            }

            @Override
            public RecursiveIterator<X> next() {
                if (this.it.hasNext()) {
                    this.element = it.next();
                    return this;
                }
                return null;
            }

        };
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        final List<X> outList = new ArrayList<>();
        while (input != null && outList.size() < max) {
            outList.add(input.getElement());
            input = input.next();
        }
        return outList;
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        return new RecursiveIterator<Pair<X, Y>>() {

            @Override
            public Pair<X, Y> getElement() {
                return new Pair<X, Y>(first.getElement(), second.getElement());
            }

            @Override
            public RecursiveIterator<Pair<X, Y>> next() {
                return first.next() != null &&
                        second.next() != null ? this : null;
            }

        };
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return new RecursiveIterator<Pair<X, Integer>>() {
            private int index = 0;

            @Override
            public Pair<X, Integer> getElement() {
                return new Pair<>(iterator.getElement(), index);
            }

            @Override
            public RecursiveIterator<Pair<X, Integer>> next() {
                if (iterator.next() != null) {
                    index++;
                    return this;
                }
                return null;
            }

        };
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alternate'");
    }

}
