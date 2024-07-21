package study.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组A中给定可以使用的1-9的数，返回由数组A中的元素组成的小于n的最大数。
 * 1：A={1,2,9,4}，n=2533，返回2499
 * 2：A={1,2,5,4}，n=2543，返回2542
 * 3：A={1,2,5,4}，n=2541，返回2525
 * 4：A={1,2,9,4}。n=2111，返回1999
 * 5：A={5,9}，n=5555，返回999
 */
public class Exam3 {
    public static int solution(List<Integer> arrs,int n){
        List<Integer> list = new ArrayList<>();
        while (n>0){
            list.add(n%10);
            n /= 10;
        }
        StringBuilder sb = new StringBuilder();
        for (Integer target : list) {

        }
//            //最高位
//            Integer element = stack.pop();
//            if(arrs.contains(element)){
//                sb.append(element);
//            }else {
//                int max = 0;
//                for (int i = 0; i < arrs.size(); i++) {
//                    if(arrs.get(i) < element){
//                        max = Math.max(arrs.get(i),max);
//                    }
//                }
//                sb.append(max);
//                while (!stack.empty()){
//                    for (int i = 0; i < arrs.size(); i++) {
//                        if(arrs.get(i) < element){
//                            max = Math.max(arrs.get(i),max);
//                        }
//                    }
//                }
//
//            }
        return Integer.valueOf(sb.toString());
    }

    public static void main(String[] args) {
        Integer[] arrs = {1,2,9,4};
        int n = 2533;
        System.out.println(solution(Arrays.asList(arrs),n));
    }
}
