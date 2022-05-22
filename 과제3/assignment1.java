/*
* 	학생은 식권 발행기를 이용해 식권을 발행 받을 수 있다.
	학생은 발행 받은 식권을 식권 회수자에게 제출하고, 음식을 받는다.
	식권 발행기는 단 하나만 존재한다.
	식권 회수자는 단 하나만 존재한다.
	식권 종류는 한식, 중식, 양식으로 나뉘고, 학생은 하나의 식권만 선택할 수 있다.
	발행될 수 있는 식권 개수는 100개로 한정되어 있고, 100개가 초과되면 더 이상 식권을 발행 받을 수 없다.

* */

import java.util.Vector;
import java.util.Random;

enum mealtypes{
    Korean_dish,
    Chinese_dish,
    Japanese_dish;

    public static String getRandomValues(){
        mealtypes[] values = mealtypes.values();
        int randomIndex = new Random().nextInt(values.length);
        return values[randomIndex].name();
    }
}

// 식권 발행기
class MealTicketProvider{
    // 총 100개의 식권으로 한정되어있다.
    private final int MAX_TICKET_COUNT=100;
    private int ticket_count = 100;
    private static MealTicketProvider mtp;



    private MealTicketProvider(){
    }
    public boolean MealTicketAvailable(){
        return this.ticket_count > 0;
    }
    public void substractTicketCounter(){
        this.ticket_count --;
    }

    // Instance Return - Singleton : 식권 발행기는 단 하나만 존재한다.
    public synchronized static MealTicketProvider getMealTicketProvider(){
        if(MealTicketProvider.mtp == null){
            MealTicketProvider.mtp = new MealTicketProvider();
        }
        return MealTicketProvider.mtp;
    }

    public ticket getTicket(){
        this.substractTicketCounter();
        ticket res = new ticket(mealtypes.getRandomValues());
        System.out.println(Thread.currentThread().getName() + " get ticket! : " + res);
        return res;
    }

    public synchronized void ticket_provide_main(studentthread st){
        if(!MealTicketAvailable()){
            System.out.println("모든 식권이 팔렸습니다! - " + Thread.currentThread().getName() + "님은 식권을 발급받지 못했습니다.");
        }
        else{
            st.setTicket(this.getTicket());
        }
    }
}

class ticket{
    // 식권 종류
    private String ticketType;
    ticket(String ticketType){
        this.ticketType = ticketType;
    }
    public String getTicketType(){
        return this.ticketType;
    }

    @Override
    public String toString(){
        return this.ticketType;
    }
}

// 식권 회수자
class MealTicketCollector{
    private static MealTicketCollector mtc = null;
    private Vector<ticket> ticketBucket;

    private MealTicketCollector(){
        this.ticketBucket = new Vector<>();
    }


    // Instance Return - Singleton : 식권 회수기는 단 하나만 존재한다.
    public synchronized static MealTicketCollector getMealTicketCollector(){
        if(MealTicketCollector.mtc == null){
            MealTicketCollector.mtc = new MealTicketCollector();
        }
        return MealTicketCollector.mtc;
    }

    public void addCollectedTicketToBucket(ticket t){
        ticketBucket.add(t);
    }

    public synchronized void ticket_collector_main(studentthread st){
        if(st.hasATicket()){
            ticket returnedTicket = st.returnTicket();
            System.out.println(Thread.currentThread().getName() + "님이 식권을 반납했습니다! " + returnedTicket + "를 드립니다.");
            this.addCollectedTicketToBucket(returnedTicket);
        }else{
            System.out.println(Thread.currentThread().getName()+ "님은 반납할 식권이 없습니다!");
        }
    }
}

// 학생객체
class studentthread extends Thread{
    private ticket t = null;

    studentthread(String name){
        super(name);
    }

    public void setTicket(ticket t){
        this.t = t;
    }
    public ticket getTicket(){return this.t;}

    // 티켓 반납
    public ticket returnTicket(){
        ticket saveTicket = this.t;
        this.t = null;
        return saveTicket;
    }
    public boolean hasATicket(){
        return (this.t != null);
    }
    @Override
    public void run() {
        MealTicketProvider mtp = MealTicketProvider.getMealTicketProvider();
        mtp.ticket_provide_main(this);
        MealTicketCollector mtc = MealTicketCollector.getMealTicketCollector();
        mtc.ticket_collector_main(this);
    }
}

class main_test{
    public final static int MAX = 103;
    public static void main(String[] args){
        Vector<studentthread> stl = new Vector<>();
        for(int i = 1; i < MAX; i++){
            stl.add(new studentthread("Student " + (i)));
        }
        for(int i = 1; i < MAX;i++){
            stl.get(i-1).start();
        }
    }
}
