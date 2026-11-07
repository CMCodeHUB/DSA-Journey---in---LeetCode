import java.util.PriorityQueue;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;
        int[] ans = new int[m];
        
        // freeServers min-heap: stores [weight, index]
        PriorityQueue<int[]> freeServers = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });
        
        // busyServers min-heap: stores [freeTime, weight, index]
        PriorityQueue<long[]> busyServers = new PriorityQueue<>((a, b) -> {
            return Long.compare(a[0], b[0]);
        });
        
        // Initially all servers are free
        for (int i = 0; i < n; i++) {
            freeServers.add(new int[] { servers[i], i });
        }
        
        long currentTime = 0;
        for (int j = 0; j < m; j++) {
            currentTime = Math.max(currentTime, j);
            
            // Release servers that have finished their tasks by currentTime
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= currentTime) {
                long[] busy = busyServers.poll();
                freeServers.add(new int[] { (int) busy[1], (int) busy[2] });
            }
            
            // If no servers are free, fast-forward time to the next available server
            if (freeServers.isEmpty()) {
                currentTime = busyServers.peek()[0];
                while (!busyServers.isEmpty() && busyServers.peek()[0] <= currentTime) {
                    long[] busy = busyServers.poll();
                    freeServers.add(new int[] { (int) busy[1], (int) busy[2] });
                }
            }
            
            // Assign task j to the best available free server
            int[] server = freeServers.poll();
            ans[j] = server[1];
            
            // Calculate when this server becomes free again and push to busyServers
            long freeTime = currentTime + tasks[j];
            busyServers.add(new long[] { freeTime, server[0], server[1] });
        }
        
        return ans;
    }
}