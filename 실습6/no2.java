/*
 *
 * Source Code Written by B889047윤준호
 * */

import java.util.*;

//Strategy : ln[149]


//  Saleable 인터페이스
interface SaleablePolicy{
    public abstract void sale(MemberType member,Book book);
}

/*
* 1번 코드를 기반으로 각 할인 정책에 따른 할인률에 멤버 타입에 따른 할인을 더한 다음에 할인률을 계산하는 방식으로 변경한다.
* */


// 10년이 넘은 책에 대한 할인
class BookOverTenYearSale implements SaleablePolicy{
    private final float DISCOUNT_RATE = 10;
    private final int year;
    BookOverTenYearSale(){
        Calendar cal = Calendar.getInstance();
        this.year = cal.get(Calendar.YEAR);
    }
    @Override
    public void sale(MemberType member,Book book) {
        if(this.year - book.getPublishedDate() > 10){
            float discount_rate = (float)(1-((DISCOUNT_RATE + member.getAdditionalDiscount()) / 100)); // Changed
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
    public void sale(MemberType member, Book book) {
        if(member.getAccumulatedPrice() > 10000){
            float discount_rate = (float)(1-((DISCOUNT_RATE + member.getAdditionalDiscount())/ 100)); // Changed
            int discounted_price = (int) (book.getPrice() * discount_rate);
            member.setAccumulatedPrice(member.getAccumulatedPrice() + discounted_price);
        }else{
            member.setAccumulatedPrice(member.getAccumulatedPrice() + book.getPrice());
        }
    }
}


// 회원종류에 따른 추상클래스
abstract class MemberType{
    private final float additionalDiscount;
    private int accumulatedPrice;
    private String name;

    private Map<String,Integer> buyBookList = new HashMap<>();
    MemberType(int additionalDiscount, int accumulatedPrice, String name){
        this.additionalDiscount = additionalDiscount;
        this.accumulatedPrice = accumulatedPrice;
        this.name = name;
    }

    public void addBuyBook(Book book){
        if(buyBookList.containsKey(book.getSignature())){
            buyBookList.replace(book.getSignature(),(Integer)(buyBookList.get(book.getSignature())) + 1);
        }
        else{
            buyBookList.put(book.getSignature(),1);
        }
        /* For test
        System.out.println("---------");
        for(Map.Entry<String,Integer> entry : buyBookList.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("---------");
        */
    }


    public float getAdditionalDiscount(){
        return this.additionalDiscount;
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



// 일반 회원클래스
class NormalMember extends MemberType{

    NormalMember(String name){
        super(0,0,name);
    }

    @Override
    public void addBuyBook(Book book) {
        super.addBuyBook(book);
    }
}

// VIP 회원 클래스
class VIPMember extends MemberType{
    VIPMember(String name){
        super(5,0,name);
    }

    @Override
    public void addBuyBook(Book book) {
        super.addBuyBook(book);
    }
}


// 책 구매 클래스
class BookBuyer{
    private SaleablePolicy policy;

    public void setPolicy(SaleablePolicy policy){
        this.policy = policy;
    }
    public BookBuyer(SaleablePolicy policy){
        this.policy = policy;
    }

    public void buyBook(MemberType member, Book book){
        policy.sale(member,book); // Strategy
        member.addBuyBook(book);
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
        MemberType m1 = new VIPMember("A");
        MemberType m2 = new NormalMember("B");

        Book b1 = new Book(5000,"Java",2000);
        Book b2 = new Book(10000, "Python",2021);

        BookBuyer bb = new BookBuyer(new AccumulateTenThousandSale()); // Basic Poilicy : 5% discount if Accumulate Ten Thousand won

        bb.buyBook(m1,b1);
        bb.buyBook(m1,b1);
        bb.buyBook(m1,b2);
        bb.setPolicy(new BookOverTenYearSale()); // Change Policy : 10% discount if book publised more than 10 year
        bb.buyBook(m1,b1);
        System.out.println(m1.getAccumulatedPrice()); //24250


        bb.buyBook(m2,b1);
        bb.buyBook(m2,b1);
        bb.buyBook(m2,b1);
        bb.buyBook(m2,b1);
        bb.buyBook(m2,b1);
        System.out.println(m2.getAccumulatedPrice()); //22500
    }
}