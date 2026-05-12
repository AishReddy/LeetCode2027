/**
 * ============================================================
 *  LeetCode #876 — Middle of the Linked List
 * ============================================================
 *
 *  PROBLEM
 *  -------
 *  Given the head of a singly linked list, return the middle node.
 *  If there are two middle nodes, return the SECOND middle node.
 *
 *  Example 1 (odd length):
 *    Input  : 1 -> 2 -> 3 -> 4 -> 5
 *    Output : Node(3)        <- exact center
 *
 *  Example 2 (even length):
 *    Input  : 1 -> 2 -> 3 -> 4
 *    Output : Node(3)        <- second of the two middles
 *
 * ============================================================
 *  THOUGHT PROCESS
 * ============================================================
 *
 *  Brute-force (two passes):
 *    Pass 1 - count total nodes (n).
 *    Pass 2 - walk to index n/2.
 *    Works, but visits nodes twice and needs extra bookkeeping.
 *
 *  Better (one pass - Floyd's slow & fast pointer):
 *    Use two pointers starting at head.
 *      slow moves 1 step per iteration.
 *      fast moves 2 steps per iteration.
 *    Because fast is always exactly TWICE as far from the start
 *    as slow, when fast has covered the whole list slow has
 *    covered exactly half - the middle.
 *    No length calculation needed. Single pass.
 *
 * ============================================================
 *  WHY THE LOOP CONDITION IS WHAT IT IS
 * ============================================================
 *
 *    while (fast != null && fast.next != null)
 *
 *  Condition 1 - fast != null
 *    Guards ODD-length lists.
 *    After the last iteration, fast lands exactly on the last
 *    node (fast.next == null), so the loop exits cleanly.
 *
 *  Condition 2 - fast.next != null
 *    Guards EVEN-length lists.
 *    fast's NEXT step would go off the end; we stop one step
 *    early so fast.next.next is never called on a null pointer.
 *    Without this check -> NullPointerException.
 *
 * ============================================================
 *  STEP-BY-STEP TRACE
 * ============================================================
 *
 *  Odd  [1 -> 2 -> 3 -> 4 -> 5]  (n = 5)
 *  +---------+---------+---------+
 *  |  Iter   |  slow   |  fast   |
 *  +---------+---------+---------+
 *  |  init   |    1    |    1    |
 *  |    1    |    2    |    3    |
 *  |    2    |    3    |    5    |  <- fast.next == null, exit
 *  +---------+---------+---------+
 *  return slow = node 3  (correct)
 *
 *  Even [1 -> 2 -> 3 -> 4]  (n = 4)
 *  +---------+---------+---------+
 *  |  Iter   |  slow   |  fast   |
 *  +---------+---------+---------+
 *  |  init   |    1    |    1    |
 *  |    1    |    2    |    3    |
 *  |    2    |    3    |  null   |  <- fast == null, exit
 *  +---------+---------+---------+
 *  return slow = node 3  (second middle, as required)
 *
 * ============================================================
 *  COMPLEXITY
 * ============================================================
 *
 *  Time  : O(n)
 *    The loop runs ~n/2 iterations (fast covers 2 steps each).
 *    Only one pass through the list - no second traversal.
 *
 *  Space : O(1)
 *    Only two pointer variables (slow, fast).
 *    No arrays, no recursion stack, no extra data structures.
 *
 *  Why this is optimal:
 *    You must visit at least the first n/2 nodes to find the
 *    middle - there is no shortcut. This algorithm does exactly
 *    that and nothing more.
 *
 * ============================================================
 */

// Definition for singly-linked list node (provided by LeetCode)
class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

class MiddleOfLinkedList {

    public ListNode middleNode(ListNode head) {

        // Both pointers begin at the head of the list.
        // At this point slow and fast are at the same position (node 1).
        ListNode slow = head;
        ListNode fast = head;

        /*
         * Advance until fast can no longer move 2 steps.
         *
         *   fast != null       - needed for ODD-length lists:
         *                        fast lands on the last node;
         *                        fast.next is null so we stop.
         *
         *   fast.next != null  - needed for EVEN-length lists:
         *                        fast's next jump would overshoot;
         *                        we stop before calling fast.next.next
         *                        on a null reference.
         *
         * Removing EITHER condition causes a NullPointerException
         * on certain list lengths.
         */
        while (fast != null && fast.next != null) {

            slow = slow.next;           // slow: 1 step forward
            fast = fast.next.next;      // fast: 2 steps forward
        }

        /*
         * When the loop exits, fast has reached (or passed) the end.
         * Because fast moved twice as fast as slow, slow has moved
         * exactly half as far - landing precisely on the middle node.
         *
         * For even-length lists slow lands on the SECOND middle,
         * which is the answer LeetCode expects.
         */
        return slow;
    }
}