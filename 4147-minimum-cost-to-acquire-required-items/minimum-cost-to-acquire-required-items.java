class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long c1 = cost1;
        long c2 = cost2;
        long cb = costBoth;
        long n1 = need1;
        long n2 = need2;
        
        long minTotalCost = Long.MAX_VALUE;
        
        // Candidate values for the number of type 3 items (z)
        long[] zCandidates = {0L, n1, n2};
        
        for (long z : zCandidates) {
            long x = Math.max(0L, n1 - z);
            long y = Math.max(0L, n2 - z);
            long currentCost = x * c1 + y * c2 + z * cb;
            minTotalCost = Math.min(minTotalCost, currentCost);
        }
        
        return minTotalCost;
    }
}