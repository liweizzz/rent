package arithmetic;

import java.util.ArrayList;
import java.util.List;

public class Exam2 {
    public static void test(List<List<Integer>> list){
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(i == 0 || i == list.size()-1){
                result.add(list.get(i));
                continue;
            }
            List<Integer> last = list.get(i - 1);
            List<Integer> cur = list.get(i);

            Integer a1 = last.get(0);
            Integer b1 = last.get(1);
            int c1= a1+b1;

            Integer a2 = cur.get(0);
            Integer b2 = cur.get(1);
            int c2 = a2+b2;

            if((c1 == c2) && a1 != a2){
                result.add(list.get(i));
            }
            if(c1 == c2 && a1 == a2){
                continue;
            }
            if(c1 != c2){

            }

        }
    }

    public static void main(String[] args) {

    }
}
