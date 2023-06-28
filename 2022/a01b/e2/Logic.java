package a01b.e2;

import java.util.List;

public interface Logic {
    boolean isOver();
    void hit(int x, int y);
    List<Pair<Integer,Integer>> getStars();
}
