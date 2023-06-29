package a03b.e2;

import java.util.List;

public interface Logic {
    boolean isOver();

    boolean move(int x, int y);

    boolean isPawn(int x, int y);

    boolean isComputer(int x, int y);
}
