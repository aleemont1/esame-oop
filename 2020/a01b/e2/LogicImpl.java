package a01b.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogicImpl implements Logic {

    private List<Pair<Integer, Integer>> paws = new ArrayList<>();
    private Pair<Integer, Integer> king;
    private final int size;
    private static final int N_PAWS = 3;

    protected LogicImpl(final int size) {
        this.size = size;
        this.king = new Pair<>(size - 1, size - 1);
        for (int i = 0; i < N_PAWS; i++) {
            paws.add(this.randPos(size-2, size));
        }
    }

    private final Pair<Integer, Integer> randPos(final int x, final int y) {
        Random random = new Random();
        return new Pair<>(random.nextInt(x), random.nextInt(y));
    }

    @Override
    public boolean isOver() {
        return paws.size() == 0;
    }

    @Override
    public boolean move(int x, int y) {
        final var pos = new Pair<>(x, y);
        if (this.getValidMoves(this.king.getX(), this.king.getY()).contains(pos) && !checkPawnCollisions(x, y)) {
            if (paws.contains(pos)) {
                paws.remove(pos);
            }
            this.king = new Pair<Integer, Integer>(x, y);
            return true;
        }
        return false;
    }

    private final boolean checkPawnCollisions(int x, int y) {
        return paws.stream().anyMatch(
                p -> (p.equals(new Pair<>((x - 1), (y - 1))))
                        || (p.equals(new Pair<>((x + 1), (y - 1)))));

    }

    private final List<Pair<Integer, Integer>> getValidMoves(int x, int y) {
        return Stream.of(
                new Pair<>(x - 1, y), new Pair<>(x + 1, y),
                new Pair<>(x, y - 1), new Pair<>(x, y + 1),
                new Pair<>(x + 1, y + 1), new Pair<>(x - 1, y - 1),
                new Pair<>(x - 1, y + 1), new Pair<>(x + 1, y - 1))
                .filter(p -> p.getX() >= 0 && p.getX() < this.size && p.getY() >= 0 && p.getY() < this.size)
                .collect(Collectors.toList());
    }

    public final boolean isKing(Pair<Integer, Integer> pos) {
        return this.king.equals(pos);
    }

    public final boolean isPaw(Pair<Integer, Integer> pos) {
        return this.paws.contains(pos);
    }

}
