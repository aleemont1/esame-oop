package a01a.e2;

public interface Logics {

    /**
     * Getter for turn.
     * @return the current turn.
     */
    public boolean getTurn();

    /**
     * Setter for turn.
     * @param turn the new turn.
     */
    public void setTurn(final boolean turn);

    /**
     * Checks wether the current cell is hittable.
     * A cell is hittable if:
     * it has not been already hit;
     * the bottom cell has already been hit.
     * 
     * @param position the position of the cell in the grid (0-8)
     * @return true if the cell is hittable, false if not.
     */
    public boolean hit(int x, int y);

    /**
     * Checks if there are 3 consecutive cells hit by the same player.
     * @return true if the game is over, false if not.
     */
    public boolean isOver();
}
