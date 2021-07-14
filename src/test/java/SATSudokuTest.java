import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

public class SATSudokuTest {

    private static SATSudoku SAT_SUDOKU;

    @BeforeAll
    static void setup() {
        SAT_SUDOKU = new SATSudoku();
    }

    @ParameterizedTest
    @MethodSource("encodedSudokuPuzzleStream")
    public void aTest(int[][] sudokuPuzzles, int[][] answerKey) {
        int[][] solvedPuzzle = SAT_SUDOKU.solveMyPuzzle(sudokuPuzzles);
        Assertions.assertTrue(Arrays.deepEquals(answerKey, solvedPuzzle));
    }

    private static Stream<Arguments> encodedSudokuPuzzleStream() {
        return Stream.of(
                    // an easy puzzle according to the site I got it from.
                    Arguments.arguments(
                        new int[][] {{-1, -1, 4, -1, 5, -1, -1, -1, -1},
                                {9, -1, -1, 7, 3, 4, 6, -1, -1},
                                {-1, -1, 3, -1, 2, 1, -1, 4, 9},
                                {-1, 3, 5, -1, 9, -1, 4, 8, -1},
                                {-1, 9, -1, -1, -1, -1, -1, 3, -1},
                                {-1, 7, 6, -1, 1, -1, 9, 2, -1},
                                {3, 1, -1, 9, 7, -1, 2, -1, -1},
                                {-1, -1, 9, 1, 8, 2, -1, -1, 3},
                                {-1, -1, -1, -1, 6, -1, 1, -1, -1}},

                        new int[][] {{2, 6, 4, 8, 5, 9, 3, 1, 7},
                                {9, 8, 1, 7, 3, 4, 6, 5, 2},
                                {7, 5, 3, 6, 2, 1, 8, 4, 9},
                                {1, 3, 5, 2, 9, 7, 4, 8, 6},
                                {8, 9, 2, 5, 4, 6, 7, 3, 1},
                                {4, 7, 6, 3, 1, 8, 9, 2, 5},
                                {3, 1, 8, 9, 7, 5, 2, 6, 4},
                                {6, 4, 9, 1, 8, 2, 5, 7, 3},
                                {5, 2, 7, 4, 6, 3, 1, 9, 8}}
                    ),
                    // a medium difficulty puzzle.
                    Arguments.arguments(
                        new int[][] {{3, 4, -1, -1, 6, -1, 2, -1, 9},
                                {2, -1, 8, 4, 9, -1, -1, -1, 6},
                                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                                {-1, 2, -1, 3, 1, -1, -1, -1, -1},
                                {-1, -1, 4, -1, -1, -1, 1, -1, -1},
                                {-1, -1, -1, -1, 2, 5, -1, 4, -1},
                                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                                {9, -1, -1, -1, 5, 1, 4, -1, 3},
                                {4, -1, 3, -1, 7, -1, -1, 6, 8}},

                        new int[][] {{3, 4, 7, 1, 6, 8, 2, 5, 9},
                                {2, 5, 8, 4, 9, 7, 3, 1, 6},
                                {1, 6, 9, 5, 3, 2, 7, 8, 4},
                                {7, 2, 6, 3, 1, 4, 8, 9, 5},
                                {5, 9, 4, 7, 8, 6, 1, 3, 2},
                                {8, 3, 1, 9, 2, 5, 6, 4, 7},
                                {6, 7, 5, 8, 4, 3, 9, 2, 1},
                                {9, 8, 2, 6, 5, 1, 4, 7, 3},
                                {4, 1, 3, 2, 7, 9, 5, 6, 8}}
                ),
                    // a hard puzzle.
                    Arguments.arguments(
                        new int[][] {{-1, -1, 1, 3, -1, 2, -1, -1, -1},
                                {-1, -1, 3, -1, -1, 7, -1, 4, 5},
                                {-1, -1, 7, -1, -1, -1, -1, -1, 9},
                                {-1, -1, 6, 5, -1, -1, -1, 7, -1},
                                {2, -1, -1, -1, -1, -1, -1, -1, 1},
                                {-1, 9, -1, -1, -1, 1, 4, -1, -1},
                                {5, -1, -1, -1, -1, -1, 9, -1, -1},
                                {6, 1, -1, 2, -1, -1, 8, -1, -1},
                                {-1, -1, -1, 9, -1, 8, 5, -1, -1}},

                        new int[][] {{4, 5, 1, 3, 9, 2, 7, 8, 6},
                                {9, 2, 3, 8, 6, 7, 1, 4, 5},
                                {8, 6, 7, 1, 5, 4, 3, 2, 9},
                                {1, 3, 6, 5, 4, 9, 2, 7, 8},
                                {2, 4, 5, 7, 8, 3, 6, 9, 1},
                                {7, 9, 8, 6, 2, 1, 4, 5, 3},
                                {5, 8, 2, 4, 3, 6, 9, 1, 7},
                                {6, 1, 9, 2, 7, 5, 8, 3, 4},
                                {3, 7, 4, 9, 1, 8, 5, 6, 2}}
                )
        );
    }
}
