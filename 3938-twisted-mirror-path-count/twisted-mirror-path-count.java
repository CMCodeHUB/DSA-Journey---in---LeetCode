
class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1_000_000_007;

        int[][] right = new int[m][n];
        int[][] down = new int[m][n];

        // -1 means invalid destination.
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                int id = i * n + j;

                if (grid[i][j] == 0) {
                    right[i][j] = id;
                    down[i][j] = id;
                } else {
                    // Entered this mirror while moving right:
                    // reflection sends the robot down.
                    right[i][j] = (i + 1 < m)
                            ? down[i + 1][j] : -1;

                    // Entered this mirror while moving down:
                    // reflection sends the robot right.
                    down[i][j] = (j + 1 < n)
                            ? right[i][j + 1] : -1;
                }
            }
        }

        long[][] dp = new long[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (dp[i][j] == 0 || grid[i][j] == 1) {
                    continue;
                }

                long ways = dp[i][j];

                // Try moving right.
                if (j + 1 < n) {
                    int dest = right[i][j + 1];

                    if (dest != -1) {
                        int x = dest / n;
                        int y = dest % n;

                        dp[x][y] = (dp[x][y] + ways) % MOD;
                    }
                }

                // Try moving down.
                if (i + 1 < m) {
                    int dest = down[i + 1][j];

                    if (dest != -1) {
                        int x = dest / n;
                        int y = dest % n;

                        dp[x][y] = (dp[x][y] + ways) % MOD;
                    }
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }
}
