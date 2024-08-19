package study.designMode.observer;

public class Dog implements Observer{
    public Dog(Subject subject){
        subject.attach(this);
    }

    @Override
    public void doSomething() {
        System.out.println("吃东西");
    }
}
