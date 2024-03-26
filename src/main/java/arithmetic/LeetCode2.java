package arithmetic;

/**
 * 2. 两数相加
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 *
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 */
public class LeetCode2 {
    public static ListNode solution(ListNode list1,ListNode list2){
        ListNode dum = new ListNode();
        ListNode cur = dum;
        int carry = 0;
        while (list1 != null || list2 != null){
            int a = list1 != null ? list1.val : 0;
            int b = list2 != null ? list2.val : 0;
            int sum = a + b + carry;
            int val = sum % 10;
            carry = sum/10;
            cur.next = new ListNode(val);
            if(list1 != null){
                list1 = list1.next;
            }
            if(list2 != null){
                list2 = list2.next;
            }
            cur = cur.next;
        }
        if(carry == 1){
            cur.next = new ListNode(carry);
        }
        return dum.next;
    }
}
