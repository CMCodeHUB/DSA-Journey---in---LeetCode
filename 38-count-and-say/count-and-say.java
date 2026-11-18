class Solution {
    public String countAndSay(int n) {
        String current = "1";
        
        for (int step = 2; step <= n; step++) {
            StringBuilder nextTerm = new StringBuilder();
            int len = current.length();
            
            for (int i = 0; i < len; i++) {
                int count = 1;
                
                // Count consecutive identical characters
                while (i + 1 < len && current.charAt(i) == current.charAt(i + 1)) {
                    count++;
                    i++;
                }
                
                nextTerm.append(count).append(current.charAt(i));
            }
            
            current = nextTerm.toString();
        }
        
        return current;
    }
}