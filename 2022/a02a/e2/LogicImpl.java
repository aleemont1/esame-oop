package a02a.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic {

    private List<Pair<Integer, Integer>> bishops = new ArrayList<>();
    private List<Pair<Integer, Integer>> disabled = new ArrayList<>();

    private final int size;

    protected LogicImpl(final int size) {
        this.size = size;
    }

    @Override
    public boolean isOver() {
        System.out.println(bishops.size() + " " + disabled.size() + " " + size * size);

        return ((bishops.size() + disabled.size()) == (size * size));
    }

    @Override
    public boolean hit(int x, int y) {
        final var pos = new Pair<>(x, y);
        if (this.isOver() && isBishop(x, y)) {
            this.bishops = new ArrayList<>();
            this.disabled = new ArrayList<>();
            return true;
        }
        if (!isBishop(x, y) && !disabled.contains(pos)) {
            bishops.add(pos);
            disableDiagonals(x, y);
            return true;
        }
        return false;
    }

    @Override
    public boolean isBishop(final int x, final int y) {
        return bishops.contains(new Pair<>(x, y));
    }

    @Override
    public void disableDiagonals(final int x0, final int y0) {
        int x, y;

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                x = x0 + i;
                y = y0 + j;

                while (x >= 0 && x < size && y >= 0 && y < size) {
                    disable(new Pair<>(x, y));
                    x += i;
                    y += j;
                }
            }
        }
    }

    private void disable(final Pair<Integer, Integer> p) {
        if (!disabled.contains(p)) {
            disabled.add(p);
        }
    }

    @Override
    public boolean isDisabled(int x, int y) {
        return this.disabled.contains(new Pair<>(x, y));
    }

}
