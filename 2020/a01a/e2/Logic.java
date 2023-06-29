package a01a.e2;

public interface Logic {
    boolean isOver();

    boolean hit(int x, int y);

    Pair<Boolean, Boolean> isMarked(Pair<Integer, Integer> pos);

    boolean getTurn();
}
