import java.util.Calendar;

class MP3{
    MP3 m;
    public void play(){
        System.out.println("Play music");
    }
    public void stop(){
        System.out.println("Stop music");
    }
}

interface TimeProvider{
    public abstract boolean playMusic();
}
class RealTimeProvider implements TimeProvider{
    private int time;
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    RealTimeProvider(int time){
        if(time < 0 || time >=24){
            System.out.println("Wrong time type. Set default as 22:00");
            this.setTime(22);
        }
        else{
            this.setTime(time);
        }
    }
    @Override
    public boolean playMusic() {
        // Get Calendar instance and get time data
        Calendar c = Calendar.getInstance();
        // Use Calendar HOUR_OF_DAY via Java Date Depecated(JDK 1.1) -> https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Date.html
        int time = c.get(Calendar.HOUR_OF_DAY);
        if(time == this.time){
            return true;
        }
        return false;
    }
}
class FakeTimeProvider implements TimeProvider{
    @Override
    public boolean playMusic() {
        return true;
    }
}

class TimeReminder{
    TimeProvider ft = new FakeTimeProvider();
    TimeProvider rt;
    TimeReminder(int time){
        rt = new RealTimeProvider(time);
    }
    public void Reminder(MP3 m){
        while(rt.playMusic()){}
        m.play();
    }
    public void JustPlay(MP3 m){
        System.out.println("Play music with JustPlay()");
        if(ft.playMusic()){
            m.play();
        }
    }
}
class main_t{
    public static void main(String[] args){
        MP3 m = new MP3();
        TimeReminder c = new TimeReminder(14);
        c.Reminder(m);
        c.JustPlay(m);
    }
}