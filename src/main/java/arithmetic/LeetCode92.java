package arithmetic;

/**
 * 92. 反转链表 II
 * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。
 * 请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
 */
public class LeetCode92 {
    //截取链表的前n个节点
    public static ListNode solution(ListNode node,int n){
        ListNode cur = node;
        int count = 1;
        while (cur!=null){
            if(count >= n){
                cur.next = null;
                break;
            }
            cur = cur.next;
            count ++;
        }
        return node;
    }

    public static ListNode solution1(ListNode node,int n){
        ListNode pre = node;
        for (int i = 0; i < n-1; i++) {
            pre = pre.next;
        }
        return node;
    }

    public static ListNode solution2(ListNode head,int left,int right){
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode pre = dummyNode;
        //定位left点处
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        //定位right点处
        ListNode rightNode = pre;
        for (int i = 0; i < right - left + 1; i++) {
            rightNode = rightNode.next;
        }
        //切除
        ListNode leftNode = pre.next;
        pre.next = null;
        ListNode cur = rightNode.next;
        rightNode.next = null;
        //反转
        reverseLinkNode(leftNode);
        //拼接
        pre.next = rightNode;
        leftNode.next = cur;
        return dummyNode.next;
    }

    public static void reverseLinkNode(ListNode leftNode){
        ListNode pre = null;
        ListNode cur;
        while (leftNode != null){
            cur = leftNode;
            leftNode=leftNode.next;
            cur.next = pre;
            pre = cur;
        }
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
//        ListNode list1 = new ListNode(3);
//        ListNode list2 = new ListNode(5);
        solution2(list1,3,4);
    }
}
