class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice,
                                 String arriveBob, String leaveBob) {
        int start = Math.max(days(arriveAlice), days(arriveBob));
        int end = Math.min(days(leaveAlice), days(leaveBob));

        return Math.max(0, end - start + 1);
    }

    private int days(String date) {
        int[] month = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int m = Integer.parseInt(date.substring(0, 2));
        int d = Integer.parseInt(date.substring(3));

        for (int i = 0; i < m - 1; i++) {
            d += month[i];
        }

        return d;
    }
}