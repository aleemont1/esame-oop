package a01a.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LogicsImpl implements Logics {

    private boolean turn = true;
    private String player = "O";
	private final Map<Pair<Integer, Integer>, String> cells = new HashMap<>();

    LogicsImpl() {
    }

    @Override
    public boolean getTurn() {
        return this.turn;
    }

    @Override
    public void setTurn(boolean turn) {
        this.turn = turn;
        this.player = turn ? "O" : "X";
    }

    @Override
    public boolean hit(int x, int y) {
        var p = new Pair<Integer,Integer>(x,y);
        if(!cells.containsKey(p) && )
        return false;
    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOver'");
    }

}
