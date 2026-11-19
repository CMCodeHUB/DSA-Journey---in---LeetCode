import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        // Sort to group duplicates together and enable early pruning
        Arrays.sort(candidates);
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] candidates, int remain, int start, List<Integer> path, List<List<Integer>> result) {
        if (remain == 0) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // Skip duplicate elements at the same recursion depth
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }

            // Early pruning: no subsequent candidate can fit into the remaining target
            if (candidates[i] > remain) {
                break;
            }

            path.add(candidates[i]);
            // Pass 'i + 1' because each element in candidates may only be used once
            backtrack(candidates, remain - candidates[i], i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}