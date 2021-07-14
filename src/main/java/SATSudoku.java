import com.google.ortools.Loader;
import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import com.google.ortools.sat.IntVar;


public class SATSudoku {

    public static final int EXPECTED_ROW_AND_COL_SIZE = 9;
    public static final int SUDOKU_ENTRY_INCLUSIVE_LOWER_BOUND = 1;
    public static final int SUDOKU_ENTRY_INCLUSIVE_UPPER_BOUND = 9;
    public static final int SUB_GRID_LENGTH = 3;

    private IntVar[][] positionalVars;

    public int[][] solveMyPuzzle(int[][] inputGrid) {
        // load native Java jars.
        Loader.loadNativeLibraries();
        final CpModel model = new CpModel();
        // define each positionalVariable to represent an entry on the Sudoku table.
        positionalVars = new IntVar[EXPECTED_ROW_AND_COL_SIZE][EXPECTED_ROW_AND_COL_SIZE];
        for (int row = 0; row < EXPECTED_ROW_AND_COL_SIZE; row++)
            for (int col = 0; col < EXPECTED_ROW_AND_COL_SIZE; col++) {
                boolean toConstrainOrNotToConstrain = inputGrid[row][col] == -1;
                // iterate over our grid, if the grid already has a pre-defined value
                // constraint the slot to be == saidValue --> >= saidValue && <= saidValue
                // otherwise constrain the value between 1 & 9.
                positionalVars[row][col] = model.newIntVar(
                        (toConstrainOrNotToConstrain) ? SUDOKU_ENTRY_INCLUSIVE_LOWER_BOUND : inputGrid[row][col],
                        (toConstrainOrNotToConstrain) ? SUDOKU_ENTRY_INCLUSIVE_UPPER_BOUND : inputGrid[row][col],
                        String.format("Entry at (row,col): (%s, %s).", row, col));
            }

        // iterate over our grid, and constrain rows, columns, and sub-grids to contain unique entries.
        for (int readIndex = 0; readIndex < EXPECTED_ROW_AND_COL_SIZE; readIndex++) {
            // fetch readIndex-th row.
            IntVar[] singleRow = fetchEntries(readIndex, 0, 1, EXPECTED_ROW_AND_COL_SIZE);
            // fetch readIndex-th col.
            IntVar[] singleColumn = fetchEntries(0, readIndex, EXPECTED_ROW_AND_COL_SIZE, 1);
            // fetch readIndex-th subgrid, going from left to right.
            IntVar[] singleSubGrid = fetchEntries(
                    (readIndex / SUB_GRID_LENGTH) * SUB_GRID_LENGTH,
                     (readIndex * SUB_GRID_LENGTH) % EXPECTED_ROW_AND_COL_SIZE,
                             SUB_GRID_LENGTH,
                             SUB_GRID_LENGTH
            );
            model.addAllDifferent(singleRow);
            model.addAllDifferent(singleColumn);
            model.addAllDifferent(singleSubGrid);
        }

        // Standard output decoding, nothing special here.
        CpSolver solver = new CpSolver();
        CpSolverStatus status = solver.solve(model);
        if (status == CpSolverStatus.FEASIBLE || status == CpSolverStatus.OPTIMAL) {
            int[][] decodedOutput = new int[EXPECTED_ROW_AND_COL_SIZE][EXPECTED_ROW_AND_COL_SIZE];
            for (int row = 0; row < EXPECTED_ROW_AND_COL_SIZE; row++)
                for (int col = 0; col < EXPECTED_ROW_AND_COL_SIZE; col++) {
                    decodedOutput[row][col] = (int) solver.value(positionalVars[row][col]);
                }
            return decodedOutput;
        } else {
            return new int[][] {{}};
        }
    }

    // helper method to read rectangular blocks from array.
    // reads as : go to entry [startRow][startCol], and read seekColSize entries from each row,
    // for seekRowSize rows.
    private IntVar[] fetchEntries(int startRow, int startCol, int seekRowSize, int seekColSize) {
        IntVar[] fetchedEntries = new IntVar[seekRowSize * seekColSize];
        int row = 0; int col = 0; int writeIndex = 0;
        while (row < seekRowSize) {
            while (col < seekColSize) {
                fetchedEntries[writeIndex++] =
                        positionalVars[(startRow + row) % EXPECTED_ROW_AND_COL_SIZE][(startCol + col++) % EXPECTED_ROW_AND_COL_SIZE];
            }
            row++; col = 0;
        }
        return fetchedEntries;
    }
}
