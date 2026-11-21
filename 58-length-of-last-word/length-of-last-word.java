class Solution {
    public int lengthOfLastWord(String s) {
        int i = s.length() - 1;
        int length = 0;

        // 1. Skip any trailing spaces from the end
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }

        // 2. Count contiguous non-space characters of the last word
        while (i >= 0 && s.charAt(i) != ' ') {
            length++;
            i--;
        }

        return length;
    }
}