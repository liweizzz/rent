package study.designMode.strategy;

public class CashContent {
    private CashSuper cashSuper;

    public CashContent(int cashType){
        switch (cashType) {
            case 1:
                this.cashSuper = new CashNormal();
                break;
            case 2:
                this.cashSuper = new CashRebate(0.8);
                break;
            case 3:
                this.cashSuper = new CashRebate(0.7);
                break;
            case 4:
                this.cashSuper = new CashReturn(300,100);
                break;
        }
    }

    public double getResult(double price,int num){
        return this.cashSuper.discount(price,num);
    }
}
