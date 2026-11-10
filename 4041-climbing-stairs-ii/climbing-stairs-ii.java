import java.util.Arrays;

class Solution {
    public int climbStairs(int n, int[] costs) {
        int[] dp = new int[n + 1];
        // Use half of Integer.MAX_VALUE to prevent integer overflow during addition
        final int INF = Integer.MAX_VALUE / 2;
        Arrays.fill(dp, INF);
        
        // Base case: 0 cost to start at step 0
        dp[0] = 0;
        
        for (int i = 1; i <= n; i++) {
            int stepCost = costs[i - 1]; // 1-indexed conversion
            
            // Try jumps of size 1, 2, and 3 steps back
            for (int j = Math.max(0, i - 3); j < i; j++) {
                int jumpCost = (i - j) * (i - j);
                dp[i] = Math.min(dp[i], dp[j] + stepCost + jumpCost);
            }
        }
        
        return dp[n];
    }
}