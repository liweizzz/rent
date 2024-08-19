package study.arithmetic;

import java.util.PriorityQueue;

/**
 * 数组中的第K个最大元素
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
 */
public class LeetCode215_TopK {
    public static int solution(int[] arrs,int k){
        PriorityQueue<Integer> queue = new PriorityQueue();
        //先把前k个元素放到队列中
        for (int i = 0; i < k; i++) {
            queue.offer(arrs[i]);
        }
        //依次对后面的元素与堆顶元素进行比较，如果比堆顶元素大，
        for (int i = k; i < arrs.length; i++) {
            if(arrs[i] > queue.peek()){
                queue.poll();
                queue.offer(arrs[i]);
            }
        }
        return queue.peek();
    }

    //手动实现大根堆
    public static int findKthLargest(int[] nums, int k) {
        int heapSize = nums.length;
        //建堆，破坏，调整，删除
        buildHeap(nums,heapSize);
        //建堆完成后，第一个元素为最大的元素，所以删除k-1个元素，则堆顶元素为第k大的元素
        for (int i = nums.length-1; i >=nums.length-(k-1) ;i--) {
            //先将堆的最后一个元素与堆顶元素交换，由于此时堆的性质被破坏，需对此时的根节点进行向下调整操作
            swap(nums,0,i);
            heapSize --;
            //对此时的根节点进行向下调整操作
            adjustHeap(nums,0,heapSize);
        }
        return nums[0];
    }

    public static void buildHeap(int[] nums,int heapSize){
        //从第一个非叶子节点开始
        for (int i = heapSize/2; i>=0 ; i--) {
            adjustHeap(nums,i,heapSize);
        }
    }

    public static void adjustHeap(int[] nums,int i, int heapSize){
        int left = i*2+1;
        int right = i*2+2;
        int largest = i;
        if(left<heapSize && nums[left]>nums[largest]){
            largest = left;
        }
        if(right<heapSize && nums[right]>nums[largest]){
            largest = right;
        }
        if(largest != i){
            swap(nums,i,largest);
            adjustHeap(nums,largest,heapSize);
        }
    }

    public static void swap(int[] nums,int a,int b){
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    public static int findKthLargest1(int[] nums,int k){
        //默认小根堆
        int len = nums.length;
        if(k>len){
            return -1;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < k; i++) {
            queue.offer(nums[i]);
        }
        for (int i = k; i < len; i++) {
            //堆顶元素比剩余数组中的元素要小，则取出堆顶元素，放入当前大的值
            if(queue.peek()<nums[i]){
                queue.poll();
                queue.offer(nums[i]);
            }
        }
        return queue.peek();
    }



    public static void main(String[] args) {
        int[] arrs = {3,2,1,5,6,4};
        LeetCode215_TopK leetCode215 = new LeetCode215_TopK();
//        int[] arrs = {3,2,3,1,2,4,5,5,6};
//        System.out.println(solution(arrs,2));
//        System.out.println(leetCode215.findKthLargest(arrs,2));
        System.out.println(findKthLargest(arrs,3));
        System.out.println(findKthLargest1(arrs,3));
    }
}
