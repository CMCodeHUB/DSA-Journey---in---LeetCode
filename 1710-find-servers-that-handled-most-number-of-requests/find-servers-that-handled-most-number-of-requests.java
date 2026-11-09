import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        // Min-heap to store busy servers: [freeTime, serverId]
        PriorityQueue<long[]> busyServers = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        
        // TreeSet to maintain currently available server indices in sorted order
        TreeSet<Integer> availableServers = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            availableServers.add(i);
        }
        
        int[] requestCount = new int[k];
        int maxRequests = 0;
        
        for (int i = 0; i < arrival.length; i++) {
            int arrivalTime = arrival[i];
            int processingTime = load[i];
            
            // Free up servers whose tasks have finished by the current arrival time
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= arrivalTime) {
                long[] busy = busyServers.poll();
                availableServers.add((int) busy[1]);
            }
            
            // If all servers are busy, drop the request
            if (availableServers.isEmpty()) {
                continue;
            }
            
            int preferredId = i % k;
            // Find the first available server >= preferredId
            Integer serverId = availableServers.ceiling(preferredId);
            
            // If none found, wrap around to the smallest available server index
            if (serverId == null) {
                serverId = availableServers.first();
            }
            
            // Assign the request to the server
            availableServers.remove(serverId);
            long freeTime = (long) arrivalTime + processingTime;
            busyServers.add(new long[] { freeTime, serverId });
            
            requestCount[serverId]++;
            maxRequests = Math.max(maxRequests, requestCount[serverId]);
        }
        
        // Collect all servers that handled the maximum number of requests
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (requestCount[i] == maxRequests) {
                result.add(i);
            }
        }
        
        return result;
    }
}