package a02a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class ScannerFactoryImpl implements ScannerFactory {

    private final <X> List<X> fromIterator(Iterator<X> input) {
        final List<X> outList = new ArrayList<>();
        input.forEachRemaining(outList::add);
        return outList;
    }

    @Override
    public <X, Y> Scanner<X, List<Y>> collect(Predicate<X> filter, Function<X, Y> mapper) {
        return new Scanner<X, List<Y>>() {

            @Override
            public List<Y> scan(Iterator<X> input) {
                final List<X> outList = new ArrayList<>();
                input.forEachRemaining(outList::add);
                return outList.stream().filter(filter).map(mapper).toList();
            }

        };
    }

    @Override
    public <X> Scanner<X, Optional<X>> findFirst(Predicate<X> filter) {
        return new Scanner<X, Optional<X>>() {

            @Override
            public Optional<X> scan(Iterator<X> input) {
                return fromIterator(input).stream()
                        .filter(filter)
                        .findFirst();
            }

        };
    }

    @Override
    public Scanner<Integer, Optional<Integer>> maximalPrefix() {
        return new Scanner<Integer, Optional<Integer>>() {

            @Override
            public Optional<Integer> scan(Iterator<Integer> input) {
                final var list = fromIterator(input);
                return IntStream.range(0, list.size())
                        .takeWhile(index -> index == 0 || list.get(index) >= list.get(index - 1))
                        .mapToObj(list::get)
                        .reduce(Integer::max);
            }

        };
    }

    @Override
    public Scanner<Integer, List<Integer>> cumulativeSum() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cumulativeSum'");
    }

}
