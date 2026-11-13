import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        
        for (int num : nums) {
            // If the set already contains the number, a duplicate exists
            if (!seen.add(num)) {
                return true;
            }
        }
        
        return false;
    }
}