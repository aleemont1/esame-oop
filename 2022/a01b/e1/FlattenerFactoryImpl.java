package a01b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlattenerFactoryImpl implements FlattenerFactory {

    @Override
    public Flattener<Integer, Integer> sumEach() {
        return each(l -> l.stream().mapToInt(Integer::intValue).sum());
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        return new Flattener<X, X>() {

            @Override
            public List<X> flatten(List<List<X>> list) {
                final var it = list.iterator();
                final var outList = new ArrayList<X>();
                while (it.hasNext()) {
                    final var subIt = it.next().iterator();
                    while (subIt.hasNext()) {
                        outList.add(subIt.next());
                    }
                }
                return outList;
            }

        };
    }

    @Override
    public Flattener<String, String> concatPairs() {
        Function<List<String>, String> concatPairsFunction = sublist -> String.join("", sublist);

        return new Flattener<String, String>() {
            @Override
            public List<String> flatten(List<List<String>> list) {
                List<String> result = new ArrayList<>();
                for (int i = 0; i < list.size(); i += 2) {
                    List<String> sublist = list.get(i);
                    if (i + 1 < list.size()) {
                        sublist.addAll(list.get(i + 1));
                    }
                    String concatenated = concatPairsFunction.apply(sublist);
                    result.add(concatenated);
                }
                return result;
            }
        };
    }

    @Override
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        return new Flattener<I, O>() {

            @Override
            public List<O> flatten(List<List<I>> list) {
                return list.stream().map(mapper).collect(Collectors.toList());
            }
        };
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        return new Flattener<Integer, Integer>() {

            @Override
            public List<Integer> flatten(List<List<Integer>> vectors) {
                if (vectors.isEmpty()) {
                    return new ArrayList<>();
                }

                int size = vectors.get(0).size();
                List<Integer> result = new ArrayList<>(size);

                for (int i = 0; i < size; i++) {
                    int sum = 0;
                    for (List<Integer> vector : vectors) {
                        sum += vector.get(i);
                    }
                    result.add(sum);
                }

                return result;
            }

        };
    }

}
