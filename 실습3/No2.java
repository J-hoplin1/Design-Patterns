import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

abstract class Member{
    public void buy(int price){
        // 구매한다는 의미의 출력문
        System.out.println(new StringBuilder("You need to pay : ").append(price).toString());
    }
}


// 집약관계를 가지는 객체들

// VIPMember에 대해서 집약관계를 가짐
class DiscountCoupon{
    private int discoutPercentage;

    DiscountCoupon(int percentage){
        this.discoutPercentage = percentage;
    }

    public int getDiscoutPercentage() {
        return discoutPercentage;
    }

    public void setDiscoutPercentage(int discoutPercentage) {
        this.discoutPercentage = discoutPercentage;
    }
}

// OrdLocal에 대한 집약관계를 가짐
class Gift{}

// VIPMember에 대한 일반화
class VIPMember<T extends DiscountCoupon> extends Member{
    ArrayList<T> lt;

    @Override
    public String toString() {
        try{
            StringBuilder st = new StringBuilder("--------------------\n");
            Iterator it = lt.iterator();
            st.append("My Coupon List\n");
            int couponNumber = 1;
            while(it.hasNext()){
                T e = (T)(it.next());
                st.append(couponNumber).append(". ").append(e.getDiscoutPercentage()).append("% discount coupon \n");
                couponNumber++;
            }
            st.append("--------------------");
            return st.toString();
        }catch (NullPointerException n){
            //쿠폰함이 비어있을 경우 예외 발생 -> NullPointerException
            return (new StringBuilder("Warning : Coupon not exist!")).toString();
        }
    }

    @Override
    public void buy(int price) {
        if(lt.isEmpty()){
            System.out.println("Coupon not exist");
            super.buy(price);
        }else{
            DiscountCoupon discount_coupon = (T) lt.remove(0);
            float rate = ((float)discount_coupon.getDiscoutPercentage()) / 100;
            super.buy((int)(price * (1-rate)));
        }
    }

    // 집약관계
    public void setLt(ArrayList<T> lt){
        this.lt = lt;
    }

    public void addCoupon(T coupon){
        lt.add(coupon);
    }
}

class VIPLocal<T extends DiscountCoupon> extends VIPMember<T>{}

class VIPNonLocal<T extends DiscountCoupon> extends VIPMember<T>{}


// OridinaryMember에 대한 일반화
class OrdinaryMember extends Member{
}

class OrdLocal<T extends Object> extends OrdinaryMember{
    ArrayList<T> list;
    OrdLocal(ArrayList<T> list){
        this.list = list;
    }
}

class OrdNonLocal extends OrdinaryMember{}

class main_t{
    public static void main(String[] args){
        // Coupon set 1
        ArrayList<DiscountCoupon> couponbook1 = new ArrayList<>();
        couponbook1.add(new DiscountCoupon(25));
        couponbook1.add(new DiscountCoupon(10));
        couponbook1.add(new DiscountCoupon(100));

        //Coupon set 2
        ArrayList<DiscountCoupon> couponbook2 = new ArrayList<>();
        couponbook2.add(new DiscountCoupon(30));
        couponbook2.add(new DiscountCoupon(50));

        VIPMember<DiscountCoupon> m1 = new VIPLocal<>();
        VIPMember<DiscountCoupon> m2 = new VIPLocal<>();

        System.out.println(m1);

        m1.setLt(couponbook1);
        m2.setLt(couponbook2);

        System.out.println(m1);

        VIPMember<DiscountCoupon> m3 = new VIPNonLocal<>();
        VIPMember<DiscountCoupon> m4 = new VIPNonLocal<>();

        m3.setLt(couponbook1);
        m4.setLt(couponbook2);

        m1.buy(10000);
        m1.buy(5000);
        m1.buy(10000);
        m1.buy(10000);
        m1.addCoupon(new DiscountCoupon(40));
        m1.buy(10000);

        m2.buy(10000);
        m3.buy(10000);
        m4.buy(10000);
    }
}
