package study.arithmetic;

import java.util.HashMap;
import java.util.Map;

/**
 * 请你设计并实现一个满足  LRU (最近最少使用) 缓存 约束的数据结构。
 * 实现 LRUCache 类：
 * LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；
 * 如果不存在，则向缓存中插入该组 key-value 。如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最近未使用的关键字。
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 */
public class LeetCode146_LRU {
    class Node{
        int key;
        int val;
        Node prev;
        Node next;
        public Node(){}
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private int capacity;
    private int size;
    //伪头尾节点
    private Node head;
    private Node tail;
    private Map<Integer,Node> cache = new HashMap<>();

    public LeetCode146_LRU(int capacity){
        this.capacity = capacity;
        this.size = 0;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key){
        Node node = cache.get(key);
        if(node == null){
            return -1;
        }
        moveToHead(node);
        return node.val;
    }

    public void put(int key,int val){
        Node node = cache.get(key);
        if(node == null){
            //新节点
            Node newNode = new Node(key,val);
            cache.put(key,newNode);
            addToHead(newNode);
            size++;
            if(size > capacity){
                Node tail = removeTail();
                cache.remove(tail.key);
                size--;
            }
        }else {
            node.val = val;
            moveToHead(node);
        }
    }

    public void moveToHead(Node node){
        removeNode(node);
        addToHead(node);
    }

    public void removeNode(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void addToHead(Node node){
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    //删除最后一个节点
    public Node removeTail(){
        Node node = tail.prev;
        removeNode(node);
        return node;
    }
}
