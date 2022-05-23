/*
 *
 * Sale 객체의 구매목록이나 가격같은 정보를 영수증프린터(HDReceiptPrinter 클래스) 에 전달해 출력하도록 한 프로그램, 아래 문제를 해결할 수 있는 방법을 제안하시오
 *
 *
 * - 주문을 입력받을 수 있는 기능을 설계하시오
 * - 기존 프로그램은 테스트할 때 실제 영수증 프린터 기기를 연결해 제대로 출력되는지를 살펴봐야 한다. 영수증 프린터 기기를 직접 연결하지 않아도 살펴볼 수 있도록 설계하시오
 * */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class Item{
    private int price;
    private String name;

    public Item(String name,int price){
        this.price = price;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return (new StringBuffer()
                .append("Product Name : ")
                .append(this.getName())
                .append(" | Product Price : ")
                .append(this.getPrice())
                .append(" ₩")).toString();
    }

}

// printable 인터페이스
interface printable{
    public abstract void print(String information);
}

// 영수증 프린터 기기
class HD108ReceiptPrinter implements printable{
    @Override
    public void print(String information) {
        System.out.println(information);
    }
}

// 영수증 프린터 기기가 아닌 테스트용 기기
class TestPrinter implements printable{
    @Override
    public void print(String information) {
        System.out.println(information);
    }
}


class orderProductWindow{
    private static orderProductWindow inst = null;
    private ArrayList<Item> exampleProductList = new ArrayList<>();

    private orderProductWindow(){
        exampleProductList.add(new Item("Phone",100000));
        exampleProductList.add(new Item("Book",2000));
        exampleProductList.add(new Item("PC",3000));
    }

    public void addProduct(String name, int price){
        exampleProductList.add(new Item(name,price));
    }

    public static orderProductWindow getWindow(){
        if(inst == null){
            inst = new orderProductWindow();
        }
        return inst;
    }

    public Item window(){
        boolean loop = true;
        int choice=0;
        while(loop){
            Iterator<Item> itr = this.exampleProductList.iterator();
            int count = 0;
            while(itr.hasNext()){
                Item i = itr.next();
                System.out.println(new StringBuilder().append(count + 1).append(". ").append(i));
                count += 1;
            }
            Scanner sc = new Scanner(System.in);
            System.out.print(">> ");
            choice = Integer.parseInt(sc.nextLine());
            if(1 <= choice && choice <= this.exampleProductList.size()){
                loop = false;
            }
        }
        return this.exampleProductList.get(choice - 1);
    }

}

class Sale {
    private orderProductWindow w;
    private ArrayList<Item> items = new ArrayList<Item>();
    private printable printer;

    Sale(printable printer){
        this.printer = printer;
        w = orderProductWindow.getWindow();
    }

    public void setPrinter(printable printer){
        this.printer = printer;
    }

    public void printReceipt() {
        Iterator<Item> itr = items.iterator();
        StringBuffer buf = new StringBuffer();
        int count = 1;
        System.out.println("[ My shopping list receipt ]");
        while(itr.hasNext()) {
            Item item = itr.next();
            buf.append("\n").append(count).append(". ").append(item.getName()).append(" | ").append(item.getPrice()).append("\n");
            count += 1;
        }
        printer.print(buf.toString());
    }
    public void add() {
        items.add(w.window());
    }
}

class main_t{
    public static void main(String[] args){
        Sale s = new Sale(new TestPrinter());
        s.add();
        s.add();
        s.printReceipt();
    }
}