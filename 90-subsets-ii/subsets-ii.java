import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);

        // Pre-fetch boxed Integer references once ([-10, 10] hits JVM IntegerCache)
        Integer[] boxed = new Integer[n];
        for (int i = 0; i < n; i++) {
            boxed[i] = nums[i];
        }

        List<List<Integer>> result = new ArrayList<>(1 << n);
        result.add(Collections.emptyList());

        int prevStart = 0;
        for (int i = 0; i < n; i++) {
            int currSize = result.size();
            int start = (i > 0 && nums[i] == nums[i - 1]) ? prevStart : 0;
            prevStart = currSize;

            Integer val = boxed[i];
            for (int j = start; j < currSize; j++) {
                List<Integer> base = result.get(j);
                int baseLen = base.size();
                Integer[] next = new Integer[baseLen + 1];

                for (int k = 0; k < baseLen; k++) {
                    next[k] = base.get(k);
                }
                next[baseLen] = val;

                result.add(Arrays.asList(next));
            }
        }

        return result;
    }
}