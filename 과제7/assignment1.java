import java.util.*;
/*
* B889047 윤준호
* */

abstract class TicketPrice{
    // 티켓의 가격이 저장되는 클래스 변수이다.
    protected static int price;
    protected static int totalDiscountRate = 0;
    protected int getPrice(){
        return price;
    }
    protected void setPrice(int price){
        TicketPrice.price = price;
    }
    protected void addDiscountRate(int discountRate){
        totalDiscountRate += discountRate;
    }
    public int getFinalDiscountRate(){
        return totalDiscountRate;
    }
    public int getFinalPrice(){
        System.out.println();
        return (int)((float)price * (1 - ((float)totalDiscountRate/(float) 100)));
    }
    public abstract void calculatePrice();
}

// 기본 가격 클래스 : 가격을 입력받는다.
class DefaultPrice extends TicketPrice{
    @Override
    public void calculatePrice() {
        setPrice(selector.SelectTicket());
    }
}

abstract class DiscountPrice extends TicketPrice{
    private TicketPrice ticketPrice;
    DiscountPrice(TicketPrice ticketPrice){
        this.ticketPrice = ticketPrice;
    }
    @Override
    public void calculatePrice() {
        ticketPrice.calculatePrice();
    }
}

class CardDiscount extends DiscountPrice{
    public CardDiscount(TicketPrice ticketPrice) {
        super(ticketPrice);
    }

    @Override
    public void calculatePrice() {
        super.calculatePrice();
        cardDiscount();
    }

    private void cardDiscount(){
        int rate = selector.CardDiscountList();
        System.out.println("카드 할인률 : " + rate + "%");
        addDiscountRate(rate);
    }
}

class TelecomDiscount extends DiscountPrice{
    public TelecomDiscount(TicketPrice ticketPrice) {
        super(ticketPrice);
    }

    @Override
    public void calculatePrice() {
        super.calculatePrice();
        telecomDiscount();
    }
    private void telecomDiscount(){
        int rate = selector.telecomDiscountList();
        System.out.println("통신사 할인률 : " + rate + "%");
        addDiscountRate(rate);
    }
}

class MembershipDiscount extends DiscountPrice{
    public MembershipDiscount(TicketPrice ticketPrice) {
        super(ticketPrice);
    }

    @Override
    public void calculatePrice() {
        super.calculatePrice();
        memberShipDiscount();
    }
    private void memberShipDiscount(){
        int rate = selector.membershipDiscount();
        System.out.println("멤버쉽 할인률 : " + rate + "%");
        addDiscountRate(rate);
    }
}

class DateDiscount extends DiscountPrice{
    public DateDiscount(TicketPrice ticketPrice) {
        super(ticketPrice);
    }

    @Override
    public void calculatePrice() {
        super.calculatePrice();
        dateDiscount();
    }
    private void dateDiscount(){
        int rate = selector.dateDiscount();
        System.out.println("요일 할인률 : " + rate + "%");
        addDiscountRate(rate);
    }
}


class selector{
    private static int discountSelector(Map<String,Integer> sets){
        ArrayList<String> list = new ArrayList<>(sets.keySet());
        while (true){
            try{
                System.out.println("---------------");
                for(int i = 0; i < list.size();i++){
                    System.out.println(i + " . " + list.get(i));
                }
                System.out.println("---------------");
                System.out.print("할인사항을 선택해주세요 >> ");
                Scanner sc = new Scanner(System.in);
                String selects = sc.nextLine();
                int totalDiscountRate = 0;
                for(String i : selects.split(" ")){
                    totalDiscountRate += sets.get(list.get(Integer.parseInt(i)));
                }
                return totalDiscountRate;
            }catch (IndexOutOfBoundsException e){
                System.out.println("다시 선택해 주세요");
            }
        }
    }
    public static int SelectTicket(){
        Map<String,Integer> priceInfos = new HashMap<>();
        priceInfos.put("1일 자유이용권",10000);
        priceInfos.put("2일 자유이용권",15000);
        priceInfos.put("5개 놀이기구 이용권",3000);
        ArrayList<String> list = new ArrayList<>(priceInfos.keySet());
        while (true){
            try{
                System.out.println("---------------");
                for(int i = 0; i < list.size();i++){
                    System.out.println(i + " . " + list.get(i));
                }
                System.out.println("---------------");
                System.out.print("구매할 티켓을 선택하세요 >> ");
                Scanner sc = new Scanner(System.in);
                int selected = Integer.parseInt(sc.nextLine());
                System.out.println("\n구매한 티켓 정보 : " + list.get(selected));
                System.out.println("구매한 티켓 가격 : " + priceInfos.get(list.get(selected)));
                return priceInfos.get(list.get(selected));
            }catch (IndexOutOfBoundsException e){
                System.out.println("다시 선택해 주세요");
            }
        }
    }
    public static int CardDiscountList(){
        Map<String, Integer> discountList = new HashMap<>();
        discountList.put("해당사항 없음",0);
        discountList.put("삼성카드",30);
        discountList.put("신한카드",20);
        discountList.put("우리카드",40);
        return discountSelector(discountList);
    }
    public static int telecomDiscountList(){
        Map<String, Integer> discountList = new HashMap<>();
        discountList.put("해당사항 없음",0);
        discountList.put("KT",10);
        discountList.put("SKT",20);
        return discountSelector(discountList);
    }
    public static int membershipDiscount(){
        Map<String, Integer> discountList = new HashMap<>();
        discountList.put("멤버쉽에 가입되어있지 않습니다",0);
        discountList.put("멤버쉽에 가입되어있습니다",5);
        return discountSelector(discountList);
    }
    public static int dateDiscount(){
        Scanner sc = new Scanner(System.in);
        System.out.print("오늘은 무슨 요일인가요? >> ");
        String select = sc.nextLine();
        int discount_rate = 0;
        switch (select){
            case "토":
            case "일":
                discount_rate = 30;
                break;
            case "월":
            case "화":
            case "수":
            case "목":
            case "금":
                discount_rate = 50;
                break;
            default:
                break;
        }
        return discount_rate;
    }
}

class mainTest{
    public static void BuyTicket(){
        System.out.println("\n[ B889047 윤준호 과제 7 제출 ]\n");
        TicketPrice tp = new DateDiscount(new MembershipDiscount(new TelecomDiscount(new CardDiscount(new DefaultPrice()))));
        tp.calculatePrice();
        System.out.println("최종 할인률 : " + tp.getFinalDiscountRate() + "%");
        System.out.println("최종금액 : " + tp.getFinalPrice() + "원");
    }
    public static void main(String[] args){
        BuyTicket();
    }
}