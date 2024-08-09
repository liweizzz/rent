package study.designMode.strategy;

public class CashReturn implements CashSuper{
    private double moneyCondition;
    private double moneyReturn;

    public CashReturn(double moneyCondition, double moneyReturn) {
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public double discount(double price, int num) {
        double result = price * num;
        if(moneyCondition >0 && result > moneyCondition){
            result = result - moneyReturn;
        }
        return result;
    }
}
