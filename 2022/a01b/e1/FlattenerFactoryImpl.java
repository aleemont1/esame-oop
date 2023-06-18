package a01b.e1;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FlattenerFactoryImpl implements FlattenerFactory {

    @Override
    public Flattener<Integer, Integer> sumEach() {
        return new Flattener<Integer, Integer>() {

            @Override
            public List<Integer> flatten(List<List<Integer>> list) {
                return list.stream().map(l -> l.stream().mapToInt(Integer::intValue).sum())
                        .collect(Collectors.toList());
                /*
                 * final List<Integer> outList = new ArrayList<>();
                 * int sum = 0;
                 * for(final var l : list) {
                 * for(final var e : l) {
                 * sum+=e;
                 * }
                 * outList.add(sum);
                 * sum = 0;
                 * }
                 * return outList;
                 */
            }

        };
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        return new Flattener<X, X>() {

            @Override
            public List<X> flatten(List<List<X>> list) {
                return list.stream().flatMap(Collection::stream).collect(Collectors.toList());
            }

        };
    }

    @Override
    public Flattener<String, String> concatPairs() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'concatPairs'");
    }

    @Override
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        return new Flattener<I, O>() {

            @Override
            public List<O> flatten(List<List<I>> list) {
                return list.stream().map(l -> mapper.apply(l)).collect(Collectors.toList());
            }

        };
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        return new Flattener<Integer, Integer>() {

            @Override
            public List<Integer> flatten(List<List<Integer>> list) {
                return IntStream.range(0, list.get(0).size())
                        .mapToObj(i -> list.stream().map(vector -> vector.get(i)).reduce(0, Integer::sum))
                        .collect(Collectors.toList());
            }

        };
    }

}
