package study.arithmetic;

import java.io.Serializable;

public class hangzhouzhaoyin implements Serializable {
    public static String get(String s){
        StringBuilder res = new StringBuilder();
        getResult(s,res);
        return res.toString();
    }

    public static void getResult(String s,StringBuilder res){
        int length = s.length();
        char maxLetter = 0;
        for (int i = 0; i < length; i++) {
            if(maxLetter < s.charAt(i)){
                maxLetter = s.charAt(i);
            }
        }
        int start = s.indexOf(maxLetter);
        int end = s.lastIndexOf(maxLetter);
        String sub = s.substring(start, end+1);
        for (int j = 0; j < sub.length(); j++) {
            if(sub.charAt(j) == maxLetter){
                res.append(maxLetter);
            }
        }
        if(end + 1< s.length()){
            String s2 = s.substring(end + 1, length);
            getResult(s2,res);
        }
    }

    public static void main(String[] args) {
        System.out.println(get("aabcbccacbbcbaaba"));
    }
}
