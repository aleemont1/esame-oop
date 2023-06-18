package a01b.e2;

import java.util.HashSet;
import java.util.Set;

public class LogicImpl implements Logic {
    private Set<Pair<Integer, Integer>> enabled = new HashSet<>();
    final int size;
    private boolean over = false;

    public LogicImpl(final int size) {
        this.size = size;
    }

    private boolean toggle(final int x, final int y) {
        var p = new Pair<>(x, y);
        if (this.enabled.contains(p)) {
            this.enabled.remove(p);
            return false;
        }
        this.enabled.add(p);
        return true;
    }

    @Override
    public void hit(final int x, final int y) {
        toggle(x - 1, y - 1);
        toggle(x - 1, y + 1);
        toggle(x + 1, y - 1);
        toggle(x + 1, y + 1);
        int numStar = 0;
        int numEmpty = 0;
        if (isEnabled(x - 1, y - 1))
            numStar++;
        if (isEnabled(x - 1, y + 1))
            numStar++;
        if (isEnabled(x + 1, y - 1))
            numStar++;
        if (isEnabled(x + 1, y + 1))
            numStar++;
        if (!isEnabled(x - 1, y - 1) && !isEnabled(x - 1, y + 1) && !isEnabled(x + 1, y - 1)
                && !isEnabled(x + 1, y + 1))
            numEmpty++;

        if (numStar == 3 && numEmpty == 1) {
            over = true;
        }
    }

    @Override
    public boolean isEnabled(int x, int y) {
        return this.enabled.contains(new Pair<Integer, Integer>(x, y));
    }

    @Override
    public boolean isOver() {
        return this.over;
    }
}
