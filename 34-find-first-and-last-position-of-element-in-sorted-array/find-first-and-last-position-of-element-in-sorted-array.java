class Solution {
    public int[] searchRange(int[] nums, int target) {
        int first = findBound(nums, target, true);
        
        // If the target doesn't exist, no need to search for the ending position
        if (first == -1) {
            return new int[]{-1, -1};
        }
        
        int last = findBound(nums, target, false);
        return new int[]{first, last};
    }
    
    private int findBound(int[] nums, int target, boolean isFirst) {
        int left = 0;
        int right = nums.length - 1;
        int bound = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                bound = mid;
                if (isFirst) {
                    right = mid - 1; // Keep searching left for the first occurrence
                } else {
                    left = mid + 1;  // Keep searching right for the last occurrence
                }
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return bound;
    }
}