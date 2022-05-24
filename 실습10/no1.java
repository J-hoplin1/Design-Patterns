import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface Observer{
    public abstract void update();
}

class DataSheetView implements Observer{
    private ScoreRecord scoreRecord;
    private int viewCount;

    public DataSheetView(ScoreRecord scoreRecord, int viewCount){
        this.scoreRecord = scoreRecord;
        this.viewCount = viewCount;
    }
    @Override
    public void update() {
        List<Integer> record = scoreRecord.getScoreRecord();
        displayScores(record,viewCount);
    }

    public void displayScores(List<Integer> record, int viewCount){
        System.out.println("List of " + viewCount + " entries");
        for(int i = 0; i < viewCount && i < record.size();i++){
            System.out.println(record.get(i));
        }
        System.out.println();
    }
}

class MinMaxView implements Observer{
    private ScoreRecord scoreRecord;

    public MinMaxView(ScoreRecord scoreRecord){
        this.scoreRecord = scoreRecord;
    }

    @Override
    public void update() {
        List<Integer> record = scoreRecord.getScoreRecord();
        displayMinMaxList(record);
    }

    public void displayMinMaxList(List<Integer> record){
        int min = Collections.min(record,null);
        int max = Collections.max(record,null);
        System.out.println("Min : " + min + " Max : " + max);
    }
}

abstract class Subject{
    private List<Observer> observers = new ArrayList<>();
    public void attatch(Observer observer){
        observers.add(observer);
    }
    public void detach(Observer observer){
        observers.remove(observer);
    }
    public void notifyObservers(){
        for(Observer o : observers){
            o.update();
        }
    }
}

class ScoreRecord extends Subject{
    private List<Integer> scores = new ArrayList<>();
    public void addScore(int score){
        scores.add(score);
        notifyObservers();
    }
    public List<Integer> getScoreRecord(){
        return scores;
    }
}

class Client{
    public static void main(String[] args){
        ScoreRecord scoreRecord = new ScoreRecord();
        DataSheetView dataSheetView3 = new DataSheetView(scoreRecord,3);
        DataSheetView dataSheetView5 = new DataSheetView(scoreRecord,5);
        MinMaxView minMaxView = new MinMaxView(scoreRecord);
        scoreRecord.attatch(dataSheetView3);
        scoreRecord.attatch(dataSheetView5);
        scoreRecord.attatch(minMaxView);

        for(int index = 1; index < 5;index++){
            int score = index * 10;
            System.out.println("Adding " + score);
            scoreRecord.addScore(score);
        }
    }
}