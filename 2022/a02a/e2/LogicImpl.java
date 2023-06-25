package a02a.e2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogicImpl implements Logic {
    private Set<Pair<Integer, Integer>> enabled = new HashSet<>();
    private final Set<Pair<Integer, Integer>> bishops = new HashSet<>();
    private final int size;

    protected LogicImpl(final int size) {
        this.size = size;
        this.clear();
    }

    private void clear() {
        this.enabled = IntStream.range(0, size)
                .mapToObj(i -> i)
                .flatMap(x -> IntStream.range(0, size)
                        .mapToObj(y -> new Pair<>(x, y)))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
        bishops.clear();
    }

    @Override
    public void isHit(int x, int y) {
        final var p = new Pair<>(x, y);
        if (this.isOver() && bishops.contains(p)) {
            this.clear();
            return;
        }
        if (enabled.contains(p) && !bishops.contains(p)) {
            bishops.add(p);
            final var iterator = enabled.iterator();
            while (iterator.hasNext()) {
                final var position = iterator.next();
                if (!isBishop(position.getX(), position.getY())
                        && (position.getX() - position.getY() == p.getX() - p.getY()
                                || position.getX() + position.getY() == p.getX() + p.getY())) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public boolean isBishop(int x, int y) {
        return bishops.contains(new Pair<>(x, y));
    }

    @Override
    public boolean isEnabled(int x, int y) {
        return enabled.contains(new Pair<>(x, y));
    }

    private boolean isOver() {
        return this.enabled.size() == this.bishops.size();
    }
}
