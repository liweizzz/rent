package arithmetic;

/**
 * 23. 合并 K 个升序链表
 */
public class LeetCode23 {
    //每次合两个，依次合并
    public static ListNode mergeLists(ListNode[] lists){
        ListNode ans = null;
        for (int i = 0; i < lists.length; i++) {
            ans = solution(ans,lists[i]);
        }
        return ans;
    }

    public static ListNode solution(ListNode list1,ListNode list2){
        ListNode dum = new ListNode();
        ListNode cur = dum;
        while (list1 != null && list2 !=null){
            if (list1.val <= list2.val){
                cur.next = list1;
                list1 = list1.next;
            }else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = list1 == null ? list2 : list1;
        return dum.next;
    }

}
