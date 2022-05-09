import java.util.Vector;

class ZooDirector{
    private Zoo mZoo = new Zoo();
    public void control(int cmd){
        switch (cmd){
            case 1:
                System.out.println("Make Cat Cage");
                mZoo.makeCatCage();
                break;
            case 2:
                System.out.println("Remove Cat Cage");
                mZoo.removeCatCage();
                break;
            case 3:
                System.out.println("Add cat");
                mZoo.addCat();
                break;
            case 4:
                System.out.println("Remove cat");
                mZoo.removeCat();
                break;
            case 5:
                System.out.println("Make Dog Cage");
                mZoo.makeDogCage();
                break;
            case 6:
                System.out.println("Remove Dog Cage");
                mZoo.removeDogCage();
                break;
            case 7:
                System.out.println("Add dog");
                mZoo.addDog();
                break;
            case 8:
                System.out.println("Remove Dog");
                mZoo.removeDog();
                break;
            default:
                System.out.println((new StringBuilder().append("Wrong types of command : ").append(cmd)).toString());
        }
    }
}

class Zoo{
    private CatCage mCatCages;
    private DogCage mDogCages;
    public void makeCatCage(){
        // 시퀸스 다이어그램 상에서 표현이 안되어있기 때문에, 다이어그램 만으로 구현해야하는 경우에는 넣어서는 안됨(alt문이 있는 경우라면 가능)
        //
        /*
        if(mCatCages == null){
            this.mCatCages = new CatCage();
        }*/
        this.mCatCages = new CatCage();
    }
    public void removeCatCage(){
        this.mDogCages = null;
    }
    public void addCat(){
        this.mCatCages.addCat();
    }
    public void removeCat(){
        this.mCatCages.removeCat();
    }
    public void makeDogCage(){
        this.mDogCages = new DogCage();
    }
    public void removeDogCage(){
        this.mDogCages = null;
    }
    public void addDog(){
        this.mDogCages.addDog();
    }
    public void removeDog(){
        this.mDogCages.removeDog();
    }
}

class CatCage{
    private Vector<Cat> mCats = new Vector<>();
    public void addCat(){
        mCats.add(new Cat());
    }
    public void removeCat(){
        mCats.remove(mCats.size() - 1);
    }
}

class DogCage{
    private Vector<Dog> mDogs = new Vector<>();
    public void addDog(){
        mDogs.add(new Dog());
    }
    public void removeDog(){
        mDogs.remove(mDogs.size() - 1);
    }
}

class Cat{
    private int mState;
    public void sick(){}
    public void recover(){}
    public void dead(){}
}

class Dog{
    private int mState;
    public void sick(){}
    public void recover(){}
    public void dead(){}
}

class test_t{
    public static void main(String[] args){
        ZooDirector zd = new ZooDirector();
        zd.control(1); // make cat cage
        zd.control(5); // make dog cage
        zd.control(3); // add cat to cage
        zd.control(7); // add dog to cage
    }
}