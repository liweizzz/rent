package study;

public class JavaVMStackSOF {
    private int stackLength = 1;

    public void stackLeak(){
        stackLength ++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF stackSOF = new JavaVMStackSOF();
        try {
            stackSOF.stackLeak();
        } catch (Exception e) {
            System.out.println(stackSOF.stackLength);
            throw new RuntimeException(e);
        }
    }
}
