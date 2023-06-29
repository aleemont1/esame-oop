package a03b.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicImpl implements Logic {

    private List<Pair<Integer, Integer>> player = new ArrayList<>();
    private List<Pair<Integer, Integer>> computer = new ArrayList<>();
    private final int size;

    protected LogicImpl(final int size) {
        this.size = size;
        this.init();
    }

    private void init() {
        for (int i = 0; i < size; i++) {
            player.add(new Pair<>(i, size - 1));
            computer.add(new Pair<>(getRandomNum(size), getRandomNum(2)));
        }
    }

    private final int getRandomNum(final int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    @Override
    public boolean isOver() {
        return computer.isEmpty();
    }

    @Override
    public boolean move(int x, int y) {
        final var possibleMoves = List.of(new Pair<>(x - 1, y - 1), new Pair<>(x + 1, y - 1));
        final var pos = new Pair<>(x, y);
        if (isPawn(x, y)) {
            for (final var e : possibleMoves) {
                if (computer.contains(e)) {
                    player.remove(pos);
                    return eat(e);
                }
            }
            if (!isPawn(x, y - 1) && !isComputer(x, y - 1) && y > 0) {
                player.remove(pos);
                return player.add(new Pair<>(x, y - 1));
            }
        }
        return false;
    }

    private final boolean eat(Pair<Integer, Integer> pos) {
        return player.add(pos) && computer.remove(pos);
    }

    public final boolean isPawn(int x, int y) {
        return player.contains(new Pair<>(x, y));
    }

    @Override
    public boolean isComputer(int x, int y) {
        return computer.contains(new Pair<>(x, y));
    }

}
