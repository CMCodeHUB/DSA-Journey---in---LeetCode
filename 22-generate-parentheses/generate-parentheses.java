class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        make(ans, "", 0, 0, n);
        return ans;
    }

    private void make(List<String> ans, String cur, int open, int close, int n) {
        if (cur.length() == n * 2) {
            ans.add(cur);
            return;
        }

        if (open < n) {
            make(ans, cur + "(", open + 1, close, n);
        }

        if (close < open) {
            make(ans, cur + ")", open, close + 1, n);
        }
    }
}