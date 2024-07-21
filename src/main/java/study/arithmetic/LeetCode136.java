package study.arithmetic;


/**
 * 136. 删除链表的节点
 * 给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
 *
 * 返回删除后的链表的头节点。
 */
public class LeetCode136 {
    public static ListNode deleteNode(ListNode head,int val){
        if(head.val == val){
            head = head.next;
        }
        ListNode pre = head;
        ListNode cur = head.next;
        while (cur !=null){
            if(cur.val == val){
                pre.next = cur.next;
                break;
            }
            pre = cur;
            cur = cur.next;
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1);
        ListNode list3 = new ListNode(2);
        ListNode list5 = new ListNode(3);
        ListNode list7 = new ListNode(4);
        ListNode list9 = new ListNode(5);
        list1.next = list3;
        list3.next = list5;
        list5.next = list7;
        list7.next = list9;
        deleteNode(list1,3);
    }
}
