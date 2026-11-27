import java.util.Arrays;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int maxFactor = 0;
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            if (item[0] > maxFactor) {
                maxFactor = item[0];
            }
            if (item[1] < minPrice) {
                minPrice = item[1];
            }
        }

        // 1. Count occurrences of each factor in O(n)
        int[] factorFreq = new int[maxFactor + 1];
        for (int[] item : items) {
            factorFreq[item[0]]++;
        }

        // 2. Harmonic sieve: multiplesCount[f] = total items whose factor is divisible by f
        // Buying 1st copy of item i yields 1 purchased + (multiplesCount[factor[i]] - 1) free
        // = multiplesCount[factor[i]] total items
        int[] multiplesCount = new int[maxFactor + 1];
        for (int f = 1; f <= maxFactor; f++) {
            int sum = 0;
            for (int mult = f; mult <= maxFactor; mult += f) {
                sum += factorFreq[mult];
            }
            multiplesCount[f] = sum;
        }

        // 3. 0/1 Knapsack over "first-copy" purchases (cost = price[i], value = multiplesCount[factor[i]])
        int[] dp = new int[budget + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        for (int[] item : items) {
            int price = item[1];
            if (price > budget) {
                continue;
            }
            int firstCopyValue = multiplesCount[item[0]];

            for (int w = budget; w >= price; w--) {
                if (dp[w - price] != -1) {
                    int candidate = dp[w - price] + firstCopyValue;
                    if (candidate > dp[w]) {
                        dp[w] = candidate;
                    }
                }
            }
        }

        // 4. Fill remaining budget (budget - w) with copies of the globally cheapest item
        int maxTotal = 0;
        for (int w = 0; w <= budget; w++) {
            if (dp[w] != -1) {
                int total = dp[w] + (budget - w) / minPrice;
                if (total > maxTotal) {
                    maxTotal = total;
                }
            }
        }

        return maxTotal;
    }
}