// 실선 -> 멤버변수만
// 점선 -> 의존성

class who{
    public static void main(String[] args){
        A1 a1 = new A1();
        a1.doA1();
    }
}

class A1{
    public void doA1(){
        A2 a2 = new A2();
        a2.doA2(this);
    }
    public void doit(A3 a3){
        a3.doit();
    }
}

class A2{
    public void doA2(A1 a1){
        A3 a3 = new A3();
        a1.doit(a3);
    }
}

class A3{
    public void doit(){
    }
}