class Solution {
    public int numberOfSets(int n, int k) {
        final int MOD = 1000000007;

        long[] dp = new long[k + 1];
        long[] sum = new long[k + 1];

        dp[0] = 1;

        for (int i = 1; i < n; i++) {
            for (int j = Math.min(k, i); j >= 1; j--) {
                sum[j] = (sum[j] + dp[j - 1]) % MOD;
                dp[j] = (dp[j] + sum[j]) % MOD;
            }
        }

        return (int) dp[k];
    }
}