import java.util.Arrays;

class Solution {
    private int[] head;
    private int[] to;
    private int[] next;
    private int edgeIdx;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        
        // Primitive adjacency list for O(n) memory and zero boxing overhead
        head = new int[n];
        to = new int[n];
        next = new int[n];
        Arrays.fill(head, -1);
        edgeIdx = 0;

        for (int i = 1; i < n; i++) {
            addEdge(parent[i], i);
        }

        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);
        
        int[] answer = new int[n];
        dfs(0, parent, s, lastSeen, answer);
        
        return answer;
    }

    private void addEdge(int u, int v) {
        to[edgeIdx] = v;
        next[edgeIdx] = head[u];
        head[u] = edgeIdx++;
    }

    private void dfs(int u, int[] parent, String s, int[] lastSeen, int[] answer) {
        int charIdx = s.charAt(u) - 'a';
        int prevAncestor = lastSeen[charIdx];
        
        // Closest ancestor with matching character becomes new parent; otherwise keep original
        int finalParent = (prevAncestor != -1) ? prevAncestor : parent[u];
        
        lastSeen[charIdx] = u;
        answer[u] = 1;

        for (int e = head[u]; e != -1; e = next[e]) {
            dfs(to[e], parent, s, lastSeen, answer);
        }

        // Backtrack character ancestor state
        lastSeen[charIdx] = prevAncestor;

        // Propagate finalized subtree size directly to u's final parent
        if (finalParent != -1) {
            answer[finalParent] += answer[u];
        }
    }
}