class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        
        // 1. Place each number x in [1, n] at its target index (x - 1)
        for (int i = 0; i < n; i++) {
            while (nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        
        // 2. Scan for the first index that doesn't hold (i + 1)
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        
        // 3. If [1..n] are all present, the smallest missing positive is n + 1
        return n + 1;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}