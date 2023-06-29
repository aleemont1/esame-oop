package a02b.e2;

public interface Logic {
    boolean isOver();
    boolean hit(int x, int y);
    boolean checkDiagonals();
    boolean isStar(int x, int y);
    boolean isEnabled(int x, int y);
}
