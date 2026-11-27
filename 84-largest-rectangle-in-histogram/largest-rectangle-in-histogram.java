class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] stack = new int[n + 1];
        int top = -1;
        int maxArea = 0;

        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i];

            while (top >= 0 && heights[stack[top]] >= currHeight) {
                int height = heights[stack[top--]];
                int width = (top < 0) ? i : (i - stack[top] - 1);
                int area = height * width;
                if (area > maxArea) {
                    maxArea = area;
                }
            }

            stack[++top] = i;
        }

        return maxArea;
    }
}