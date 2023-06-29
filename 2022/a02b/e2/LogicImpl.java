package a02b.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic {
    private List<Pair<Integer, Integer>> stars = new ArrayList<>();
    private List<Pair<Integer, Integer>> disabled = new ArrayList<>();
    private final int size;
    private boolean over = false;

    protected LogicImpl(final int size) {
        this.size = size;
    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    private boolean toggle(int x, int y) {
        final var pos = new Pair<>(x, y);
        if (!disabled.contains(pos)) {
            if (!stars.contains(pos)) {
                stars.add(pos);
                return true;
            } else {
                stars.remove(pos);
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean hit(int x, int y) {
        return toggle(x, y);
    }

    @Override
    public boolean isStar(int x, int y) {
        return this.stars.contains(new Pair<>(x, y));
    }

    @Override
    public boolean isEnabled(int x, int y) {
        return !this.disabled.contains(new Pair<>(x, y));
    }

    @Override
    public boolean checkDiagonals() {
        if (!disabled.isEmpty()) {
            over = true;
            return true;
        } else {
            List<Pair<Integer, Integer>> starCells = new ArrayList<>();
            int count = 0;

            // Check for diagonal lines from bottom left to top right from x=0 to the end
            for (int i = 0; i < size; i++) {
                starCells.clear();
                count = 0;

                for (int j = 0; j <= i; j++) {
                    final var p = new Pair<>(j, size - 1 - i + j);
                    starCells.add(p);

                    if (stars.contains(p)) {
                        count++;
                    }
                }

                if (count == 3) {
                    disabled.addAll(starCells);
                    over = true;
                    return true;
                }
            }

            // Check for diagonal lines from bottom left to top right from top to bottom
            for (int i = 1; i < size; i++) {
                starCells.clear();
                count = 0;

                for (int j = 0; j < size - i; j++) {
                    final var p = new Pair<>(i + j, j);
                    starCells.add(p);

                    if (stars.contains(p)) {
                        count++;
                    }
                }

                if (count == 3) {
                    disabled.addAll(starCells);
                    over = true;
                    return true;
                }
            }
        }

        return false;
    }

}
