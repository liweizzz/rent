package study.designMode.observer;

public class Cat implements Observer{
    public Cat(Subject subject){
        subject.attach(this);
    }

    @Override
    public void doSomething() {
        System.out.println("喵喵喵");
    }
}
