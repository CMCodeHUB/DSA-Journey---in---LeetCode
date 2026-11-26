class Solution {
    public String simplifyPath(String path) {
        int n = path.length();
        int maxSegments = (n >>> 1) + 1;
        int[] starts = new int[maxSegments];
        int[] ends = new int[maxSegments];

        int top = 0;
        int totalChars = 0;
        int i = 0;

        while (i < n) {
            // 1. Skip consecutive slashes
            while (i < n && path.charAt(i) == '/') {
                i++;
            }
            if (i == n) {
                break;
            }

            // 2. Capture segment bounds [start, end)
            int start = i;
            while (i < n && path.charAt(i) != '/') {
                i++;
            }
            int end = i;
            int len = end - start;

            // 3. Evaluate segment without allocating substrings
            if (len == 1 && path.charAt(start) == '.') {
                // Current directory ".": no-op
                continue;
            } else if (len == 2 && path.charAt(start) == '.' && path.charAt(start + 1) == '.') {
                // Parent directory "..": pop last directory if not already at root
                if (top > 0) {
                    top--;
                    totalChars -= (ends[top] - starts[top]);
                }
            } else {
                // Valid directory or file name (including "...", "....", etc.)
                starts[top] = start;
                ends[top] = end;
                top++;
                totalChars += len;
            }
        }

        // Root directory edge case
        if (top == 0) {
            return "/";
        }

        // 4. Assemble canonical path in a single exact-sized char[] buffer
        char[] out = new char[totalChars + top];
        int cursor = 0;
        for (int k = 0; k < top; k++) {
            out[cursor++] = '/';
            int s = starts[k];
            int e = ends[k];
            path.getChars(s, e, out, cursor);
            cursor += (e - s);
        }

        return new String(out);
    }
}