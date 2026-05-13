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
    public void reorderList(ListNode head) {

        // ── STEP 1: Find middle of the list ──────────────────────────
        ListNode slow = head;   // was: start
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode secondHalf = slow.next;  // was: second
        slow.next = null;                 // cut first half from second half

        // ── STEP 2: Reverse the second half ──────────────────────────
        ListNode prev = null;
        ListNode curr = secondHalf;       // was: current

        while (curr != null) {
            ListNode nextNode = curr.next; // was: future
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }

        // ── STEP 3: Merge two halves ──────────────────────────────────
        ListNode firstPtr  = head;        // was: head  (explicit pointer name)
        ListNode secondPtr = prev;        // was: prev  (head of reversed half)

        while (firstPtr != null && secondPtr != null) {
            ListNode firstNext  = firstPtr.next;   // was: future
            ListNode secondNext = secondPtr.next;  // was: future2

            firstPtr.next  = secondPtr;
            secondPtr.next = firstNext;

            firstPtr  = firstNext;
            secondPtr = secondNext;
        }
    }
}

/*
 * ─────────────────────────────────────────────────
 *  Variable rename map
 * ─────────────────────────────────────────────────
 *  start    → slow        standard slow-pointer name
 *  second   → secondHalf  describes what it points to
 *  current  → curr        concise, standard
 *  future   → nextNode    saves next node in reverse loop
 *  head     → firstPtr    explicit merge pointer (first half)
 *  prev     → secondPtr   explicit merge pointer (second half)
 *  future   → firstNext   saves first half's next in merge
 *  future2  → secondNext  saves second half's next in merge
 *
 * ─────────────────────────────────────────────────
 *  Example: [1 → 2 → 3 → 4]
 * ─────────────────────────────────────────────────
 *  Step 1: first  → 1 → 2 | second → 3 → 4
 *  Step 2: reversed second → 4 → 3
 *  Step 3: merged → 1 → 4 → 2 → 3  ✓
 *
 *  Time  : O(n)
 *  Space : O(1)
 */