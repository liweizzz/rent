package study.arithmetic;

import java.util.*;

/**
 * 15. 三数之和
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
 *
 * 你返回所有和为 0 且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 */
public class LeetCode15 {
    //两数之和=0
    public static List<List<Integer>> twoSum(int[] nums){
        List<List<Integer>> res = new ArrayList<>();
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                int sum = nums[i] + nums[j];
                if(sum == 0){
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[j]);
                    res.add(list);
                }
            }
        }
        return res;
    }

    public static List<List<Integer>> twoSum1(int[] nums){
        List<List<Integer>> res = new ArrayList<>();
        int len = nums.length;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < len; i++) {
            if(set.contains(0 - nums[i])){
                List<Integer> list = new ArrayList<>();
                list.add(0-nums[i]);
                list.add(nums[i]);
                res.add(list);
            }else {
                set.add(nums[i]);
            }
        }
        return res;
    }

    //三数之和
    public static List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> res = new ArrayList<>();
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            int target = -nums[i];
            Map<Integer,Integer> map = new HashMap<>();
            for (int j = 0; j < len; j++) {
                if(j!=i){
                    if(map.containsKey(target - nums[j])){
                        List<Integer> list = new ArrayList<>();
                        if(i<map.get(target - nums[j]) && map.get(target - nums[j]) < j){
                            list.add(nums[i]);
                            list.add(target - nums[j]);
                            list.add(nums[j]);
                            if(!res.contains(list)){
                                res.add(list);
                            }
                        }
                    }else {
                        map.put(nums[j],j);
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {0,0,0,0};
        List<List<Integer>> lists = threeSum(nums);
        System.out.println(lists);
    }

}
