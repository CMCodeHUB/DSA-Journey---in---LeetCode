import java.util.Arrays;

class Solution {
    private static final long MOD1 = 1_000_000_007L;
    private static final long MOD2 = 1_000_000_009L;
    private static final long BASE1 = 131_542_391L;
    private static final long BASE2 = 265_443_576L;

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return 1;
        }

        // O(n) fast-path check for length = 1 using direct array counting
        int maxVal = 0;
        for (int x : nums) {
            if (x > maxVal) maxVal = x;
        }
        int[] freq = new int[maxVal + 1];
        for (int x : nums) {
            freq[x]++;
        }
        for (int x : nums) {
            if (freq[x] == 1) {
                return 1;
            }
        }

        // Precompute powers of BASE1 and BASE2 up to n
        long[] pow1 = new long[n + 1];
        long[] pow2 = new long[n + 1];
        pow1[0] = 1L;
        pow2[0] = 1L;
        for (int i = 1; i <= n; i++) {
            pow1[i] = (pow1[i - 1] * BASE1) % MOD1;
            pow2[i] = (pow2[i - 1] * BASE2) % MOD2;
        }

        // Reusable primitive buffer for window hashes
        long[] hashes = new long[n];

        int low = 2;
        int high = n;
        int ans = n;

        while (low <= high) {
            int mid = low + ((high - low) >>> 1);
            if (hasUniqueWindow(nums, mid, pow1, pow2, hashes)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    private boolean hasUniqueWindow(int[] nums, int len, long[] pow1, long[] pow2, long[] hashes) {
        int n = nums.length;
        int windowCount = n - len + 1;
        if (windowCount == 1) {
            return true;
        }

        long h1 = 0L;
        long h2 = 0L;

        // Compute hash of the initial window [0, len - 1]
        for (int i = 0; i < len; i++) {
            h1 = (h1 * BASE1 + nums[i]) % MOD1;
            h2 = (h2 * BASE2 + nums[i]) % MOD2;
        }
        hashes[0] = (h1 << 32) | h2;

        long highestPow1 = pow1[len - 1];
        long highestPow2 = pow2[len - 1];

        // Slide window across nums in O(n)
        for (int i = len; i < n; i++) {
            long outgoing = nums[i - len];
            long incoming = nums[i];

            h1 = (h1 - (outgoing * highestPow1) % MOD1 + MOD1) % MOD1;
            h1 = (h1 * BASE1 + incoming) % MOD1;

            h2 = (h2 - (outgoing * highestPow2) % MOD2 + MOD2) % MOD2;
            h2 = (h2 * BASE2 + incoming) % MOD2;

            hashes[i - len + 1] = (h1 << 32) | h2;
        }

        // Sort primitive long[] slice and check for any hash with multiplicity 1
        Arrays.sort(hashes, 0, windowCount);

        if (hashes[0] != hashes[1]) {
            return true;
        }
        if (hashes[windowCount - 1] != hashes[windowCount - 2]) {
            return true;
        }
        for (int i = 1; i < windowCount - 1; i++) {
            if (hashes[i] != hashes[i - 1] && hashes[i] != hashes[i + 1]) {
                return true;
            }
        }

        return false;
    }
}