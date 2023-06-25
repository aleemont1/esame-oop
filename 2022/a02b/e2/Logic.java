package a02b.e2;

public interface Logic {
    void restart();
    boolean hit(int x, int y);
    void checkRestart();
    boolean isAvailable(int x, int y);
    boolean isStar(int x, int y);
}
