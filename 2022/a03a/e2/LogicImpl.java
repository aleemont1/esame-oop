package a03a.e2;

import java.util.Optional;

public class LogicImpl implements Logic {
    private Pair<Integer, Integer> player;
    private Pair<Integer, Integer> computer;
    private boolean turn; // false = player, true = computer
    private final int size;

    public LogicImpl(final int size) {
        this.size = size;
        this.turn = false;
        generateRandomPositions();
    }

    private void generateRandomPositions() {
        player = new Pair<>(getRandomPos(size), getRandomPos(size));
        int x = getRandomPos(size);
        int y = getRandomPos(size);
        while (x == player.getX() && y == player.getY()) {
            x = getRandomPos(size);
            y = getRandomPos(size);
        }
        computer = new Pair<>(x, y);
    }

    @Override
    public Pair<Integer, Integer> getPlayerPos() {
        return player;
    }

    @Override
    public Pair<Integer, Integer> getCompPos() {
        return computer;
    }

    @Override
    public Optional<Boolean> isOver() {
        if (computer.getX() == player.getX() && computer.getY() == player.getY()) {
            return Optional.of(turn);
        }
        return Optional.empty();
    }

    @Override
    public void move(int x, int y) {
        final var playerPos = this.getPlayerPos();
        final var compPos = this.getCompPos();

        if (playerPos.getX() == x || playerPos.getY() == y) {
            this.player = new Pair<Integer, Integer>(x, y);
        }
        if (!this.player.equals(computer)) {
            turn = !turn;
        }

        if (compPos.getX() == playerPos.getX() || compPos.getY() == playerPos.getY()) {
            this.computer = this.player; // eats
        } else {
            final var randPos = getRandomPos(size);
            if (randPos % 2 == 0) {
                this.computer = new Pair<Integer, Integer>(randPos, computer.getY());
            } else {
                this.computer = new Pair<Integer, Integer>(computer.getX(), randPos);
            }
            if (!computer.equals(player))
                turn = !turn;
        }

    }

    private int getRandomPos(int max) {
        return (int) (Math.random() * max);
    }

    @Override
    public boolean getTurn() {
        return turn;
    }
}
