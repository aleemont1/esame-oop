package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return new SubsequenceCombiner<Integer, Integer>() {

            @Override
            public List<Integer> combine(List<Integer> list) {
                SubsequenceCombiner<Integer, List<Integer>> combiner = tripletsToList();
                List<List<Integer>> outList = combiner.combine(list);
                return outList.stream()
                        .map(sublist -> sublist.stream().mapToInt(Integer::intValue).sum())
                        .toList();
            }

        };
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return new SubsequenceCombiner<X, List<X>>() {

            @Override
            public List<List<X>> combine(List<X> list) {
                final var it = list.iterator();
                final List<List<X>> outList = new ArrayList<>();
                List<X> subList = new ArrayList<>();
                int i = 0;
                while (it.hasNext()) {
                    subList.add(i++, it.next());
                    if (i == 3) {
                        outList.add(subList);
                        subList = new ArrayList<>();
                        i = 0;
                    }
                }
                if (i != 0) {
                    outList.add(subList);
                }
                return outList;
            }

        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return null;
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return new SubsequenceCombiner<X, Y>() {

            @Override
            public List<Y> combine(List<X> list) {
                return list.stream().map(function).collect(Collectors.toList());
            }

        };
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return new SubsequenceCombiner<Integer, List<Integer>>() {

            @Override
            public List<List<Integer>> combine(List<Integer> list) {
                final var it = list.iterator();
                int sum = 0;
                final List<List<Integer>> outList = new ArrayList<>();
                List<Integer> subList = new ArrayList<>();
                while (it.hasNext()) {
                    if (sum >= 100) {
                        sum = 0;
                        outList.add(subList);
                        subList = new ArrayList<>();
                    }
                    final var e = it.next();
                    subList.add(e);
                    sum += e;
                    if (!it.hasNext()) {
                        outList.add(List.of(e));
                    }
                }
                return outList;
            }

        };
    }

}
