package study.designMode.strategy;

public class Test {
    public static void main(String[] args) {
        CashContent cashContent = new CashContent(1);
        double result = cashContent.getResult(2, 100);
        System.out.println(result);
    }
}
