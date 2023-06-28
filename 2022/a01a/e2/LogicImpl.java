package a01a.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic {
    final List<Pair<Integer, Integer>> hits = new ArrayList<>();
    final int size;
    static int countHits = 0;
    boolean over = false;

    protected LogicImpl(final int size) {
        this.size = size;
    }

    @Override
    public boolean hit(int x, int y) {
        final var pos = new Pair<>(x, y);

        if (!hits.contains(pos)) {
            System.out.println(isInDiagonal(x, y));
            if (isInDiagonal(x, y)) {
                countHits++;
            } else {
                countHits = 0;
            }
            if (hits.size() > 0)
                over = checkDiagonal(x, y) && countHits == 1;
            System.out.println(countHits);
            hits.add(pos);
            System.out.println(pos);
            return true;
        }
        hits.remove(pos);
        countHits = 0;
        return false;
    }

    private boolean isInDiagonal(int x, int y) {
        int countLeft = 0;
        int countRight = 0;

        for (Pair<Integer, Integer> pos : hits) {
            int dx = x - pos.getX();
            int dy = y - pos.getY();

            if (dx == dy) {
                countLeft++;
            }

            if (dx == -dy) {
                countRight++;
            }
        }

        return countLeft >= 2 || countRight >= 2;
    }

    private boolean checkDiagonal(int x, int y) {
        int dx = x - hits.get(hits.size() - 1).getX();
        int dy = y - hits.get(hits.size() - 1).getY();

        return hits.contains(new Pair<>(x - 2 * dx, y - 2 * dy)) && hits.contains(new Pair<>(x - dx, y - dy));
    }

    @Override
    public final boolean isOver() {
        return this.over;
    }

}
