package study.arithmetic;

import java.util.*;

public class Exam1 {

    public static void test(int pageSize, List<Integer> pages, int key){
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < pageSize; i++) {
            if(map.containsKey(pages.get(i))){
                map.put(pages.get(i),map.get(pages.get(i))+1);
            }else {
                map.put(pages.get(i),1);
            }
        }
        int count =0;
        Map<Integer,Integer> map1 = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if(entry.getValue() >= key){
                count += 1;
                map1.put(entry.getKey(),entry.getValue());
            }
        }
        System.out.println(count);
        if(count < 1){
            return;
        }
        List<Map.Entry<Integer,Integer>> list = new ArrayList<>(map1.entrySet());
        Collections.sort(list, (o1, o2) -> {
            int compare = Integer.compare(o2.getValue(), o1.getValue());
            if(compare !=0){
                return compare;
            }
            return o1.getKey().compareTo(o2.getKey());
        });
        for (Map.Entry<Integer,Integer> entry : list) {
            System.out.println(entry.getKey());
        }
    }

    public static void main(String[] args) {
        List<Integer> pages = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int pageSize = in.nextInt();
        for (int i=0;i<pageSize;i++){
            pages.add(in.nextInt());
        }
        int key = in.nextInt();
        test(pageSize,pages,key);
    }
}
