package a02b.e1;

import java.util.ArrayList;
import java.util.List;

public class PatternExtractorFactoryImpl implements PatternExtractorFactory {

    @Override
    public PatternExtractor<Integer, Integer> countConsecutiveZeros() {
        return new PatternExtractor<Integer, Integer>() {
            @Override
            public List<Integer> extract(List<Integer> input) {
                final List<Integer> outList = new ArrayList<>();
                int sum = 0;
                for (int i = 0; i < input.size(); i++) {
                    while (input.get(i) == 0) {
                        sum++;
                        i++;
                    }
                    if (sum > 0)
                        outList.add(sum);
                    sum = 0;
                }
                return outList;
            }
        };
    }

    @Override
    public PatternExtractor<Double, Double> averageConsecutiveInRange(double min, double max) {
        return new PatternExtractor<Double, Double>() {

            @Override
            public List<Double> extract(List<Double> input) {
                final List<Double> outList = new ArrayList<>();
                double sum = 0, avg = 0;
                for (int i = 0; i < input.size(); i++) {

                    while (input.get(i).doubleValue() > min && input.get(i).doubleValue() < max) {
                        sum += input.get(i).doubleValue();
                        avg++;
                        i++;
                    }
                    if (sum != 0)
                        outList.add(sum / avg);
                    sum = 0;
                    avg = 0;
                }
                return outList;
            }

        };
    }

    @Override
    public PatternExtractor<String, String> concatenateBySeparator(String separator) {
        return new PatternExtractor<String, String>() {

            @Override
            public List<String> extract(List<String> input) {
                final List<String> outList = new ArrayList<>();
                String concat = "";
                for (int i = 0; i < input.size(); i++) {
                    while (!input.get(i).equals(separator)) {
                        concat = concat + input.get(i);
                        if (i + 1 < input.size())
                            i++;
                        else
                            break;
                    }
                    if (concat != "")
                        outList.add(concat);
                    concat = "";
                }
                return outList;
            }

        };
    }

    @Override
    public PatternExtractor<String, Double> sumNumericStrings() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sumNumericStrings'");
    }

}
