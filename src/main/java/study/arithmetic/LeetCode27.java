package study.arithmetic;

/**
 * 27. 移除元素
 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 */
public class LeetCode27 {
    public static int removeElement(int[] nums,int val){
        int len = nums.length;
        int res = 0;
        for (int i = 0; i < len; i++) {
            if(nums[i] != val){
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {3,2,2,3};
        int i = removeElement(nums, 3);
        System.out.println(i);
    }
}
