package a01a.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic {
    private final List<Pair<Integer, Integer>> playerOne = new ArrayList<>();
    private final List<Pair<Integer, Integer>> playerTwo = new ArrayList<>();
    private final int size;
    private boolean turn = true; // true == playerOne, false == playerTwo;

    protected LogicImpl(final int size) {
        this.size = size;
    }

    @Override
    public boolean isOver() {
        return isVerticalWinning() || isHorizontalWinning();
    }

    @Override
    public boolean hit(int x, int y) {
        if (isMarked(new Pair<>(x, y + 1)).getX() || y == size - 1) {
            System.out.println("Hit in: " + new Pair<>(x, y));
            toggle(x, y);
            return true;
        }
        System.out.println("NO Hit in: " + new Pair<>(x, y));
        return false;
    }

    private void toggle(final int x, final int y) {
        final var pos = new Pair<>(x, y);
        if (!isMarked(pos).getX()) {
            if (turn) {
                playerOne.add(pos);
            } else {
                playerTwo.add(pos);
            }
            turn = !turn;
        }
    }

    public final Pair<Boolean, Boolean> isMarked(Pair<Integer, Integer> pos) {
        return new Pair<>(playerOne.contains(pos) || playerTwo.contains(pos), this.getTurn());
    }

    private boolean isVerticalWinning() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 2; j++) {
                final var cells = List.of(new Pair<>(i, j), new Pair<>(i, j + 1), new Pair<>(i, j + 2));
                boolean isPlayerOneWinning = playerOne.containsAll(cells);
                boolean isPlayerTwoWinning = playerTwo.containsAll(cells);

                if (isPlayerOneWinning || isPlayerTwoWinning) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isHorizontalWinning() {
        for (int i = 0; i < size - 2; i++) {
            for (int j = 0; j < size; j++) {
                final var cells = List.of(new Pair<>(i, j), new Pair<>(i + 1, j), new Pair<>(i + 2, j));
                boolean isPlayerOneWinning = playerOne.containsAll(cells);
                boolean isPlayerTwoWinning = playerTwo.containsAll(cells);

                if (isPlayerOneWinning || isPlayerTwoWinning) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean getTurn() {
        return this.turn;
    }
}
