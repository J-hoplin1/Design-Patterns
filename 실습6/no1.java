/*
* 
* Source Code Written by B889047윤준호
* */

import java.util.*;

//Strategy : ln[55]


//  Saleable 인터페이스
interface SaleablePolicy{
    public abstract void sale(Member member,Book book);
}


// 10년이 넘은 책에 대한 할인
class BookOverTenYearSale implements SaleablePolicy{
    private final float DISCOUNT_RATE = 10;
    private final int year;
    BookOverTenYearSale(){
        Calendar cal = Calendar.getInstance();
        this.year = cal.get(Calendar.YEAR);
    }
    @Override
    public void sale(Member member,Book book) {
        if(this.year - book.getPublishedDate() > 10){
            float discount_rate = (float)(1-(DISCOUNT_RATE / 100));
            int discounted_price = (int) (book.getPrice() * discount_rate);
            int price = member.getAccumulatedPrice() + discounted_price;
            member.setAccumulatedPrice(price);
        }else{
            int price = member.getAccumulatedPrice() + book.getPrice();
            member.setAccumulatedPrice(price);
        }
    }
}
// 만원이 넘은 회원에 대해
class AccumulateTenThousandSale implements SaleablePolicy{
    private final float DISCOUNT_RATE = 5;
    @Override
    public void sale(Member member, Book book) {
        if(member.getAccumulatedPrice() > 10000){
            float discount_rate = (float)(1-(DISCOUNT_RATE / 100));
            int discounted_price = (int) (book.getPrice() * discount_rate);
            member.setAccumulatedPrice(member.getAccumulatedPrice() + discounted_price);
        }else{
            member.setAccumulatedPrice(member.getAccumulatedPrice() + book.getPrice());
        }
    }
}


class BookBuyer{
    private SaleablePolicy policy;

    public void setPolicy(SaleablePolicy policy){
        this.policy = policy;
    }
    public BookBuyer(SaleablePolicy policy){
        this.policy = policy;
    }

    public void buyBook(Member member, Book book){
        policy.sale(member,book); // Strategy
        member.addBuyBook(book);
    }
}


// 회원클래스
class Member{
    private int accumulatedPrice;
    private String name;

    private Map<String,Integer> buyBookList = new HashMap<>();


    Member(String name){
        this.accumulatedPrice = 0;
        this.name = name;
    }

    public void addBuyBook(Book book){
        if(buyBookList.containsKey(book.getSignature())){
            buyBookList.replace(book.getSignature(),(Integer)(buyBookList.get(book.getSignature())) + 1);
        }
        else{
            buyBookList.put(book.getSignature(),1);
        }
        System.out.println("---------");
        for(Map.Entry<String,Integer> entry : buyBookList.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("---------");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccumulatedPrice() {
        return accumulatedPrice;
    }

    public void setAccumulatedPrice(int accumulatedPrice) {
        this.accumulatedPrice = accumulatedPrice;
    }
}

// 책 클래스
class Book{
    private int price;
    private String signature;
    private int publishedDate;

    Book(int price, String signature,int publishedDate){
        this.price = price;
        this.signature = signature;
        this.publishedDate = publishedDate;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    public int getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(int publishedDate) {
        this.publishedDate = publishedDate;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}


class main_t{
    public static void main(String[] args){
        Member m1 = new Member("A");
        Member m2 = new Member("B");

        Book b1 = new Book(5000,"Java",2000);
        Book b2 = new Book(10000, "Python",2021);

        BookBuyer bb = new BookBuyer(new AccumulateTenThousandSale());

        bb.buyBook(m1,b1);
        bb.buyBook(m1,b1);
        bb.buyBook(m1,b2);
        bb.setPolicy(new BookOverTenYearSale()); // Change Policy
        bb.buyBook(m1,b1);
        System.out.println(m1.getAccumulatedPrice()); //24500


        bb.buyBook(m2,b1);
        bb.buyBook(m2,b1);
        bb.buyBook(m2,b1);
        bb.buyBook(m2,b2);
        System.out.println(m2.getAccumulatedPrice()); //23500
    }
}