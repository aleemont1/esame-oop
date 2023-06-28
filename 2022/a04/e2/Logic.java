package a04.e2;

public interface Logic {
    boolean isOver();

    boolean move(int x, int y);

    Pair<Integer, Integer> getRook();

    Pair<Integer, Integer> getKing();
}
