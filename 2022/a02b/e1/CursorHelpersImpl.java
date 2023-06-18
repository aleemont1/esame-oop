package a02b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CursorHelpersImpl implements CursorHelpers {

    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return list == null ? null : new Cursor<X>() {
            private int count = 0;

            @Override
            public X getElement() {
                return (count < list.size()) ? list.get(count) : list.get(count - 1);
            }

            @Override
            public boolean advance() {
                return ++count < list.size() ? true : false;
            }

        };
    }

    @Override
    public Cursor<Integer> naturals() {
        return new Cursor<Integer>() {
            private int number;

            @Override
            public Integer getElement() {
                return number;
            }

            @Override
            public boolean advance() {
                return number++ < Integer.MAX_VALUE;
            }

        };
    }

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        return input == null || max <= 0
                ? null
                : new Cursor<X>() {
                    private int count = 0;

                    @Override
                    public X getElement() {
                        final X element = input.getElement();
                        return count < max && element != null ? element : null;
                    }

                    @Override
                    public boolean advance() {
                        final var next = input.advance();
                        return ++count < max && next;
                    }

                };
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        consumer.accept(input.getElement());
        while (input.advance())
            consumer.accept(input.getElement());
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        final List<X> outList = new ArrayList<>();
        outList.add(input.getElement());
        int count = 1;
        while (input.advance() && count++ < max) {
            outList.add(input.getElement());
        }
        return outList;

    }

}
