class Solution {
    public boolean isValidSudoku(char[][] board) {
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];
        
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char cell = board[r][c];
                if (cell == '.') {
                    continue;
                }
                
                int num = cell - '1'; // Map '1'-'9' to 0-8
                int boxIdx = (r / 3) * 3 + (c / 3);
                
                // If the digit already exists in the current row, column, or 3x3 box
                if (rows[r][num] || cols[c][num] || boxes[boxIdx][num]) {
                    return false;
                }
                
                rows[r][num] = true;
                cols[c][num] = true;
                boxes[boxIdx][num] = true;
            }
        }
        
        return true;
    }
}