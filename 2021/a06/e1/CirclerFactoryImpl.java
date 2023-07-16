package a06.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CirclerFactoryImpl implements CirclerFactory {

    @Override
    public <T> Circler<T> leftToRight() {
        return new Circler<T>() {
            private List<T> source = new ArrayList<>();
            private Iterator<T> it = source.iterator();

            @Override
            public void setSource(List<T> elements) {
                this.source = new ArrayList<>(elements);
                this.it = source.iterator();
            }

            @Override
            public T produceOne() {
                if (!it.hasNext()) {
                    it = source.iterator();
                }
                return it.next();
            }

            @Override
            public List<T> produceMany(int n) {
                int i = 0;
                final List<T> outList = new ArrayList<>();
                while (i < n) {
                    if (!it.hasNext()) {
                        it = source.iterator();
                    }
                    outList.add(i++, it.next());
                }
                return outList;
            }

        };
    }

    @Override
    public <T> Circler<T> alternate() {
        return new Circler<T>() {
            private List<T> source = new ArrayList<>();
            private Iterator<T> it = source.iterator();

            @Override
            public void setSource(List<T> elements) {
                this.source = new ArrayList<>(elements);
                this.it = source.iterator();
            }

            @Override
            public T produceOne() {
                if (!it.hasNext()) {
                    Collections.reverse(source);
                    it = source.iterator();
                }
                return it.next();
            }

            @Override
            public List<T> produceMany(int n) {
                final List<T> outList = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    if (!it.hasNext()) {
                        Collections.reverse(source);
                        it = source.iterator();
                    }
                    outList.add(it.next());
                }
                return outList;
            }

        };
    }

    @Override
    public <T> Circler<T> stayToLast() {
        return new Circler<T>() {
            private List<T> source = new ArrayList<>();
            private Iterator<T> it = source.iterator();

            @Override
            public void setSource(List<T> elements) {
                this.source = new ArrayList<>(elements);
                this.it = source.iterator();
            }

            @Override
            public T produceOne() {
                return it.hasNext() ? it.next() : source.get(source.size() - 1);
            }

            @Override
            public List<T> produceMany(int n) {
                final List<T> outList = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    if (!it.hasNext()) {
                        outList.add(source.get(source.size() - 1));
                    } else {
                        outList.add(it.next());
                    }
                }
                return outList;
            }
        };
    }

    @Override
    public <T> Circler<T> leftToRightSkipOne() {
        return new Circler<T>() {
            private List<T> source = new ArrayList<>();
            private Iterator<T> it = source.iterator();

            @Override
            public void setSource(List<T> elements) {
                this.source = new ArrayList<>(elements);
                this.it = source.iterator();
            }

            @Override
            public T produceOne() {
                T e = null;
                if (it.hasNext()) {
                    e = it.next();
                }
                try {
                    it.next();
                } catch(Exception ex) {
                    it = source.iterator();
                }
                return e;
            }

            @Override
            public List<T> produceMany(int n) {
                int i = 0;
                final List<T> outList = new ArrayList<>();
                while (i < n) {
                    try {
                        outList.add(i++, produceOne());
                    } catch (Exception e) {
                        it = source.iterator();
                    }
                }
                return outList;
            }

        };
    }

    @Override
    public <T> Circler<T> alternateSkipOne() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alternateSkipOne'");
    }

    @Override
    public <T> Circler<T> stayToLastSkipOne() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stayToLastSkipOne'");
    }

}
