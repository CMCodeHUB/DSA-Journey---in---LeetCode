import java.util.Arrays;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        
        int maxVal = nums[nums.length - 1];
        // dp[v] = max length of consecutive sequence ending at value v
        int[] dp = new int[maxVal + 2];
        int maxLen = 1;
        
        for (int num : nums) {
            // Update dp[num + 1] first so we don't reuse the current element twice
            dp[num + 1] = dp[num] + 1;
            dp[num] = dp[num - 1] + 1;
            
            maxLen = Math.max(maxLen, Math.max(dp[num], dp[num + 1]));
        }
        
        return maxLen;
    }
}