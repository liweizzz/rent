package arithmetic;

import java.util.Scanner;

public class LastWord {

    public static ListNode merge(ListNode list1,ListNode list2){
        ListNode dum = new ListNode();
        ListNode cur = dum;
        while (list1 != null && list2 != null){
            if(list1.val < list2.val){
                cur.next = list1;
                list1 = list1.next;
            }else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = list1 == null ? list2 : list1;
        return dum.next;
    }

    public static int aaa(int a,int b){
        int x ,y;
        if(a >= b){
            x = a;
            y = b;
        }else {
            x = b;
            y = a;
        }

        for (int i = 1; i <= y; i++) {
            if((x * i) % y == 0){
                return x * i;
            }
        }
        return 0;
    }

    public static int getWord(String str){
        if(str == null || str.length() == 0){
            return 0;
        }
        int index = str.lastIndexOf(" ");
        String s = str.substring(index+1,str.length());
        return s.length();
    }
//    public static void main(String[] args) {
//        String str = "";
//        Scanner in = new Scanner(System.in);
//        while (in.hasNextLine()){
//            str = in.nextLine();
//            System.out.println(getWord(str));
//        }
//    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(aaa(a,b));
        }
    }
}
