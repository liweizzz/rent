package study.designMode.factory;

public class OpreationFactory {
    public static Opreation getOpreate(String opreate){
        Opreation opreation = null;
        switch (opreate){
            case "+":
                opreation = new Add();
                break;
            case "-":
                opreation = new Add();
                break;
            case "*":
                opreation = new Add();
                break;
            case "/":
                opreation = new Add();
                break;
        }
        return opreation;
    }
}
