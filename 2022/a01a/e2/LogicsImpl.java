package a01a.e2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicsImpl implements Logics {

    final Set<Pair<Integer, Integer>> enabled = new HashSet<>();
    final List<Pair<Integer, Integer>> line = new ArrayList<>();

    @Override
    public boolean isOver() {
        if (this.line.size() < 3) {
            return false;
        }
        return checkDiag(line);
    }

    private boolean checkDiag(final List<Pair<Integer, Integer>> list) {
        int sumCheck = 1;
        for (int i = 0; i < list.size() - 1; i++) {
            final var current = list.get(i);
            final var next = list.get(i + 1);
            System.out.println(i + ") " + "curr: " + current + " next: " + next);
            if ((current.getX() + 1) == next.getX() && (current.getY() + 1) == next.getY()) {
                sumCheck++;
                System.out.println(sumCheck);
            }
        }
        return sumCheck == 3;
    }

    @Override
    public boolean isHit(int x, int y) {
        final var pos = new Pair<>(x, y);
        if (enabled.contains(pos)) {
            enabled.remove(pos);
            line.clear();
        } else {
            enabled.add(pos);
            line.add(pos);
            if (line.size() > 3) {
                line.remove(0);
            }
        }
        return enabled.contains(pos);
    }

}
