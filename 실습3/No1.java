// 1번

import java.util.ArrayList;

class Book{
    StudyingStudent sd;
}

class Toy{
    PlayingStudent ps;
}

abstract class Student{
    public abstract void addItem();
}

class StudyingStudent extends Student{
    ArrayList<Book> lt = new ArrayList<>();
    @Override
    public void addItem() {

    }
}

class PlayingStudent extends Student{
    ArrayList<Toy> lt = new ArrayList<>();
    @Override
    public void addItem() {

    }
}
