class Solution {
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1.0;
        }

        // Promote to long to safely handle Integer.MIN_VALUE (-2^31)
        long N = n;
        boolean isNegative = N < 0;
        if (isNegative) {
            N = -N;
        }

        double result = 1.0;
        double currentProduct = x;

        while (N > 0) {
            if ((N & 1L) == 1L) {
                result *= currentProduct;
            }
            currentProduct *= currentProduct;
            N >>= 1;
        }

        // Perform the reciprocal at the end to prevent amplifying initial rounding error
        return isNegative ? 1.0 / result : result;
    }
}