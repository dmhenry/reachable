package reachable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Determines how many two-dimensional array elements are reachable from any
 * positive element, provided an allowable number of steps. Assumes the only
 * valid single moves are up, right, down, left (diagonals count as two moves).
 * This implementation works with ragged arrays.
 */
class Reachable {

    private final Set<Coordinate> reachableCoordinates = new HashSet<>();
    private Integer shortcutValue;

    Reachable(int[][] matrix, int steps) {
        validateInput(matrix, steps);

        tryShortcut(matrix, steps);

        if (shortcutValue == null) {
            for (int row = 0; row < matrix.length; ++row) {
                for (int col = 0; col < matrix[row].length; ++col) {
                    if (matrix[row][col] > 0) {
                        visit(matrix, row, col, steps);
                    }
                }
            }
        }
    }

    /*
     * Visits matrix[row][col]. Called recursively to reach all possible nodes,
     * depending upon the input matrix shape and contents and the number of
     * remaining moves. Works with ragged arrays.
     */
    private void visit(int[][] matrix, int row, int col, int remainingMoves) {
        reachableCoordinates.add(new Coordinate(row, col));

        if (remainingMoves > 0) {
            if (row - 1 >= 0 && col < matrix[row - 1].length) {
                /* Second condition ensures correct behavior with ragged arrays. */
                visit(matrix, row - 1, col, remainingMoves - 1); // up
            }
            if (col + 1 < matrix[row].length) {
                visit(matrix, row, col + 1, remainingMoves - 1); // right
            }
            if (row + 1 < matrix.length && col < matrix[row + 1].length) {
                /* Second condition ensures correct behavior with ragged arrays. */
                visit(matrix, row + 1, col, remainingMoves - 1); // down
            }
            if (col - 1 >= 0) {
                visit(matrix, row, col - 1, remainingMoves - 1); // left
            }
        }
    }

    int getCount() {
        return (shortcutValue != null) ? shortcutValue : reachableCoordinates.size();
    }

    private void validateInput(int[][] matrix, int steps) {
        if (steps < 0) {
            throw new IllegalArgumentException("steps must be non-negative: steps=" + steps);
        }
        if (matrix == null) {
            throw new NullPointerException("matrix must be non-null");
        }
    }

    /*
     * This is an optimization and is not strictly necessary for correct
     * function. If the matrix is rectangular, i.e., each row has the same
     * length, then provided a sufficiently large value for steps, any positive
     * value implies that every element of the matrix is reachable. This can
     * save computation and potentially prevent a very large number of steps
     * from overflowing the stack.
     *
     * Example: In a 5x4 matrix, only 7 steps are required to make it from one
     * corner to its diagonal; the farthest distance between any two elements.
     * Thus, any integer number of steps greater than 6 makes any two elements
     * mutually reachable, provided at least one positive value exists. If no
     * positive values exist then, of course, no elements are reachable.
     */
    private void tryShortcut(int[][] matrix, int steps) {
        int firstRowLength = matrix[0].length;
        boolean uniformLength = true;

        if (steps > matrix.length + firstRowLength - 3) {
            /* Step size is large enough that a single positive value makes
             * any element in a rectangular matrix reachable. */

            /* Ensure uniform length */
            for (int row = 1; row < matrix.length; ++row) {
                if (matrix[row].length != firstRowLength) {
                    uniformLength = false;
                    break;
                }
            }

            if (uniformLength) {
                shortcutValue = 0; // set default for no positive value found

                outer:
                /* Try to find a single positive value. */
                for (int row = 0; row < matrix.length; ++row) {
                    for (int col = 0; col < matrix[row].length; ++col) {
                        if (matrix[row][col] > 0) {
                            shortcutValue = matrix.length * matrix[row].length;
                            break outer;
                        }
                    }
                }
            }
        }
    }

    /*
     * A Cartesian pair.
     * equals() and hashCode() are overridden to ensure correct Set behavior
     */
    private class Coordinate {

        private final int x, y;

        private Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Coordinate)) {
                return false;
            }

            Coordinate that = (Coordinate) other;
            return (x == that.x) && (y == that.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

}
