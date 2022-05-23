import java.util.*;

/*
*
* 솔직히 말하면 저 커맨드 패턴 제대로 이해 못하고 작성한거라...
* 보시는분들은 참고하세요... 이 코드는 참고 안하시는거 추천...
* */


abstract class Game{
    Button RedBtn;
    Button GreenBtn;
    Button BlueBtn;

    boolean result;

    protected Game(){}
    public abstract void GameScreen();
    public abstract void executeCommand1();
    public abstract void executeCommand2();
    public abstract void executeCommand3();
}

class Game1 extends Game{
    private static Game1 instance = new Game1();
    Random random = new Random();
    int[] randNumbers = new int[3];

    int maxValueIndex;
    private Button btn;

    private Game1(){
        super();
    }
    public static Game1 getInstance(){
        return instance;
    }
    public void initiateButton(){
        if(btn == null){
            btn = new Game1Button(new RedBtnCommandGame1(instance),new GreenBtnCommandGame1(instance),new BlueBtnCommandGame1(instance));
        }
    }
    @Override
    public void GameScreen() {
        initiateButton();
        Scanner sc = new Scanner(System.in);
        System.out.println("세 개의 주사위 중 가장 큰 값의 주사위를 고르는 게임. 세 개의 주사위가 생성되고, 숫자는 1~6 중에 랜덤으로 생성됨");
        System.out.println("\n빨강 버튼: 왼쪽 주사위 선택. 가장 큰 값이 아니라면 게임 오버");
        System.out.println("초록 버튼: 가운데 주사위 선택. 가장 큰 값이 아니라면 게임 오버");
        System.out.println("파란 버튼: 오른쪽 주사위 선택. 가장 큰 값이 아니라면 게임 오버");
        while (true){
            System.out.println("주사위를 굴립니다");
            SetRandomNumber();
            System.out.println("주사위를 굴렸습니다.");
            System.out.println("Left : " + randNumbers[0] + " Center : " + randNumbers[1] +" Right : " + randNumbers[2]);
            int selection;
            while(true){
                System.out.print("버튼을 누르세요 >> ");
                 selection = Integer.parseInt(sc.nextLine());
                if(selection < 1 || selection > 3){
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요");
                }else{
                    break;
                }
            }
            maxValueIndex = findMaxValueIndex();
            switch (selection){
                case 1:
                    btn.RedButtonPress();
                    break;
                case 2:
                    btn.GreenButtonPress();
                    break;
                case 3:
                    btn.BlueButtonPress();
                    break;
            }
            if(!result){
                System.out.println("Game Over!");
                System.exit(0);
            }else{
                System.out.println("Go to next stage!");
            }

        }
    }
    public int findMaxValueIndex(){
        int rt = 0;
        for(int i = 1;i<randNumbers.length;i++){
            if(randNumbers[rt] < randNumbers[i]){
                rt = i;
            }else{
                ;
            }
        }
        return rt + 1;
    }

    public void SetRandomNumber(){
        for(int i = 0; i< randNumbers.length;i++){
            randNumbers[i] = random.nextInt(6) + 1;
        }
    }

    @Override
    public void executeCommand1() {
        result = (maxValueIndex == 1);
    }

    @Override
    public void executeCommand2() {
        result = (maxValueIndex == 2);
    }

    @Override
    public void executeCommand3() {
        result = (maxValueIndex == 3);
    }
}

class Game1Button extends Button{
    public Game1Button(Command rcommand,Command gcommand, Command bcommand){
        super(rcommand, gcommand, bcommand);
    }
}

class RedBtnCommandGame1 implements Command{
    Game game;
    RedBtnCommandGame1(Game game){
        this.game = game;
    }
    @Override
    public void execute() {
        game.executeCommand1();
    }
}

class GreenBtnCommandGame1 implements Command{
    Game game;
    GreenBtnCommandGame1(Game game){
        this.game = game;
    }
    @Override
    public void execute() {
        game.executeCommand2();
    }
}

class BlueBtnCommandGame1 implements Command{
    Game game;
    BlueBtnCommandGame1(Game game){
        this.game = game;
    }
    @Override
    public void execute() {
        game.executeCommand3();
    }
}


class Button{
    private Command RedButtonCommand;
    private Command GreenButtonCommand;
    private Command BlueButtonCommand;

    private Command NormalCommand;

    public Button(Command command){
        this.NormalCommand = command;
    }

    public Button(Command rcommand,Command gcommand, Command bcommand){
        this.RedButtonCommand = rcommand;
        this.GreenButtonCommand = gcommand;
        this.BlueButtonCommand = bcommand;
    }

    public void NormalPressed(){
        this.NormalCommand.execute();
    }

    public void RedButtonPress(){
        RedButtonCommand.execute();
    }
    public void GreenButtonPress(){
        GreenButtonCommand.execute();
    }
    public void BlueButtonPress(){
        BlueButtonCommand.execute();
    }

}

interface Command{
    public abstract void execute();
}

class RedBtnCommandGameSelection implements Command{
    private Game game;
    RedBtnCommandGameSelection(Game game){
        this.game = game;
    }
    @Override
    public void execute() {
        game.GameScreen();
    }
}

class GreenBtnCommandGameSelection implements Command{
    private Game game;
    GreenBtnCommandGameSelection(Game game){
        this.game = game;
    }
    @Override
    public void execute() {
    }
}

class BlueBtnCommandGameSelection implements Command{
    private Game game;
    BlueBtnCommandGameSelection(Game game){
        this.game = game;
    }
    @Override
    public void execute() {
    }
}

class GameNames{
    public static final String Game1 = "주사위가 주사위고 또 주사위다";
    public static final String Game2 = "그쯤에서 재빨리 블록 빼내기";
    public static final String Game3 = "햄버거 가게의 당일치기 견습생";
}

class main{
    public static void main(String[] args){
        Button btnRed = new Button(new RedBtnCommandGameSelection(Game1.getInstance()));

        Scanner sc = new Scanner(System.in);
        System.out.println("[빨강버튼은 1을 입력, 초록버튼은 2를 입력, 파랑버튼은 3을 입력하세요]");
        int selection;
        while(true){
            System.out.print("게임을 선택하세요(게임1: 빨강버튼, 게임2: 초록버튼, 게임3: 파랑버튼) >> ");
            selection = Integer.parseInt(sc.nextLine());
            if(selection < 1 || selection > 3){
                System.out.println("잘못된 입력입니다. 다시 선택해 주세요");
            }else{
                System.out.println("게임 " + selection + "를 선택하셨습니다");
                break;
            }
        }
        // 게임 3개중 하나만 구현
        // 게임 1만 구현함
        switch (selection){
            case 1:
                btnRed.NormalPressed();
                break;
            case 2:
            case 3:
            default:
                System.out.println("아직 구현중인 게임입니다.");
        }

    }
}