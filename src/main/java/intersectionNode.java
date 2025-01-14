import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class intersectionNode {

    public ListNode solution(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) return null;

        if(headA.next == null && headB.next == null && headA.val == headB.val) return headA;

        List<Integer> listA = new ArrayList<Integer>();
        ListNode p = headA;
        while(p != null) {
            listA.add(p.val);
            p = p.next;
        }

        List<Integer> listB = new ArrayList<Integer>();
        p = headB;
        while(p != null) {
            if (listA.contains(p.val))
                return p;
        }

        return null;
    }

    public ListNode solutionAI(ListNode headA, ListNode headB) {
        // Handle edge cases
        if (headA == null || headB == null) return null;

        ListNode a = headA;
        ListNode b = headB;

        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }

        return a;
    }

    public static void main(String[] args) {

        Map<Integer, String> m = HashMap<Integer, String>();

        /*intersectionNode c = new intersectionNode();
        // 4,1,8,4,5
        int[] arr = {4, 1, 8, 4, 5};
        ListNode a = intersectionNode.createList(arr);

        // 5,6,1,8,4,5
        int[] arrb = {5, 6, 1, 8, 4, 5};
        ListNode b = intersectionNode.createList(arrb);

        ListNode r = c.solutionAI(a, b);
        System.out.println(r.val);*/

        // Create test lists
        ListNode commonNode = new ListNode(8);
        commonNode.next = new ListNode(4);
        commonNode.next.next = new ListNode(5);


        ListNode headA = new ListNode(4);
        headA.next = new ListNode(1);
        headA.next.next = commonNode;

        ListNode headB = new ListNode(5);
        headB.next = new ListNode(6);
        headB.next.next = new ListNode(1);
        headB.next.next.next = commonNode;

        intersectionNode solution = new intersectionNode();
        ListNode intersection = solution.solutionAI(headA, headB);
        System.out.println(intersection != null ? intersection.val : "No intersection");
    }

    public static ListNode createList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }
        return head;
    }
}
