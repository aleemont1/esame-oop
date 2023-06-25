package a02b.e2;

import java.util.HashSet;
import java.util.Set;

public class LogicImpl implements Logic {

    private final Set<Pair<Integer, Integer>> stars = new HashSet<>();
    private final Set<Pair<Integer, Integer>> disabled = new HashSet<>();
    private final int size;

    protected LogicImpl(final int size) {
        this.size = size;
    }

    @Override
    public void restart() {
        stars.clear();
        disabled.clear();
    }

    private void toggle(final int x, final int y) {
        final var p = new Pair<>(x, y);
        if (!disabled.contains(p)) {
            if (stars.contains(p)) {
                stars.remove(p);
            } else {
                stars.add(p);
            }
        }
    }

    @Override
    public boolean hit(int x, int y) {
        final var p = new Pair<>(x, y);
        if (!disabled.contains(p)) {
            this.toggle(p.getX(), p.getY());
            return true;
        }
        return false;
    }

    @Override
    public synchronized void checkRestart() {
        final Set<Pair<Integer, Integer>> diagonal = new HashSet<>();
        final Set<Pair<Integer, Integer>> starsCopy = new HashSet<>(stars);
        for (final var star : starsCopy) {
            int x = star.getX();
            int y = star.getY();
            int nStar = isStar(x, y) ? 1 : 0;
            int i = x;
            int j = y;
            diagonal.add(new Pair<>(i, j));
            while (i >= 0 && j >= 0) {
                if (isStar(--i, --j)) {
                    nStar++;
                }
                diagonal.add(new Pair<>(i, j));
            }
            i = x;
            j = y;
            while (i < this.size && j < this.size) {
                if (isStar(++i, ++j)) {
                    nStar++;
                }
                diagonal.add(new Pair<>(i, j));
            }

            if (nStar == 3) {
                if (disabled.isEmpty()) {
                    disableDiagonal(diagonal);
                    break;
                } else {
                    restart();
                    break;
                }
            }
        }
    }

    private void disableDiagonal(Set<Pair<Integer, Integer>> diagonal) {
        disabled.addAll(diagonal);
    }

    @Override
    public boolean isStar(final int x, final int y) {
        return stars.contains(new Pair<>(x, y));
    }

    @Override
    public boolean isAvailable(final int x, final int y) {
        return !disabled.contains(new Pair<>(x, y));
    }

}
