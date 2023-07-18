package a03a.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class ParserFactoryImpl implements ParserFactory {

    @Override
    public <X> Parser<X> fromFinitePossibilities(Set<List<X>> acceptedSequences) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                List<X> sequence = new ArrayList<>();

                while (iterator.hasNext()) {
                    sequence.add(iterator.next());
                }

                return acceptedSequences.contains(sequence);
            }

        };
    }

    @Override
    public <X> Parser<X> fromGraph(X x0, Set<Pair<X, X>> transitions, Set<X> acceptanceInputs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromGraph'");
    }

    @Override
    public <X> Parser<X> fromIteration(X x0, Function<X, Optional<X>> next) {
        return recursive(x -> x.equals(x0)
                ? Optional.of(next.apply(x0).map(y -> fromIteration(y, next))
                        .orElse(fromFinitePossibilities(Set.of(Collections.emptyList()))))
                : Optional.empty(), false);

    }

    @Override
    public <X> Parser<X> recursive(Function<X, Optional<Parser<X>>> nextParser, boolean isFinal) {
        return new Parser<X>() {

            @Override
            public boolean accept(Iterator<X> iterator) {
                if (!iterator.hasNext())
                    return isFinal;
                final var next = nextParser.apply(iterator.next());
                return next.isPresent() ? next.get().accept(iterator) : false;
            }

        };
    }

    @Override
    public <X> Parser<X> fromParserWithInitial(X x, Parser<X> parser) {
        return recursive(p -> Optional.of(parser), false);
    }

}
