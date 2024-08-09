package study.designMode.strategy;

public class CashNormal implements CashSuper{
    @Override
    public double discount(double price, int num) {
        return price * num;
    }
}
