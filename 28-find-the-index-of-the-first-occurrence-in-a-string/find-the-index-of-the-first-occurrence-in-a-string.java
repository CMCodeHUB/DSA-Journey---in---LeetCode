class Solution {
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        
        if (m == 0) {
            return 0;
        }
        if (n < m) {
            return -1;
        }
        
        int[] lps = buildLPS(needle);
        int i = 0; // pointer for haystack
        int j = 0; // pointer for needle
        
        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    return i - m;
                }
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        
        return -1;
    }
    
    private int[] buildLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int prevLPS = 0;
        int i = 1;
        
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(prevLPS)) {
                prevLPS++;
                lps[i] = prevLPS;
                i++;
            } else if (prevLPS > 0) {
                prevLPS = lps[prevLPS - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
        
        return lps;
    }
}