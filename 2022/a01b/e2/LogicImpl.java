package a01b.e2;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

public class LogicImpl implements Logic {
    private final int size;
    private final List<JButton> cells;
    private int moves;
    private boolean over = false;
    protected LogicImpl(final int size, final List<JButton> cells, final int moves) {
        this.size = size;
        this.cells = new ArrayList<>(cells);
        this.moves = moves;
    }

    @Override
    public boolean isOver() {
        return this.over;
    }

    private void toggle(JButton button) {
        if (button.getText().equals("")) {
            button.setText("*");
        } else {
            button.setText("");
        }
    }

    private int countStars(final List<JButton> buttons) {
        int stars = 0;
        for (final var b : buttons) {
            if (b.getText().equals("*")) {
                stars++;
            }
        }
        return stars;
    }

    @Override
    public void isHit(JButton button) {
        final int index = cells.indexOf(button);
        final int row = index / this.size;
        final int col = index % size;
        // check if it's a border click
        if (row == 0 || col == 0 || row == size - 1 || col == size - 1) {
            return;
        }

        final List<JButton> corners = List.of(cells.get(index - size - 1), cells.get(index - size + 1),
                cells.get(index + size - 1), cells.get(index + size + 1));
        for (final var c : corners) {
            toggle(c);
        }
        moves++;
        if (moves >= 3 && !button.getText().equals("*") &&
                countStars(corners) == 3) {
            // Game over condition met
            this.over = true;
        }
    }

}
