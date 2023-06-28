package a04.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicImpl implements Logic {

    private Pair<Integer, Integer> rook;
    private Pair<Integer, Integer> king;
    private final int size;

    protected LogicImpl(final int size) {
        this.rook = new Pair<Integer, Integer>(getRandomPos(size), getRandomPos(size));
        this.king = new Pair<Integer, Integer>(getRandomPos(size), getRandomPos(size));
        System.out.println("Init king in: " + king);
        System.out.println("Init rook in: " + rook);
        this.size = size;
    }

    private final int getRandomPos(final int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    @Override
    public boolean isOver() {
        return king.getX() == rook.getX() && king.getY() == rook.getY();
    }

    @Override
    public boolean move(int x, int y) {
        // if (checkOverKing(x, y))
        {
            if ((x == this.rook.getX() || y == this.rook.getY())) {
                this.rook = new Pair<Integer, Integer>(x, y);
                if (this.rook.equals(this.king)) {
                    kingMove();
                    return true;
                }
                kingMove();
                return false;
            }
        }
        kingMove();
        return false;
    }

    private void kingMove() {
        final int king_x = king.getX();
        final int king_y = king.getY();

        if (king_x == rook.getX() || king_y == rook.getY()) {
            this.king = this.rook;
            return;
        }

        int[] dx = { -1, 0, 1, -1, 1, -1, 0, 1 };
        int[] dy = { -1, -1, -1, 0, 0, 1, 1, 1 };

        List<Pair<Integer, Integer>> validMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            int new_x = king_x + dx[i];
            int new_y = king_y + dy[i];

            if (new_x >= 0 && new_x < size && new_y >= 0 && new_y < size) {
                validMoves.add(new Pair<>(new_x, new_y));
            }
        }

        if (!validMoves.isEmpty()) {
            int randomIndex = (int) (Math.random() * validMoves.size());
            this.king = validMoves.get(randomIndex);
        }
    }

    private boolean checkOverKing(final int x, final int y) {
        final int king_x = king.getX();
        final int king_y = king.getY();
        final int rook_x = rook.getX();
        final int rook_y = rook.getY();

        if (rook_x > king_x) {
            if (rook_y > king_y) {
                return x >= king_x && y >= king_y;
            }
            return x >= king_x && y <= king_y;
        }
        if (rook_y < king_y) {
            return x <= king_x && y <= king_y;
        }
        return x <= king_x && y >= king_y;
    }

    @Override
    public Pair<Integer, Integer> getRook() {
        return new Pair<Integer, Integer>(this.rook.getX(), this.rook.getY());
    }

    @Override
    public Pair<Integer, Integer> getKing() {
        return new Pair<Integer, Integer>(this.king.getX(), this.king.getY());
    }

}
