package a03b.e2;

import java.util.ArrayList;
import java.util.List;

public class LogicImpl implements Logic {

    final int size;
    final List<Pair<Integer, Integer>> player = new ArrayList<>();
    final List<Pair<Integer, Integer>> computer = new ArrayList<>();

    protected LogicImpl(final int size) {
        this.size = size;
        initGame();
    }

    private void initGame() {
        player.clear();
        computer.clear();
        for (int y = 0; y < size; y++) {
            computer.add(new Pair<>(getRandomPos(size), getRandomPos(2)));
            player.add(new Pair<>(y, size - 1));
        }
    }

    private int getRandomPos(int max) {
        return (int) (Math.random() * max);
    }

    @Override
    public boolean isOver() {
        return (computer.isEmpty() || this.isStuck()) ? true : false;
    }

    @Override
    public boolean move(int x, int y) {
        final var currentPos = new Pair<>(x, y);
        final var eatRight = new Pair<>(x + 1, y - 1);
        final var eatLeft = new Pair<>(x - 1, y - 1);
        final var movePos = new Pair<>(x, y - 1);

        if (!player.contains(currentPos)) {
            return false;
        }
        if (computer.contains(eatRight)) {
            return eat(eatRight, currentPos);
        }
        if (computer.contains(eatLeft)) {
            return eat(eatLeft, currentPos);
        }
        if (!player.contains(movePos) && !computer.contains(movePos) && movePos.getY() > 0) {
            return playerMove(movePos, currentPos);
        }
        
        return false;
    }

    private boolean eat(final Pair<Integer, Integer> pos, final Pair<Integer, Integer> oldPos) {
        return this.computer.remove(pos) &&
                playerMove(pos, oldPos);
    }

    private boolean playerMove(final Pair<Integer, Integer> newPos, final Pair<Integer, Integer> oldPos) {
        return this.player.add(newPos) &&
                this.player.remove(oldPos);
    }

    private boolean isStuck() {
        return player.stream()
                .allMatch(p -> computer.stream()
                        .anyMatch(c -> p.getX() == c.getX() && p.getY() == c.getY() + 1));
    }

    @Override
    public List<Pair<Integer, Integer>> getPlayer() {
        return new ArrayList<>(this.player);
    }

    @Override
    public List<Pair<Integer, Integer>> getComputer() {
        return new ArrayList<>(this.computer);
    }

}
