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
    public static int findMaxLessThanN(int[] A, int n) {
        char[] nChars = String.valueOf(n).toCharArray();
        Arrays.sort(A);
        String result = generateMax(A, nChars, true);
        return Integer.parseInt(result);
    }

    private static String generateMax(int[] A, char[] nChars, boolean isLimit) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < nChars.length; i++) {
            boolean found = false;
            for (int j = A.length - 1; j >= 0; j++) {
                if (isLimit && A[j] + '0' > nChars[i]) {
                    continue;
                }

                if (A[j] + '0' < nChars[i]) {
                    sb.append(A[j]);
                    found = true;
                    break;
                }

                if (A[j] + '0' == nChars[i]) {
                    sb.append(A[j]);
                    if (i == nChars.length - 1) {
                        found = true;
                    } else {
                        String remaining = generateMax(A, Arrays.copyOfRange(nChars, i + 1, nChars.length), isLimit);
                        if (remaining.length() == nChars.length - i - 1) {
                            sb.append(remaining);
                            found = true;
                        }
                    }
                    break;
                }
            }

            if (found) {
                break;
            } else if (i > 0) {
                sb.deleteCharAt(sb.length() - 1);
                for (int j = A.length - 1; j >= 0; j--) {
                    if (A[j] + '0' < nChars[i - 1]) {
                        sb.append(A[j]);
                        break;
                    }
                }
                for (int k = i; k < nChars.length; k++) {
                    sb.append(A[A.length - 1]);
                }
                break;
            }
        }

        while (sb.length() < nChars.length) {
            sb.append(A[A.length - 1]);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        int[] arrs = {1,2,9,4};
        int n = 2533;
        System.out.println(findMaxLessThanN(arrs,n));
    }
}
