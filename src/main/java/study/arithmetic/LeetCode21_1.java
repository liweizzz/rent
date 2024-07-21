package study.arithmetic;

public class LeetCode21_1 {
    //合并成一个有序的链表
    public static ListNode solution(ListNode list1, ListNode list2){
        ListNode pre = new ListNode();
        ListNode cur = pre;
        while (list1 != null && list2 != null){
            if(list1.val <= list2.val){
                cur.next = list1;
                list1 = list1.next;
            }else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = list1 == null ? list2 : list1;
        return pre.next;
    }

    //合并两个链表
    public static ListNode solution1(ListNode list1,ListNode list2){
        ListNode pre = new ListNode();
        ListNode cur = pre;
        while (list1 != null){
            if(list1 == null){
                cur.next = list2;
                break;
            }
            cur.next = list1;
            list1 = list1.next;
            cur = cur.next;
        }
        return pre.next;
    }

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1);
        ListNode list3 = new ListNode(3);
        ListNode list5 = new ListNode(5);
        ListNode list7 = new ListNode(7);
        list1.next = list3;
        list3.next = list5;
        list5.next = list7;
        ListNode list2 = new ListNode(2);
        ListNode list4 = new ListNode(4);
        ListNode list6 = new ListNode(6);
        list2.next = list4;
        list4.next = list6;
        ListNode solution = solution1(list1, list2);
        System.out.println(solution);
    }
}
