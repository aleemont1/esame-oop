package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return new SubsequenceCombiner<Integer, Integer>() {

            @Override
            public List<Integer> combine(List<Integer> list) {
                final List<Integer> outList = new ArrayList<>();
                int index = 0;
                int sum = 0;
                while (index < list.size()) {
                    sum = 0;
                    for (int i = index; i < list.size() && i < index + 3; i++) {
                        sum += list.get(i);
                    }
                    index += 3;
                    outList.add(sum);
                }
                return outList;
            }
        };
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return new SubsequenceCombiner<X, List<X>>() {
            @Override
            public List<List<X>> combine(List<X> list) {
                final List<List<X>> outList = new ArrayList<>();
                int index = 0;
                while (index < list.size()) {
                    final List<X> subList = new ArrayList<>();
                    for (int i = index; i < list.size() && i < index + 3; i++) {
                        subList.add(list.get(i));
                    }
                    index += 3;
                    outList.add(subList);
                }
                return outList;
            }
        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countUntilZero'");
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return new SubsequenceCombiner<X, Y>() {

            @Override
            public List<Y> combine(List<X> list) {
                return list.stream().map(e -> function.apply(e)).collect(Collectors.toList());
            }

        };
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return new SubsequenceCombiner<Integer, List<Integer>>() {

            @Override
            public List<List<Integer>> combine(List<Integer> list) {
                final List<List<Integer>> outList = new ArrayList<>();
                int sum = 0;
                final List<Integer> subList = new ArrayList<>();
                for (final int e : list) {
                    subList.add(e);
                    if ((sum += e) >= threshold || list.indexOf(e) == list.size() - 1) {
                        outList.add(new ArrayList<>(subList));
                        subList.clear();
                        sum = 0;
                    }
                }
                return outList;
            }

        };
    }

}
