package arithmetic;

import java.util.Arrays;

public class BubbleSort {
    public static int[] getResult(int[] arrs){
        int len = arrs.length;
        int temp;
        for (int i = 0; i < len-1; i++) {
            for (int j = 0; j < len -1 -i; j++) {
                if(arrs[j] > arrs[j+1]){
                    temp = arrs[j];
                    arrs[j] = arrs[j+1];
                    arrs[j+1] = temp;
                }
            }
        }
        return arrs;
    }

    public static void main(String[] args) {
        int[] arrs = {64,34,25,12,22,11,90};
        System.out.println(Arrays.toString(getResult(arrs)));
    }
}
