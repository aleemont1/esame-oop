package a01b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class EventSequenceProducerHelpersImpl implements EventSequenceProducerHelpers {

    @Override
    public <E> EventSequenceProducer<E> fromIterator(Iterator<Pair<Double, E>> iterator) {
        return () -> {
            return iterator.next();
        };
    }

    @Override
    public <E> List<E> window(EventSequenceProducer<E> sequence, double fromTime, double toTime) {
        final var outList = new ArrayList<E>();
        while (true) {
            try {
                final var n = sequence.getNext();
                if (n.get1() > fromTime && n.get1() < toTime)
                    outList.add(sequence.getNext().get2());
            } catch (NoSuchElementException e) {
                return outList;
            }
        }
    }

    @Override
    public <E> Iterable<E> asEventContentIterable(EventSequenceProducer<E> sequence) {
        return new Iterable<E>() {

            @Override
            public Iterator<E> iterator() {

                return new Iterator<E>() {
                    private boolean hasNext = true;

                    @Override
                    public boolean hasNext() {
                        return hasNext;
                    }

                    @Override
                    public E next() {
                        try {
                            return sequence.getNext().get2();
                        } catch (NoSuchElementException e) {
                            hasNext = false;
                        }
                        return null;
                    }

                };
            }

        };
    }

    @Override
    public <E> Optional<Pair<Double, E>> nextAt(EventSequenceProducer<E> sequence, double time) {
        return Optional.of(filter(sequence, n -> sequence.getNext().get1() < time).getNext());
    }

    @Override
    public <E> EventSequenceProducer<E> filter(EventSequenceProducer<E> sequence, Predicate<E> predicate) {
        return new EventSequenceProducer<E>() {

            @Override
            public Pair<Double, E> getNext() throws NoSuchElementException {
                final var next = sequence.getNext();
                if (predicate.test(next.get2())) {
                    return next;
                }
                throw new NoSuchElementException();
            }

        };
    }

}
