package arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * 234. 回文链表
 * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
 */
public class LeetCode234 {
    //反转链表
    public static ListNode solution(ListNode node){
        //定义一个空的伪节点，用来接收当前节点的赋值
        ListNode dum = null;
        //当前指针
        ListNode cur = null;
        //循环条件是node节点不为空
        while (node!=null){
            //node节点赋值给当前节点
            cur = node;
            //node节点进位
            node = node.next;
            //把空的伪节点赋值给当前节点的下一个节点
            cur.next = dum;
            //把当前节点复制给空的伪节点
            dum = cur;
        }
        return cur;
    }

    public void test(){
        Executors.newCachedThreadPool();
        Double d = 1.2;
        d.compareTo(1.3);
    }

    public static boolean solution1(ListNode node){
        List<Integer> list = new ArrayList<>();
        while (node!=null){
            list.add(node.val);
            node = node.next;
        }
        int size = list.size();
        for (int i = 0; i < size/2; i++) {
            if(list.get(i) != list.get(size-i-1)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1);
        ListNode list3 = new ListNode(2);
        ListNode list5 = new ListNode(2);
        ListNode list7 = new ListNode(1);
        list1.next = list3;
        list3.next = list5;
        list5.next = list7;
        solution(list1);
    }
}
