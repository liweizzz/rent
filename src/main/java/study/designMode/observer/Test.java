package study.designMode.observer;

public class Test {
    public static void main(String[] args) {
        Subject subject = new Subject();
        new Dog(subject);
        new Cat(subject);
        subject.setState(10);
    }
}
