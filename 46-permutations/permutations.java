import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, nums, result);
        return result;
    }

    private void backtrack(int first, int[] nums, List<List<Integer>> result) {
        // Base case: all positions are fixed
        if (first == nums.length) {
            List<Integer> permutation = new ArrayList<>(nums.length);
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
            return;
        }

        for (int i = first; i < nums.length; i++) {
            // Place nums[i] at the current 'first' index
            swap(nums, first, i);
            
            // Recursively generate permutations for the remaining subarray
            backtrack(first + 1, nums, result);
            
            // Backtrack: undo the swap to restore original order
            swap(nums, first, i);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}