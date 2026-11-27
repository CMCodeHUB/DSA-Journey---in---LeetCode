class Solution {
    public boolean search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int mid = low + ((high - low) >>> 1);
            int midVal = nums[mid];

            if (midVal == target) {
                return true;
            }

            int lowVal = nums[low];
            int highVal = nums[high];

            // Ambiguous duplicate endpoints: shrink search window from both ends
            if (lowVal == midVal && midVal == highVal) {
                low++;
                high--;
            } else if (lowVal <= midVal) {
                // Left half [low..mid] is sorted in non-decreasing order
                if (lowVal <= target && target < midVal) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                // Right half [mid..high] is sorted in non-decreasing order
                if (midVal < target && target <= highVal) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return false;
    }
}