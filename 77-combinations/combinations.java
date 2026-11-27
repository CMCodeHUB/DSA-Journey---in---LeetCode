import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> combine(int n, int k) {
        int total = binomial(n, k);
        List<List<Integer>> result = new ArrayList<>(total);

        // Reusable primitive buffer initialized to the first combination [1, 2, ..., k]
        int[] comb = new int[k];
        for (int i = 0; i < k; i++) {
            comb[i] = i + 1;
        }

        while (true) {
            // Snapshot current combination into an exact-sized List<Integer>
            Integer[] snapshot = new Integer[k];
            for (int i = 0; i < k; i++) {
                snapshot[i] = comb[i];
            }
            result.add(Arrays.asList(snapshot));

            // Find the rightmost index i that can be incremented without exceeding n - k + i + 1
            int i = k - 1;
            while (i >= 0 && comb[i] == n - k + i + 1) {
                i--;
            }

            // All combinations generated
            if (i < 0) {
                break;
            }

            // Advance comb[i] and reset trailing elements to minimal consecutive values
            comb[i]++;
            for (int j = i + 1; j < k; j++) {
                comb[j] = comb[j - 1] + 1;
            }
        }

        return result;
    }

    private int binomial(int n, int k) {
        if (k > n - k) {
            k = n - k;
        }
        long res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (n - i + 1) / i;
        }
        return (int) res;
    }
}