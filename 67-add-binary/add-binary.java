class Solution {
    public String addBinary(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int maxLen = Math.max(i, j) + 2;
        char[] result = new char[maxLen];

        int k = maxLen - 1;
        int carry = 0;

        while (i >= 0 || j >= 0 || carry != 0) {
            int sum = carry;
            if (i >= 0) {
                sum += a.charAt(i--) - '0';
            }
            if (j >= 0) {
                sum += b.charAt(j--) - '0';
            }

            result[k--] = (char) ((sum & 1) + '0');
            carry = sum >>> 1;
        }

        return new String(result, k + 1, maxLen - (k + 1));
    }
}