import java.util.*;

// 원래 실습에는 코드 작성하는게 없지만, 감이 안와 코드를 작성했습니다.

abstract class Observer{
    protected int secureLevel;
    protected Subject sensor;
    public Observer(int secureLevel,Subject sensor){
        this.secureLevel = secureLevel;
        this.sensor = sensor;
    }
    public void spot(){
        Object[] res = sensor.getStates();
        if((int)res[0] <= this.secureLevel){
            behavior();
        }
    }
    public abstract void behavior();
}

class SpotCorrespondentLv1 extends Observer{
    public SpotCorrespondentLv1(int secureLevel,Subject sensor){
        super(secureLevel,sensor);
    }

    @Override
    public void behavior() {
        System.out.println("Light On");
    }
}

class SpotCorrespondentLv2 extends Observer{
    public SpotCorrespondentLv2(int secureLevel, Subject sensor) {
        super(secureLevel, sensor);
    }

    @Override
    public void behavior() {
        System.out.println("Alarm On");
    }
}

class SpotCorrespondentLv3 extends Observer{
    public SpotCorrespondentLv3(int secureLevel, Subject sensor) {
        super(secureLevel, sensor);
    }

    @Override
    public void behavior() {
        System.out.println("Activate Phone : Call police");
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
    }
    public Object[] getStates(){
        Object[] returnvalue = {info.get(key1),info.get(key2)};
        return returnvalue;
    }
}
