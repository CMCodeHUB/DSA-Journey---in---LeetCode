import java.util.*;

class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        int[][] up = new int[LOG][n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] baseMask = new int[n];
        int[] parent = new int[n];

        char[] chars = s.toCharArray();
        int timer = 0;

        // Iterative DFS avoids stack overflow on a deep tree.
        int[] stack = new int[2 * n + 5];
        int top = 0;
        stack[top++] = 0;

        while (top > 0) {
            int event = stack[--top];

            if (event < 0) {
                int u = ~event;
                tout[u] = timer - 1;
                continue;
            }

            int u = event;
            tin[u] = timer++;

            up[0][u] = parent[u];
            for (int j = 1; j < LOG; j++) {
                up[j][u] = up[j - 1][up[j - 1][u]];
            }

            stack[top++] = ~u;

            for (int v : graph[u]) {
                if (v == parent[u]) continue;

                parent[v] = u;
                depth[v] = depth[u] + 1;
                baseMask[v] = baseMask[u] ^ (1 << (chars[v] - 'a'));
                stack[top++] = v;
            }
        }

        Fenwick bit = new Fenwick(n + 2);
        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");
            int u = Integer.parseInt(parts[1]);

            if (parts[0].equals("update")) {
                char c = parts[2].charAt(0);

                int delta = (1 << (chars[u] - 'a')) ^ (1 << (c - 'a'));

                if (delta != 0) {
                    bit.rangeXor(tin[u], tout[u], delta);
                    chars[u] = c;
                }
            } else {
                int v = Integer.parseInt(parts[2]);
                int lca = getLCA(u, v, depth, up, LOG);

                int maskU = baseMask[u] ^ bit.query(tin[u]);
                int maskV = baseMask[v] ^ bit.query(tin[v]);

                int mask = maskU ^ maskV ^ (1 << (chars[lca] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }

    private int getLCA(int u, int v, int[] depth, int[][] up, int LOG) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    static class Fenwick {
        int[] tree;
        int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 2];
        }

        void update(int i, int val) {
            for (; i <= n; i += i & -i) {
                tree[i] ^= val;
            }
        }

        void rangeXor(int left, int right, int val) {
            update(left + 1, val);
            update(right + 2, val);
        }

        int query(int index) {
            int result = 0;

            for (int i = index + 1; i > 0; i -= i & -i) {
                result ^= tree[i];
            }

            return result;
        }
    }
}