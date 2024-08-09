package study.designMode.strategy;

public class CashRebate implements CashSuper{
    private double moneyRebate = 1.0;

    public CashRebate(double moneyRebate) {
        this.moneyRebate = moneyRebate;
    }

    @Override
    public double discount(double price, int num) {
        return price * num * moneyRebate;
    }
}
