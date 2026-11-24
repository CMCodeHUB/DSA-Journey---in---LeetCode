class Solution {
    // Prime factor bitmasks for values 0..10 (primes: 2->1, 3->2, 5->4, 7->8)
    private static final int[] PRIME_MASK = {
        0, // 0 (unused)
        0, // 1: no prime factors
        1, // 2: 2
        2, // 3: 3
        1, // 4: 2
        4, // 5: 5
        3, // 6: 2 * 3
        8, // 7: 7
        1, // 8: 2
        2, // 9: 3
        5  // 10: 2 * 5
    };

    public int maxLength(int[] nums) {
        int n = nums.length;
        // Every subarray of length 2 satisfies a * b == lcm(a, b) * gcd(a, b)
        int maxLen = 2;
        int windowMask = 0;
        int left = 0;

        for (int right = 0; right < n; right++) {
            int curMask = PRIME_MASK[nums[right]];

            // Shrink window until nums[right] is coprime with all elements in [left, right - 1]
            while ((windowMask & curMask) != 0) {
                windowMask ^= PRIME_MASK[nums[left]];
                left++;
            }

            windowMask |= curMask;
            int currentLen = right - left + 1;
            if (currentLen > maxLen) {
                maxLen = currentLen;
            }
        }

        return maxLen;
    }
}