package a01c.e1;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class EventHistoryFactoryImpl implements EventHistoryFactory {

    @Override
    public <E> EventHistory<E> fromMap(Map<Double, E> map) {
        final Iterator<Double> times = map.entrySet().stream()
                .sorted((p1, p2) -> p1.getKey() - p2.getKey() > 0.0 ? 1 : -1)
                .map(t -> t.getKey()).iterator();
        final Iterator<E> content = map.entrySet().stream()
                .sorted((p1, p2) -> p1.getKey() - p2.getKey() > 0.0 ? 1 : -1)
                .map(t -> t.getValue()).iterator();
        return fromIterators(times, content);
    }

    @Override
    public <E> EventHistory<E> fromIterators(Iterator<Double> times, Iterator<E> content) {
        return new EventHistory<E>() {

            @Override
            public double getTimeOfEvent() {
                return times.hasNext() ? times.next() : null;
            }

            @Override
            public E getEventContent() {
                return content.hasNext() ? content.next() : null;
            }

            @Override
            public boolean moveToNextEvent() {
                return times.hasNext() && content.hasNext();
            }

        };
    }

    @Override
    public <E> EventHistory<E> fromListAndDelta(List<E> content, double initial, double delta) {
        return fromIterators(Stream.iterate(initial, d -> d + delta).iterator(), content.iterator());

    }

    @Override
    public <E> EventHistory<E> fromRandomTimesAndSupplier(Supplier<E> content, int size) {
        return fromIterators(Stream.iterate(0.0, x -> x + Math.random()).limit(size).iterator(),
                Stream.generate(content).iterator());
    }

    @Override
    public EventHistory<String> fromFile(String file) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromFile'");
    }

}
