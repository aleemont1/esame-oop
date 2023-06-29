package a02a.e2;
public interface Logic {
    boolean isOver();

    boolean hit(int x, int y);

    void disableDiagonals(int x, int y);

    boolean isBishop(int x, int y);

    boolean isDisabled(int x, int y);
}
