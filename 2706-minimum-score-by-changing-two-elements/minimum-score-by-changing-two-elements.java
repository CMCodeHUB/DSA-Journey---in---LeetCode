import java.util.Arrays;

class Solution {
    public int minimizeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        
        // Option 1: Change the two largest elements to match nums[n - 3]
        int changeTwoLargest = nums[n - 3] - nums[0];
        
        // Option 2: Change the two smallest elements to match nums[2]
        int changeTwoSmallest = nums[n - 1] - nums[2];
        
        // Option 3: Change the smallest and largest elements to match nums[1] and nums[n - 2]
        int changeBothEnds = nums[n - 2] - nums[1];
        
        return Math.min(changeTwoLargest, Math.min(changeTwoSmallest, changeBothEnds));
    }
}