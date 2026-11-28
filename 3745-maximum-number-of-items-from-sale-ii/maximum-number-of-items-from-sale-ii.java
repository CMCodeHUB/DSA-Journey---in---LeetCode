import java.util.Arrays;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        // 1. Count factor frequencies and find global minimum price in O(n)
        int[] factorFreq = new int[n + 1];
        long minPrice = Long.MAX_VALUE;
        for (int[] item : items) {
            factorFreq[item[0]]++;
            if (item[1] < minPrice) {
                minPrice = item[1];
            }
        }

        // 2. Harmonic sieve: multiplesCount[f] = count of items with factor divisible by f
        int[] multiplesCount = new int[n + 1];
        for (int f = 1; f <= n; f++) {
            int sum = 0;
            for (int mult = f; mult <= n; mult += f) {
                sum += factorFreq[mult];
            }
            multiplesCount[f] = sum;
        }

        // 3. Pack (price, bonusCap) into primitive long[] to sort without boxing:
        //    high 32 bits = price (1..10^9), low 32 bits = bonusCap (0..n-1)
        long[] packed = new long[n];
        for (int i = 0; i < n; i++) {
            long price = items[i][1];
            long bonusCap = multiplesCount[items[i][0]] - 1L;
            packed[i] = (price << 32) | (bonusCap & 0xFFFFFFFFL);
        }
        Arrays.sort(packed);

        // 4. Greedily buy 2-for-price bonus copies while price <= 2 * minPrice
        long remBudget = budget;
        long totalItems = 0L;
        long twoPlainCost = 2L * minPrice;

        for (int i = 0; i < n; i++) {
            long price = packed[i] >>> 32;
            long bonusCap = packed[i] & 0xFFFFFFFFL;

            if (bonusCap == 0L) {
                continue;
            }
            // Buying 2 plain items at minPrice is strictly cheaper than 1 bonus copy at price
            if (price > twoPlainCost || remBudget < price) {
                break;
            }

            long maxAffordable = remBudget / price;
            long take = bonusCap < maxAffordable ? bonusCap : maxAffordable;

            totalItems += 2L * take;
            remBudget -= take * price;
        }

        // 5. Convert any remaining budget into 1-for-minPrice plain copies
        totalItems += remBudget / minPrice;

        return (int) totalItems;
    }
}