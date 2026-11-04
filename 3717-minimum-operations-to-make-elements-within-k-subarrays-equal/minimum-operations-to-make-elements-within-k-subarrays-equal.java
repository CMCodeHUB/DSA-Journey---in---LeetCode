import java.util.*;

class Solution {
    TreeMap<Integer, Integer> low = new TreeMap<>();
    TreeMap<Integer, Integer> high = new TreeMap<>();

    long sumLow = 0, sumHigh = 0;
    int sizeLow = 0, sizeHigh = 0;

    void addLow(int x) {
        low.put(x, low.getOrDefault(x, 0) + 1);
        sumLow += x;
        sizeLow++;
    }

    void addHigh(int x) {
        high.put(x, high.getOrDefault(x, 0) + 1);
        sumHigh += x;
        sizeHigh++;
    }

    void removeLow(int x) {
        int c = low.get(x);
        if (c == 1) low.remove(x);
        else low.put(x, c - 1);
        sumLow -= x;
        sizeLow--;
    }

    void removeHigh(int x) {
        int c = high.get(x);
        if (c == 1) high.remove(x);
        else high.put(x, c - 1);
        sumHigh -= x;
        sizeHigh--;
    }

    void add(int x) {
        if (low.isEmpty() || x <= low.lastKey()) addLow(x);
        else addHigh(x);
        balance();
    }

    void remove(int x) {
        if (low.containsKey(x)) removeLow(x);
        else removeHigh(x);
        balance();
    }

    void balance() {
        while (sizeLow > sizeHigh + 1) {
            int x = low.lastKey();
            removeLow(x);
            addHigh(x);
        }

        while (sizeLow < sizeHigh) {
            int x = high.firstKey();
            removeHigh(x);
            addLow(x);
        }
    }

    long getCost() {
        long median = low.lastKey();
        return median * sizeLow - sumLow
             + sumHigh - median * sizeHigh;
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = new long[n - x + 1];

        low.clear();
        high.clear();
        sumLow = sumHigh = 0;
        sizeLow = sizeHigh = 0;

        // Calculate cost for every window of size x.
        for (int i = 0; i < n; i++) {
            add(nums[i]);

            if (i >= x) {
                remove(nums[i - x]);
            }

            if (i >= x - 1) {
                cost[i - x + 1] = getCost();
            }
        }

        // DP: minimum cost to select t windows in first i elements.
        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (int t = 1; t <= k; t++) {
            Arrays.fill(dp[t], INF);
        }

        for (int i = 0; i <= n; i++) {
            dp[0][i] = 0;
        }

        for (int t = 1; t <= k; t++) {
            for (int i = 1; i <= n; i++) {
                dp[t][i] = dp[t][i - 1];

                if (i >= x) {
                    int start = i - x;

                    if (dp[t - 1][start] != INF) {
                        dp[t][i] = Math.min(
                            dp[t][i],
                            dp[t - 1][start] + cost[start]
                        );
                    }
                }
            }
        }

        return dp[k][n];
    }
}