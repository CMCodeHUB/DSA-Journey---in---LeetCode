class Solution {
    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m < n) {
            return "";
        }

        int[] need = new int[128];
        for (int i = 0; i < n; i++) {
            need[t.charAt(i)]++;
        }

        char[] sChars = s.toCharArray();
        int missing = n;
        int left = 0;
        int minStart = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < m; right++) {
            char rightChar = sChars[right];
            if (need[rightChar] > 0) {
                missing--;
            }
            need[rightChar]--;

            // Shrink from the left while the window remains valid
            while (missing == 0) {
                int currentLen = right - left + 1;
                if (currentLen < minLen) {
                    minLen = currentLen;
                    minStart = left;
                }

                char leftChar = sChars[left];
                need[leftChar]++;
                if (need[leftChar] > 0) {
                    missing++;
                }
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : new String(sChars, minStart, minLen);
    }
}