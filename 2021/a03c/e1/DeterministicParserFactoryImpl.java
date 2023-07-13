package a03c.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DeterministicParserFactoryImpl implements DeterministicParserFactory {

    @Override
    public DeterministicParser oneSymbol(String s) {
        return new DeterministicParser() {

            @Override
            public Optional<List<String>> accepts(List<String> tokens) {
                var copy = new LinkedList<>(tokens);
                return (copy).remove(s) ? Optional.of(copy) : Optional.empty();
            }

        };
    }

    @Override
    public DeterministicParser twoSymbols(String s1, String s2) {
        return sequence(oneSymbol(s1), oneSymbol(s2));
    }

    @Override
    public DeterministicParser possiblyEmptyIncreasingSequenceOfPositiveNumbers() {
        return new DeterministicParser() {

            @Override
            public Optional<List<String>> accepts(List<String> tokens) {
                List<Integer> sequenceIndices = IntStream.range(0, tokens.size() - 1)
                        .filter(i -> Integer.parseInt(tokens.get(i + 1)) - Integer.parseInt(tokens.get(i)) >= 0)
                        .boxed()
                        .collect(Collectors.toList());

                if (sequenceIndices.isEmpty()) {
                    return Optional.empty();
                } else {
                    int lastSequenceIndex = sequenceIndices.get(sequenceIndices.size() - 1);
                    return Optional.of(tokens.subList(lastSequenceIndex, tokens.size()));
                }
            }

        };
    }

    @Override
    public DeterministicParser sequenceOfParsersWithDelimiter(String start, String stop, String delimiter,
            DeterministicParser element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sequenceOfParsersWithDelimiter'");
    }

    @Override
    public DeterministicParser sequence(DeterministicParser first, DeterministicParser second) {
        return tokens -> first.accepts(tokens).flatMap(second::accepts);
    }

}
