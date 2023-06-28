package a01b.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicImpl implements Logic {
    private final int size;
    final List<Pair<Integer, Integer>> stars = new ArrayList<>();
    private static int countRemoved = 0;
    private boolean over = false;

    protected LogicImpl(final int size) {
        this.size = size;
    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    private void toggle(final int x, final int y) {
        final var pos = new Pair<>(x, y);
        if (stars.contains(pos)) {
            stars.remove(pos);
            countRemoved++;
        } else {
            stars.add(pos);
        }
    }

    @Override
    public void hit(final int x, final int y) {
        final var corners = getCorners(x, y);
        System.out.println(corners);
        for (final var c : corners) {
            toggle(c.getX(), c.getY());
        }
        if (countRemoved == 3) {
            over = true;
        } else {
            countRemoved = 0;
        }
    }

    private final List<Pair<Integer, Integer>> getCorners(final int x, final int y) {
        return Stream.of(
                new Pair<>(x - 1, y - 1),
                new Pair<>(x + 1, y - 1),
                new Pair<>(x - 1, y + 1),
                new Pair<>(x + 1, y + 1))
                .filter(p -> p.getX() >= 0 && p.getX() < size && p.getY() >= 0 && p.getY() < size)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pair<Integer, Integer>> getStars() {
        return new ArrayList<>(this.stars);
    }

}
