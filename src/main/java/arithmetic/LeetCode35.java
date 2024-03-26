package arithmetic;

/**
 * 35. 搜索插入位置
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 *
 * 请必须使用时间复杂度为 O(log n) 的算法。
 */
public class LeetCode35 {
    public static int solution(int[] nums,int target){
        int start = 0;
        int end = nums.length-1;
        int mid;
        while (start <= end){
            mid = (start + end)/2;
            if(target == nums[mid]){
                return mid;
            }
            if(target > nums[mid]){
                start = mid +1;
            }
            if(target < nums[mid]){
                end = mid -1;
            }
        }
        return end+1;
    }
}
