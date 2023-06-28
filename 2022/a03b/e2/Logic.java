package a03b.e2;

import java.util.List;

public interface Logic {
    boolean isOver();

    boolean move(int x, int y);

    List<Pair<Integer, Integer>> getPlayer();

    List<Pair<Integer, Integer>> getComputer();
}
