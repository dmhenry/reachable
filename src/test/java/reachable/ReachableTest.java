package reachable;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnitParamsRunner.class)
public class ReachableTest {

    private List<int[]> rows = new ArrayList<>();
    private Integer steps;
    private Integer expected;

    @Test
    @Parameters("src/test/resources/zeroReachable.txt")
    public void zeroReachable(String infilePath) throws IOException {
        readInput(infilePath);

        int[][] matrix = convertToArray(rows);
        Reachable reachable = new Reachable(matrix, steps);

        assertEquals(expected, Integer.valueOf(reachable.getCount()));
    }

    @Test
    @Parameters("src/test/resources/zeroSteps.txt")
    public void zeroSteps(String infilePath) throws IOException {
        readInput(infilePath);

        int[][] matrix = convertToArray(rows);
        Reachable reachable = new Reachable(matrix, steps);

        assertEquals(expected, Integer.valueOf(reachable.getCount()));
    }

    @Test
    @Parameters("src/test/resources/simpleOneStep.txt")
    public void simpleOneStep(String infilePath) throws IOException {
        readInput(infilePath);

        int[][] matrix = convertToArray(rows);
        Reachable reachable = new Reachable(matrix, steps);

        assertEquals(expected, Integer.valueOf(reachable.getCount()));
    }

    @Test
    @Parameters("src/test/resources/twoStepsMoreValues.txt")
    public void twoStepsMoreValues(String infilePath) throws IOException {
        readInput(infilePath);

        int[][] matrix = convertToArray(rows);
        Reachable reachable = new Reachable(matrix, steps);

        assertEquals(expected, Integer.valueOf(reachable.getCount()));
    }

    @Test
    @Parameters("src/test/resources/threeStepsRagged.txt")
    public void threeStepsRagged(String infilePath) throws IOException {
        readInput(infilePath);

        int[][] matrix = convertToArray(rows);
        Reachable reachable = new Reachable(matrix, steps);

        assertEquals(expected, Integer.valueOf(reachable.getCount()));
    }

    @Test
    @Parameters("src/test/resources/largeStepsZeroReachable.txt")
    public void largeStepsZeroReachable(String infilePath) throws IOException {
        readInput(infilePath);

        int[][] matrix = convertToArray(rows);
        Reachable reachable = new Reachable(matrix, steps);

        assertEquals(expected, Integer.valueOf(reachable.getCount()));
    }

    @Test
    @Parameters("src/test/resources/fullyReachableByShortcut.txt")
    public void fullyReachableByShortcut(String infilePath) throws IOException {
        readInput(infilePath);

        int[][] matrix = convertToArray(rows);
        Reachable reachable = new Reachable(matrix, steps);

        assertEquals(expected, Integer.valueOf(reachable.getCount()));
    }

    @Test(expected = NullPointerException.class)
    public void nullMatrix() {
        new Reachable(null, steps);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidSteps() {
        new Reachable(new int[][]{}, -1);
    }

    private void readInput(String infilePath) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(infilePath))) {
            for (String inputLine : stream.collect(Collectors.toList())) {
                if (inputLine.isEmpty() || inputLine.split("\\s*").length == 0) {
                    continue;
                }

                if (steps == null) {
                    steps = Integer.parseInt(inputLine);
                } else if (expected == null) {
                    expected = Integer.parseInt(inputLine);
                } else {
                    String[] stringValues = inputLine.split("\\s+");
                    int[] row = new int[stringValues.length];

                    for (int i = 0; i < stringValues.length; ++i) {
                        row[i] = Integer.parseInt(stringValues[i]);
                    }
                    rows.add(row);
                }
            }

            assertNotNull("Invalid test file format: " + infilePath, steps);
            assertNotNull("Invalid test file format: " + infilePath, expected);
        }
    }

    private int[][] convertToArray(List<int[]> rows) {
        int[][] matrix = new int[rows.size()][];

        for (int i = 0; i < rows.size(); ++i) {
            matrix[i] = rows.get(i);
        }
        return matrix;
    }

}
