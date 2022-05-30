import java.util.*;

// 원래 실습에는 코드 작성하는게 없지만, 감이 안와 코드를 작성했습니다.

abstract class Observer{
    protected int secureLevel;
    protected Subject sensor;
    public Observer(int secureLevel,Subject sensor){
        this.secureLevel = secureLevel;
        this.sensor = sensor;
    }

    public boolean checkSecureLevelValid(){
        return ((Integer) this.sensor.getStates()[0] >= this.secureLevel) && ((Boolean)this.sensor.getStates()[1]);
    }

    public void spot(){
        behavior();
    }

    public int getSecureLevel(){
        return this.secureLevel;
    }
    public abstract void behavior();
}

class SpotCorrespondentLv1 extends Observer{
    public SpotCorrespondentLv1(int secureLevel,Subject sensor){
        super(secureLevel,sensor);
    }

    @Override
    public void behavior() {
        if(checkSecureLevelValid()){
            System.out.println("Light On");
        }
    }
}

class SpotCorrespondentLv2 extends Observer{
    public SpotCorrespondentLv2(int secureLevel, Subject sensor) {
        super(secureLevel, sensor);
    }

    @Override
    public void behavior() {
        if(checkSecureLevelValid()){
            System.out.println("Alarm On");
        }
    }
}

class SpotCorrespondentLv3 extends Observer{
    public SpotCorrespondentLv3(int secureLevel, Subject sensor) {
        super(secureLevel, sensor);
    }

    @Override
    public void behavior() {
        if(checkSecureLevelValid()){
            System.out.println("Activate Phone : Call police");
        }
    }
}

abstract class Subject{
    private List<Observer> observers = new ArrayList<>();
    public void attach(Observer observer){observers.add(observer);}
    public void detach(Observer observer){observers.remove(observer);}
    public void notifyObserver(){
        for(Observer o : observers){
            o.spot();
        }
    }
    public abstract void setStates(int level,boolean detected);
    public abstract Object[] getStates();
}

class Sensor extends Subject{
    private final String key1 = "SecureLevel";
    private final String key2 = "IsDetected";
    private Map<String, Object> info = new HashMap<>();
    public Sensor(){
        info.put(key1,0);
        info.put(key2,false);
    }

    public void setStates(int level,boolean detected){
        info.put(key1,level);
        info.put(key2,detected);
        notifyObserver();
    }
    public Object[] getStates(){
        return new Object[]{info.get(key1),info.get(key2)};
    }
}

class main{
    public static void main(String[] args){
        Subject s = new Sensor();
        s.attach(new SpotCorrespondentLv1(1,s));
        s.attach(new SpotCorrespondentLv2(2,s));
        s.attach(new SpotCorrespondentLv3(3,s));
        s.setStates(2,true);
    }
}