import java.util.Vector;

class Queue<T extends Object>{
    Vector<T> t = new Vector<>();
    public void enque(T element){
        t.add(element);
    }
    public T deque(){
        return t.remove(0);
    }
}
