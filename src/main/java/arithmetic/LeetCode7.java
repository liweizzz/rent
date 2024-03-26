package arithmetic;

/**
 * 7. 整数反转
 */
public class LeetCode7 {
    public static int reverse(int x){
        int reverse = 0;
        int res = 0;
        while (x!=0){
            res = x%10;
            if(reverse > 214748364 || (reverse == 214748364 && res>7)){
                return 0;
            }
            if(reverse < -214748364 || (reverse == -214748364 && res<-8)){
                return 0;
            }
            reverse = reverse*10 + x%10;
            x = x/10;
        }
        return reverse;
    }

    public static void main(String[] args) {
        reverse(-2147483412);
    }
}
