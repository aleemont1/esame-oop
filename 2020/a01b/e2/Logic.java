package a01b.e2;

public interface Logic {
    boolean isOver();

    boolean move(int x, int y);

    boolean isKing(Pair<Integer, Integer> pos);

    boolean isPaw(Pair<Integer, Integer> pos);
}
