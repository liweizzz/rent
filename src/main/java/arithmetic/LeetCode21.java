package arithmetic;

/**
 * 21. 合并两个有序链表
 * 将两个有序链表合并为一个新的有序链表，自定义链表结构，不能使用jdk集合类，如1-3-5-7和2-4-6合并为1-2-3-4-5-6-7
 */
public class LeetCode21 {
    public static ListNode solution(ListNode list1,ListNode list2){
        ListNode dum = new ListNode();
        ListNode cur = dum;
        while (list1 != null && list2 != null){
            if(list1.val < list2.val){
                cur.next = list1;
                list1 = list1.next;
            }else{
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = list1 == null ? list2 : list1;
        return dum.next;
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
        ListNode solution = solution(list1, list2);
        System.out.println(solution);
    }
}
