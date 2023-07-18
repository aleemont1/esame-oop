package a02b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class CursorHelpersImpl implements CursorHelpers {

    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return new Cursor<X>() {
            private final Iterator<X> it = list.iterator();
            private X element = it.next();

            @Override
            public X getElement() {
                return this.element;
            }

            @Override
            public boolean advance() {
                if (this.it.hasNext()) {
                    element = it.next();
                    return true;
                }
                return false;
            }

        };
    }

    @Override
    public Cursor<Integer> naturals() {
        return new Cursor<Integer>() {
            private int n = 0;
            @Override
            public Integer getElement() {
                return this.n;
            }

            @Override
            public boolean advance() {
                n++;
                return true;
            }
            
        };
    }

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'take'");
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        consumer.accept(input.getElement());
        while (input.advance()) {
            consumer.accept(input.getElement());
        }
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        final List<X> outList = new ArrayList<>();
        while(outList.size() < max) {
            outList.add(input.getElement());
            if(!input.advance()) {
                break;
            }
        }
        return outList;
    }

}
