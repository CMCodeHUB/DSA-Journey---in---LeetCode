import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        long ans = 0;
        long cost = 0;
        // Store indices of `nums` with `nums[i]` in non-increasing order.
        Deque<Integer> dq = new ArrayDeque<>();
        
        for (int i = nums.length - 1, j = nums.length - 1; i >= 0; --i) {
            final int num = nums[i];
            
            // Maintain monotonic stack/deque by popping elements smaller than current `num`
            while (!dq.isEmpty() && nums[dq.peekLast()] < num) {
                int l = dq.pollLast();
                int r = dq.peekLast() == null ? j + 1 : dq.peekLast();
                // Adjust elements in range [l, r-1] to `num`
                cost += (long) (r - l) * (num - nums[l]);
            }
            dq.offerLast(i);
            
            // If cost exceeds k, shrink the window from the right (decrement j)
            while (cost > k) {
                cost -= (long) (nums[dq.peekFirst()] - nums[j]);
                if (dq.peekFirst() == j) {
                    dq.pollFirst();
                }
                --j;
            }
            
            // All subarrays starting at `i` and ending between `i` and `j` are valid
            ans += (long) (j - i + 1);
        }
        
        return ans;
    }
}