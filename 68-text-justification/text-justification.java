import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        char[] lineBuf = new char[maxWidth];
        int n = words.length;
        int i = 0;

        while (i < n) {
            // 1. Greedily pack as many words as possible onto the current line
            int lineWordsLen = words[i].length();
            int j = i + 1;
            while (j < n && lineWordsLen + (j - i) + words[j].length() <= maxWidth) {
                lineWordsLen += words[j].length();
                j++;
            }

            int gaps = j - i - 1;
            boolean isLastLine = (j == n);

            // Reset reusable buffer to spaces
            Arrays.fill(lineBuf, ' ');
            int cursor = 0;

            if (isLastLine || gaps == 0) {
                // 2a. Left-justify: single space between words, trailing spaces on the right
                for (int k = i; k < j; k++) {
                    String word = words[k];
                    int len = word.length();
                    word.getChars(0, len, lineBuf, cursor);
                    cursor += len + 1; // +1 leaves one space between words
                }
            } else {
                // 2b. Full-justify: distribute spaces evenly, left slots take extra remainder
                int totalSpaces = maxWidth - lineWordsLen;
                int baseSpaces = totalSpaces / gaps;
                int extraSpaces = totalSpaces % gaps;

                for (int k = i; k < j; k++) {
                    String word = words[k];
                    int len = word.length();
                    word.getChars(0, len, lineBuf, cursor);
                    cursor += len;

                    if (k < j - 1) {
                        int gapIndex = k - i;
                        cursor += baseSpaces + (gapIndex < extraSpaces ? 1 : 0);
                    }
                }
            }

            result.add(new String(lineBuf));
            i = j;
        }

        return result;
    }
}