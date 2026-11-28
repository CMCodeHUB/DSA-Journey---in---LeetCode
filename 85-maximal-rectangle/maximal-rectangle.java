class Solution {
    public int maximalRectangle(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[] heights = new int[cols];
        int[] stack = new int[cols + 1];
        int maxArea = 0;

        for (int r = 0; r < rows; r++) {
            char[] row = matrix[r];
            int top = -1;

            for (int c = 0; c <= cols; c++) {
                // Update rolling histogram height for column c (or 0 sentinel at c == cols)
                int currHeight;
                if (c < cols) {
                    if (row[c] == '1') {
                        heights[c]++;
                    } else {
                        heights[c] = 0;
                    }
                    currHeight = heights[c];
                } else {
                    currHeight = 0;
                }

                // Evaluate rectangles bounded by the next shorter bar on the right
                while (top >= 0 && heights[stack[top]] >= currHeight) {
                    int h = heights[stack[top--]];
                    int width = (top < 0) ? c : (c - stack[top] - 1);
                    int area = h * width;
                    if (area > maxArea) {
                        maxArea = area;
                    }
                }

                stack[++top] = c;
            }
        }

        return maxArea;
    }
}