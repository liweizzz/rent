package study.arithmetic;

import java.util.Arrays;

public class QuickSort {
    public static int[] getResult(int[] nums){
        int len = nums.length;
//        solution(nums,0,len-1);
        solution1(nums,0,len-1);
        return nums;
    }

    //双边指针 挖坑法
    public static void solution(int[] nums,int low,int high){
        if(low >= high){
            return;
        }
        int i = low,j = high,index = nums[i];
        while (i<j){
            //从右往左找，第一个比他小的值
            while (i<j && nums[j] >= index){
                j--;
            }
            if(i<j){
                nums[i] = nums[j];
                i++;
            }
            //从左往右找第一个比他大
            while (i<j && nums[i] < index){
                i++;
            }
            if(i<j){
                nums[j] = nums[i];
                j--;
            }
        }
        nums[i] = index;
        solution(nums,low,i-1);
        solution(nums,i+1,high);
    }

    //双边指针 交换法
    public static void solution1(int[] nums,int low,int high){
        if(low >= high){
            return;
        }
        int i = low,j = high,index = nums[i];
        while (i < j) {
            //从右往左找，第一个比他小的值
            while (i < j && nums[j] > index) {
                j--;
            }
            //从左往右找第一个比他大
            while (i < j && nums[i] <= index) {
                i++;
            }
            //没有过界则交换
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        // 最终将分界值与当前指针数据交换
        nums[low] = nums[j];
        nums[j] = index;
        solution1(nums,low,i-1);
        solution1(nums,i+1,high);
    }

    public static int binarySearch(int[] nums,int target){
        int start = 0,end = nums.length-1;
        int mid = 0;
        while (start <= end){
            mid = (start + end)/2;
            if(target == nums[mid]){
                return mid;
            }
            if(target > mid){
                start = mid+1;
            }
            if(target < mid){
                end = mid-1;
            }
        }
        return mid;
    }


    public static void main(String[] args) {
        int[] arrs = {3,4,2,9,6,5,7};
        System.out.println(Arrays.toString(getResult(arrs)));
    }
}
