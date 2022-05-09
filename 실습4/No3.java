import java.util.ArrayList;


/*
* OCP : 기존의 코드를 변경하지 않으면서 기능을 추가할 수 있도록 설계
* LSP : 부모 클래스와 자식클래스 사이의 행위가 일관성 있어야함
* DIP : 변화하기 어려운것, 거의 변화가 없는것에 의존해야함
* ISP : 인터페이스 분리원칙
*
* 이 4가지를 준수하여 작성하였다.
* */
abstract class Battery{
    private int capacity;
    abstract void use();
    abstract void insert();
    abstract void remove();
}
interface Chargable{
    public abstract void charge();
}
interface Resizable{
    public abstract void resize();
}
interface Revoltable{
    public abstract void revoltable();
}
class Smartphonebattery extends Battery implements Chargable{
    @Override
    void use() {
        System.out.println(new StringBuilder().append("Use ").append(this.getClass().getName()).append("'s battery"));
    }
    @Override
    void insert() {
        System.out.println(new StringBuilder().append("Insert ").append(this.getClass().getName()).append("'s battery"));
    }
    @Override
    void remove() {
        System.out.println(new StringBuilder().append("Remove ").append(this.getClass().getName()).append("'s battery"));
    }
    @Override
    public void charge() {
        System.out.println("Chargable");
    }
}

class Clockbattery extends Battery implements Resizable{
    @Override
    void use() {
        System.out.println(new StringBuilder().append("Use ").append(this.getClass().getName()).append("'s battery"));
    }

    @Override
    void insert() {
        System.out.println(new StringBuilder().append("Insert ").append(this.getClass().getName()).append("'s battery"));
    }

    @Override
    void remove() {
        System.out.println(new StringBuilder().append("Remove ").append(this.getClass().getName()).append("'s battery"));
    }

    @Override
    public void resize() {
        System.out.println("Resizable");
    }
}
class RemoteControllerBattery extends Battery implements Revoltable{
    @Override
    void use() {
        System.out.println(new StringBuilder().append("Use ").append(this.getClass().getName()).append("'s battery"));
    }

    @Override
    void insert() {
        System.out.println(new StringBuilder().append("Insert ").append(this.getClass().getName()).append("'s battery"));
    }

    @Override
    void remove() {
        System.out.println(new StringBuilder().append("Remove ").append(this.getClass().getName()).append("'s battery"));
    }

    @Override
    public void revoltable() {
        System.out.println("Revoltable");
    }
}

class User{
    private static ArrayList<Battery> b = new ArrayList<>();

    public static void main(String[] args){
        b.add(new Clockbattery());
        b.add(new RemoteControllerBattery());
        b.add(new Smartphonebattery());
        for(Battery t : b){
            System.out.println("---------------------");
            t.use();
            t.insert();
            t.remove();
            switch (t.getClass().getName()){
                case "Clockbattery":
                    Clockbattery c = (Clockbattery)t;
                    c.resize();
                    break;
                case "RemoteControllerBattery":
                    RemoteControllerBattery r = (RemoteControllerBattery) t;
                    r.revoltable();
                    break;
                case "Smartphonebattery":
                    Smartphonebattery s = (Smartphonebattery) t;
                    s.charge();
                    break;
            }
            System.out.println("---------------------");
        }
    }
}
