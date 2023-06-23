package a01b.e2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicImpl implements Logic {
    final Set<Pair<Integer, Integer>> star = new HashSet<>();
    private boolean over = false;
    final int size;

    protected LogicImpl(final int size) {
        this.size = size;
    }

    @Override
    public boolean isOver() {
        return over;
    }

    private final boolean checkSquare(final int x, final int y) {
        int numStars = 0;
        for (int i = 0; i < 4; i++) {
            final int sign = i % 2 == 0 ? 1 : -1;
            if (star.contains(new Pair<>(x + sign, y + sign))) {
                numStars++;
            }
        }
        return numStars == 3;
    }

    @Override
    public boolean isEnabled(final int x, final int y) {
        return star.contains(new Pair<>(x, y));
    }

    public boolean toggle(final int x, final int y) {
        var p = new Pair<>(x, y);
        if (this.star.contains(p)) {
            this.star.remove(p);
            return false;
        }
        this.star.add(p);
        return true;
    }

    @Override
    public void isHit(int x, int y) {
        // Create a list to store the toggles
        List<Boolean> toggles = new ArrayList<>();

        // Define the coordinates of the corner cells
        int[][] cornerCoordinates = {
                { x - 1, y - 1 }, // Top-left
                { x - 1, y + 1 }, // Top-right
                { x + 1, y - 1 }, // Bottom-left
                { x + 1, y + 1 } // Bottom-right
        };

        // Toggle the corner cells and add the results to the toggles list
        for (int[] coordinates : cornerCoordinates) {
            int cornerX = coordinates[0];
            int cornerY = coordinates[1];

            // Check if the corner cell is within the grid boundaries
            if (cornerX >= 0 && cornerX < size && cornerY >= 0 && cornerY < size) {
                boolean result = toggle(cornerX, cornerY);
                toggles.add(result);
            }
        }

        // Count the occurrences of true and false in the toggles list
        int trueCount = 0;
        int falseCount = 0;
        for (boolean toggle : toggles) {
            if (toggle) {
                trueCount++;
            } else {
                falseCount++;
            }
        }

        // Set the over variable based on the conditions
        this.over = toggles.size() == 2 && falseCount == 3 && trueCount == 1;
    }

}
