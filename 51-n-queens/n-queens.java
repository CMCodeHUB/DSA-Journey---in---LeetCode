import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> results = new ArrayList<>();
        int[] queens = new int[n]; // queens[row] = column index of placed queen
        backtrack(0, n, 0, 0, 0, queens, results);
        return results;
    }

    private void backtrack(int row, int n, int cols, int diag1, int diag2,
                           int[] queens, List<List<String>> results) {
        if (row == n) {
            results.add(buildBoard(queens, n));
            return;
        }

        // Bitmask of all safe columns in the current row
        int available = ((1 << n) - 1) & ~(cols | diag1 | diag2);

        while (available != 0) {
            // Isolate the rightmost safe column bit
            int bit = available & -available;
            // Clear that bit for the next iteration
            available &= available - 1;

            queens[row] = Integer.numberOfTrailingZeros(bit);

            backtrack(
                row + 1,
                n,
                cols | bit,
                (diag1 | bit) << 1,
                (diag2 | bit) >>> 1,
                queens,
                results
            );
        }
    }

    private List<String> buildBoard(int[] queens, int n) {
        List<String> board = new ArrayList<>(n);
        char[] rowChars = new char[n];
        Arrays.fill(rowChars, '.');

        for (int r = 0; r < n; r++) {
            int c = queens[r];
            rowChars[c] = 'Q';
            board.add(new String(rowChars));
            rowChars[c] = '.'; // Reset for next row
        }
        return board;
    }
}