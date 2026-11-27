import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        int n = nums.length;
        int totalMasks = 1 << n;

        List<List<Integer>> result = new ArrayList<>(totalMasks);

        // Pre-fetch boxed references once (values in [-10, 10] hit JVM IntegerCache)
        Integer[] boxed = new Integer[n];
        for (int i = 0; i < n; i++) {
            boxed[i] = nums[i];
        }

        for (int mask = 0; mask < totalMasks; mask++) {
            int size = Integer.bitCount(mask);
            Integer[] subset = new Integer[size];

            int idx = 0;
            int bits = mask;
            while (bits != 0) {
                int lowestBitIndex = Integer.numberOfTrailingZeros(bits);
                subset[idx++] = boxed[lowestBitIndex];
                bits &= bits - 1; // Clear lowest set bit
            }

            result.add(Arrays.asList(subset));
        }

        return result;
    }
}