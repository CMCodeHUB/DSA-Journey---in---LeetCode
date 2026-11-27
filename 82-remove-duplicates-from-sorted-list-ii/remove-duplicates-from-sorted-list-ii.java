/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;
        ListNode curr = head;

        while (curr != null) {
            if (curr.next != null && curr.val == curr.next.val) {
                // Advance curr to the end of the duplicate value block
                while (curr.next != null && curr.val == curr.next.val) {
                    curr = curr.next;
                }
                // Bypass the entire duplicate block
                prev.next = curr.next;
            } else {
                // curr is distinct; advance the confirmed tail pointer
                prev = curr;
            }
            curr = curr.next;
        }

        return dummy.next;
    }
}