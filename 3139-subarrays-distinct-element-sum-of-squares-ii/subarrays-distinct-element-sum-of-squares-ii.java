class Solution {
    static final long MOD = 1_000_000_007L;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        long[] sum = new long[4 * n];
        long[] square = new long[4 * n];
        long[] lazy = new long[4 * n];
        int[] last = new int[100001];

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int left = last[nums[i]];
            update(1, 0, n - 1, left, i, sum, square, lazy);

            ans = (ans + square[1]) % MOD;
            last[nums[i]] = i + 1;
        }

        return (int) ans;
    }

    private void update(int node, int l, int r, int ql, int qr,
                        long[] sum, long[] square, long[] lazy) {
        if (ql <= l && r <= qr) {
            long len = r - l + 1L;
            square[node] = (square[node] + 2 * sum[node] + len) % MOD;
            sum[node] = (sum[node] + len) % MOD;
            lazy[node]++;
            return;
        }

        push(node, l, r, sum, square, lazy);

        int mid = (l + r) / 2;

        if (ql <= mid)
            update(node * 2, l, mid, ql, qr, sum, square, lazy);

        if (qr > mid)
            update(node * 2 + 1, mid + 1, r, ql, qr, sum, square, lazy);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        square[node] = (square[node * 2] + square[node * 2 + 1]) % MOD;
    }

    private void push(int node, int l, int r, long[] sum,
                      long[] square, long[] lazy) {
        if (lazy[node] == 0 || l == r) return;

        int mid = (l + r) / 2;
        apply(node * 2, l, mid, lazy[node], sum, square, lazy);
        apply(node * 2 + 1, mid + 1, r, lazy[node], sum, square, lazy);

        lazy[node] = 0;
    }

    private void apply(int node, int l, int r, long val,
                       long[] sum, long[] square, long[] lazy) {
        long len = r - l + 1L;
        square[node] = (square[node] + 2 * val % MOD * sum[node]
                        + val * val % MOD * len) % MOD;
        sum[node] = (sum[node] + val * len) % MOD;
        lazy[node] = (lazy[node] + val) % MOD;
    }
}