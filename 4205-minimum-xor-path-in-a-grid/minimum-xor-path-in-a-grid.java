import java.util.*;

class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        BitSet[][] dp = new BitSet[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = new BitSet(1024);

                if (i == 0 && j == 0) {
                    dp[i][j].set(grid[i][j]);
                    continue;
                }

                if (i > 0) {
                    for (int x = dp[i - 1][j].nextSetBit(0);
                         x >= 0;
                         x = dp[i - 1][j].nextSetBit(x + 1)) {
                        dp[i][j].set(x ^ grid[i][j]);
                    }
                }

                if (j > 0) {
                    for (int x = dp[i][j - 1].nextSetBit(0);
                         x >= 0;
                         x = dp[i][j - 1].nextSetBit(x + 1)) {
                        dp[i][j].set(x ^ grid[i][j]);
                    }
                }
            }
        }

        return dp[m - 1][n - 1].nextSetBit(0);
    }
}