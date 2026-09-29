class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Path length must be even
        if ((m + n - 1) % 2 != 0) {
            return false;
        }

        // First and last characters must be '(' and ')'
        if (grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }

        boolean[][][] dp = new boolean[m][n][m + n];

        dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int balance = 0; balance <= m + n - 1; balance++) {

                    if (!dp[i][j][balance]) {
                        continue;
                    }

                    // Move down
                    if (i + 1 < m) {
                        int next = grid[i + 1][j] == '('
                                ? balance + 1
                                : balance - 1;

                        if (next >= 0) {
                            dp[i + 1][j][next] = true;
                        }
                    }

                    // Move right
                    if (j + 1 < n) {
                        int next = grid[i][j + 1] == '('
                                ? balance + 1
                                : balance - 1;

                        if (next >= 0) {
                            dp[i][j + 1][next] = true;
                        }
                    }
                }
            }
        }

        return dp[m - 1][n - 1][0];
    }
}