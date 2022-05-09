// 3번
class Person{
    private Role r;
    public Role getRole(){
        return r;
    }
    public void setRole(){

    }

}

abstract class Role{
    abstract void BehaveMyRole();
}

class Developer extends Role{
    @Override
    void BehaveMyRole() {

    }
}

class Studnet extends Role{
    @Override
    void BehaveMyRole() {

    }
}