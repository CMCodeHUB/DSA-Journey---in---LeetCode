class Solution {
    public int numOfSubarrays(int[] arr) {
        long oddCount = 0;
        long evenCount = 1; // Base case: prefix sum of 0 is even
        long currentSum = 0;
        long result = 0;
        long MOD = 1_000_000_007;
        
        for (int num : arr) {
            currentSum += num;
            
            if (currentSum % 2 != 0) {
                // If current prefix sum is odd, add count of previous even prefix sums
                result = (result + evenCount) % MOD;
                oddCount++;
            } else {
                // If current prefix sum is even, add count of previous odd prefix sums
                result = (result + oddCount) % MOD;
                evenCount++;
            }
        }
        
        return (int) result;
    }
}