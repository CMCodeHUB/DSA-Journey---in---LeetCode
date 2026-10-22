
class Solution:
    def minOperations(self, nums):
        n = len(nums)
        ans = float('inf')

        k = nums.index(0)
        if all(nums[(k + i) % n] == i for i in range(n)):
            ans = min(ans, k if k == 0 else min(k, n - k + 2))

        rev = nums[::-1]
        k = rev.index(0)
        if all(rev[(k + i) % n] == i for i in range(n)):
            ans = min(ans, min(k, n - k) + 1)

        return ans if ans != float('inf') else -1
