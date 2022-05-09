import java.util.Iterator;
import java.util.LinkedList;

class Fruit {
    private int price;

    Fruit(int price) {
        setPrice(price);
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int calculatePrice(){
        return getPrice();
    }
}

class Banana extends Fruit{
    Banana(int price){
        super(price);
    }
    @Override
    public int calculatePrice() {
        return super.getPrice();
    }
}

class Apple extends Fruit{
    Apple(int price){
        super(price);
    }
    @Override
    public int calculatePrice(){
        return super.getPrice();
    }
}

class Pear extends Fruit{
    Pear(int price){
        super(price);
    }
    @Override
    public int calculatePrice(){
        return super.getPrice();
    }
}

class Orange extends Fruit{
    Orange(int price){
        super(price);
    }
    @Override
    public int calculatePrice(){
        return super.getPrice();
    }
}

class main_1{
    public static int computeTotalPrice(LinkedList<Fruit> f){
        int total = 0;
        Iterator<Fruit> itr = f.iterator();
        while(itr.hasNext()){
            Fruit ft = itr.next();
            total += ft.calculatePrice();
        }
        return total;
    }
    public static void main(String[] args){
        LinkedList<Fruit> ll = new LinkedList<Fruit>();
        ll.add(new Apple(1000));
        ll.add(new Banana(2000));
        ll.add(new Pear(1500));
        ll.add(new Orange(1200));

        System.out.println(computeTotalPrice(ll));
    }
}

