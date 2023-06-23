package a02c.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListSplitterFactoryImpl implements ListSplitterFactory {

    private <X> List<List<X>> fromSize(List<X> list, int size) {
        return IntStream.range(0, list.size() / size).mapToObj(i -> list.subList(i * size, i * size + size))
                .collect(Collectors.toList());
    }

    private <X> ListSplitter<X> asSize(int size) {
        return new ListSplitter<X>() {

            @Override
            public List<List<X>> split(List<X> list) {
                return fromSize(list, size);
            }

        };
    }

    @Override
    public <X> ListSplitter<X> asPairs() {
        return asSize(2);
    }

    @Override
    public <X> ListSplitter<X> asTriplets() {
        return asSize(3);
    }

    @Override
    public <X> ListSplitter<X> asTripletsWithRest() {
        return new ListSplitter<X>() {

            @Override
            public List<List<X>> split(List<X> list) {
                int sublistSize = 3;
                int totalSublists = (int) Math.ceil(list.size() / (double) sublistSize);

                return IntStream.range(0, totalSublists)
                        .mapToObj(i -> list.subList(i * sublistSize,
                                Math.min((i + 1) * sublistSize, list.size())))
                        .collect(Collectors.toList());
            }

        };
    }

    @Override
    public <X> ListSplitter<X> bySeparator(X separator) {
        return new ListSplitter<X>() {

            @Override
            public List<List<X>> split(List<X> list) {
                final List<List<X>> outList = new ArrayList<>();
                List<X> subList = new ArrayList<>();
                for (final var e : list) {
                    if (e != separator) {
                        subList.add(e);
                    } else {
                        if (!subList.isEmpty()) {
                            outList.add(subList);
                            subList = new ArrayList<>();
                        }
                        outList.add(List.of(e));
                    }
                }

                if (!subList.isEmpty()) {
                    outList.add(subList);
                }
                return outList;
            }
        };
    }

    @Override
    public <X> ListSplitter<X> byPredicate(Predicate<X> predicate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'byPredicate'");
    }

}
