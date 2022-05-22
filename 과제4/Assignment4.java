// 상태코드
// Default Global Messages
class MSGS{
    public static final String checkoutComplete = "대출이 완료되었습니다.";
    public static final String unabletocheckout = "대출 불가능 상태입니다.";
    public static final String reservationcanceled = "예약이 취소되었습니다.";
    public static final String reserved = "예약되었습니다.";
    public static final String failToReserve = "예약에 실패하였습니다.";
    public static final String failToCancelReservation="예약 취소에 실패하였습니다";
    public static final String notReservedYet = "이 책은 예약되지 않은 책입니다";
    public static final String returnComplete = "반납이 완료되었습니다";
    public static final String returnFailed = "반납에 실패하였습니다";
    public static final String bookWillBeStored = "책이 저장됩니다";
    public static final String nameDifferent = "예약된 이름이 일치하지 않습니다";
}

// Book Class
class Book{
    private final String bookname;
    private String reservePeople="";
    private static final int storeTerm = 20;
    private State state;

    public Book(String bookname){
        this.bookname = bookname;
        this.state = BeforeCheckout.getInstance();
    }

    @Override
    public String toString() {
        return this.bookname;
    }

    public String getReservePeople(){
        return this.reservePeople;
    }
    public void setReservePeople(String reservePeople){
        this.reservePeople = reservePeople;
    }

    public void setState(State state){
        this.state = state;
    }


    // Reqeust of a book checkout
    public void CheckOutRequestFromClient(){
        state.checkout(this);
    }

    // 예약 요청
    public void BookReserveRequestFromClient(String name){
        state.makereservation(this,name);
    }

    // 예약 취소 요청
    public void BookReserveCancelRequestFromClient(String name){
        state.cancelreservation(this,name);
    }

    // 저장된 책을 찾아가는 요청
    public void GetStoredBookRequestFromClient(String name){
        state.getStoredBook(this,name);
    }

    // 책 반납 요청
    public void BookReturnRequestFromClient(){
        state.returnbook(this);
        System.out.println(MSGS.returnComplete);
    }

    // 시간 만료로 인한 강제 반납(상태 변경)
    public void BookReturnViaStoreTermFinished(int testTime){
        if(testTime >= storeTerm){
            state.storetermfinished(this);
            System.out.println("보관기간이 만료되어 반납되었습니다");
        }else{
            System.out.println("아직 보관기간이 안끝났습니다.");
        }
    }

    public void printBookStatus(){
        StringBuilder status = new StringBuilder();
        status.append("Book Name : ").append(this).append("\n").append("Status : ");
        status.append(state.getClass().getName());
        status.append("\n").append("Reserved People : ").append(reservePeople);
        System.out.println(status.toString());
    }

}

// State Interface
interface State{
    public abstract void checkout(Book book);
    public abstract void makereservation(Book book,String name);
    public abstract void returnbook(Book book);
    public abstract void cancelreservation(Book book,String name);
    public abstract void storetermfinished(Book book);
    public abstract void getStoredBook(Book book, String name);
}

class BeforeCheckout implements State{
    private static BeforeCheckout instance;
    private BeforeCheckout(){}

    public static BeforeCheckout getInstance(){
        if(instance == null){
            instance = new BeforeCheckout();
        }
        return instance;
    }

    @Override
    public void checkout(Book book) {
        book.setState(NormalCheckOut.getInstance());
        System.out.println(MSGS.checkoutComplete);
    }

    @Override
    public void makereservation(Book book,String name) {}
    @Override
    public void returnbook(Book book) {}
    @Override
    public void cancelreservation(Book book,String name) {}
    @Override
    public void storetermfinished(Book book) {}

    @Override
    public void getStoredBook(Book book, String name) {

    }
}

class NormalCheckOut implements State{
    private static NormalCheckOut instance;
    private NormalCheckOut(){}

    // apply Single ton pattern
    public static NormalCheckOut getInstance(){
        if(instance == null){
            instance = new NormalCheckOut();
        }
        return instance;
    }

    @Override
    public void checkout(Book book) {}

    @Override
    public void makereservation(Book book,String name) {
        book.setState(Reserved.getInstance());
        book.setReservePeople(name);
        System.out.println(MSGS.reserved);
    }

    @Override
    public void returnbook(Book book) {
        book.setState(BeforeCheckout.getInstance());
        System.out.println(MSGS.returnComplete);
    }

    @Override
    public void cancelreservation(Book book,String name) {}

    @Override
    public void storetermfinished(Book book) {}

    @Override
    public void getStoredBook(Book book, String name) {

    }
}

class Reserved implements State{
    private static Reserved instance;
    private Reserved(){}

    public static Reserved getInstance(){
        if(instance == null){
            instance = new Reserved();
        }
        return instance;
    }

    @Override
    public void checkout(Book book) {

    }

    @Override
    public void makereservation(Book book,String name) {

    }

    @Override
    public void returnbook(Book book) {
        book.setState(Stored.getInstance());
        System.out.println(MSGS.bookWillBeStored);
    }

    @Override
    public void cancelreservation(Book book,String name) {
        if(book.getReservePeople().equals(name)){
            book.setState(NormalCheckOut.getInstance());
            book.setReservePeople("");
            System.out.println(MSGS.reservationcanceled);
        }else{
            System.out.println(MSGS.failToCancelReservation);
        }
    }

    @Override
    public void storetermfinished(Book book) {

    }

    @Override
    public void getStoredBook(Book book, String name) {

    }
}

class Stored implements State{
    private static Stored instance;
    private Stored(){}

    public static Stored getInstance(){
        if(instance == null){
            instance = new Stored();
        }
        return instance;
    }

    @Override
    public void checkout(Book book) {

    }

    @Override
    public void makereservation(Book book,String name) {

    }

    @Override
    public void returnbook(Book book) {

    }

    @Override
    public void cancelreservation(Book book,String name) {
        if(book.getReservePeople().equals(name)){
            book.setState(BeforeCheckout.getInstance());
            book.setReservePeople("");
            System.out.println(MSGS.reservationcanceled);
        }else{
            System.out.println(MSGS.failToCancelReservation);
        }
    }

    @Override
    public void storetermfinished(Book book) {
        book.setState(BeforeCheckout.getInstance());
        book.setReservePeople("");
        System.out.println("저장기간 만료");
    }

    @Override
    public void getStoredBook(Book book, String name) {
        if(book.getReservePeople().equals(name)){
            book.setState(NormalCheckOut.getInstance());
            book.setReservePeople("");
            System.out.println("저장된 책을 찾아갔습니다");
        }else{
            System.out.println("예약자와 이름이 일치하지 않습니다.");
        }
    }
}

class Person{
    private String name;
    Person(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class main_t{
    public static void main(String[] args){
        // 책의 예약 stream을 위한 테스트 객체
        Person p1 = new Person("Person 1");
        Person p2 = new Person("Person 2");

        Book b1 = new Book("Book 1");
        Book b2 = new Book("Book 2");

        System.out.println("< Status Code 의미 >\n");
        System.out.println("Not checkout yet : " + "대기상태(아무나 대출가능)");
        System.out.println("Chekced out : " + "대출된 상태");
        System.out.println("Reserved : " + "예약된 상태");
        System.out.println("Stored : " + "저장된 상태\n\n");

        System.out.println("[ Stream Scenario 1 ]\n");

        System.out.println("초기상태");
        b1.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("book1이 대출되었다고 가정한다. 대출이 되면, 대출중 상태로 바뀐다.");
        b1.CheckOutRequestFromClient();
        b1.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("대출이 완료된 상태에서는 다시 대출이 되어서는 안된다.");
        b1.CheckOutRequestFromClient();
        b1.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("Person1 이 책을 예약한다. 예약은 책이 대출되어있는 상태에서만 예약할 수 있다.");
        b1.BookReserveRequestFromClient(p1.getName());
        b1.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("예약이 되어있는 상태에서 다른 사람이 예약을 할 수 없어야한다.");
        b1.BookReserveCancelRequestFromClient(p2.getName());
        b1.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("책이 반환되었다고 가정한다. 예약상태의 책은 반환되면 예약자에게 대출해주기 위해 일정 기간 보관한다.");
        b1.BookReturnRequestFromClient();
        b1.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("책이 보관된 상태에서는 책을 대출할 수 없다.");
        b1.CheckOutRequestFromClient();
        b1.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("예약은 대출중인 상태에서만 가능하므로, Person2는 예약이 불가능해야한다.");
        b1.BookReserveRequestFromClient(p2.getName());
        b1.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("저장중인 책을 예약한 사람이 가져간다. 예약한 사람이 가져가면, 책은 대출중 상태가 되어야 한다.");
        b1.GetStoredBookRequestFromClient(p1.getName());
        b1.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("Person2가 Book1을 예약한다. 예약을 하면 예약중 상태로 바뀐다.");
        b1.BookReserveRequestFromClient(p2.getName());
        b1.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("Person2가 예약을 취소한다. 예약을 취소하면 대출중 상태로 바뀐다");
        b1.BookReserveCancelRequestFromClient(p2.getName());
        b1.printBookStatus();

        System.out.println("\n\n[ Stream Scenario 2 ]\n");
        System.out.println("초기상태");
        b2.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("책을 대출한다.");
        b2.CheckOutRequestFromClient();
        b2.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("예약자 없이 책을 반납한다.");
        b2.BookReturnRequestFromClient();
        b2.printBookStatus();
        System.out.println("---------------------------");

        System.out.println("\n\n[ Stream Scenario 3 ]\n");

        System.out.println("초기상태");
        b2.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("책을 대출한다");
        b2.CheckOutRequestFromClient();
        b2.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("Person2가 예약을 한다");
        b2.BookReserveRequestFromClient(p2.getName());
        b2.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("책이 반납된다");
        b2.BookReturnRequestFromClient();
        b2.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("책 저장중에 예약을 취소한다. 취소하면 초기상태로 간다");
        b2.BookReserveCancelRequestFromClient(p2.getName());
        b2.printBookStatus();
        System.out.println("---------------------------");

        System.out.println("\n\n[ Stream Scenario 4 ]\n");

        System.out.println("초기상태");
        b2.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("책을 대출한다");
        b2.CheckOutRequestFromClient();
        b2.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("Person2가 예약을 한다");
        b2.BookReserveRequestFromClient(p2.getName());
        b2.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("책이 반납된다");
        b2.BookReturnRequestFromClient();
        b2.printBookStatus();
        System.out.println("---------------------------");
        System.out.println("저장기간이 만료되어 아무나 대출할 수 있는 상태가 된다고 가정한다.");
        b2.BookReturnViaStoreTermFinished(20);
        b2.printBookStatus();

    }
}