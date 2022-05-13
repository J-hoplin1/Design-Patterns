import java.util.HashMap;

/*
* Source Code written by Hoplin
*
* 2022 / 04 / 11
* Design Pattern Assignment_1
* */

class librarian{
    public void startRentalScreen(String id,String pw,String bookName){
        rentalScreen screen = new rentalScreen();
        boolean result = screen.enter(id,pw);
        if(result){
            screen.rental(bookName);
        }
    }
}

class rentalScreen{
    public boolean enter(String id, String pw){
        memberList mlist = new memberList();
        boolean res = mlist.check(id,pw);
        if(res){
            this.display("성공");
            return true;
        }else{
            this.display("실패");
            return false;
        }
    }
    public void display(String str){
        System.out.println(str);
    }
    public void rental(String bookName){
        book b = new book(bookName);
        this.display("대여성공");
    }
}

class memberList{
    HashMap<String, String> members = new HashMap<>();
    memberList(){
        members.put("test1","1234");
        members.put("test2","1234");
    }
    public boolean check(String id, String pw){
        /*
        * Source Code written by Hoplin, 윤준호
        * */
        if(members.containsKey(id)){
            return members.get(id).equals(pw);
        }else{
            return false;
        }
    }
}

class book{
    private String bookName;
    book(String bookName){
        this.bookName = bookName;
    }
}

// 테스트를 위한 main함수
class main_t{
    public static void main(String[] args){
        librarian l = new librarian();
        l.startRentalScreen("test1","1234","Book");
        l.startRentalScreen("test2","1234","Book2");
        l.startRentalScreen("test3","1234","Book3");
    }
}
