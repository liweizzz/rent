package arithmetic;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数字A：");
        Double numberA = Double.parseDouble(scanner.nextLine());
        System.out.println("请选择运算符号（+、-、*、/）：");
        String strOper = scanner.nextLine();
        System.out.println("请输入数字B：");
        Double numberB = Double.parseDouble(scanner.nextLine());
        double result = 0d;
        switch (strOper){
            case "+":
                result = numberA + numberB;
                break;
            case "-":
                result = numberA - numberB;
                break;
            case "*":
                result = numberA * numberB;
                break;
            case "/":
                result = numberA / numberB;
                break;
        }
        System.out.println("结果是：" + result);
    }
}
