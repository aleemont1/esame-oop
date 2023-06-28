package a03a.e2;

import java.util.Optional;

public interface Logic {
    Optional<Boolean> isOver();

    void move(int x, int y);

    Pair<Integer,Integer> getPlayerPos();

    Pair<Integer,Integer> getCompPos();

    boolean getTurn();
}
