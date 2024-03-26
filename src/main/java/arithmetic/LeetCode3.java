package arithmetic;

import java.util.HashSet;
import java.util.Set;

/**
 * 3. 无重复字符的最长子串
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class LeetCode3 {
    public static int solution(String s){
        int ans = 0;
        int k = 0;
        int len = s.length();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < len; i++) {
            if(i>0){
                set.remove(s.charAt(i-1));
            }
            while (k<len && !set.contains(s.charAt(k))){
                set.add(s.charAt(k));
                k++;
            }
            ans = Math.max(ans,k-i);
        }
        return ans;
    }
    
    public static void test(){
        System.out.println(Double.valueOf(0));
    }

    public static void main(String[] args) {
//        solution("pwwkew");
        test();
    }
}
