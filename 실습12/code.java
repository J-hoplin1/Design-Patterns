/**
 * 브리또 세가지 메뉴
 *
 * 세개의 다른 식재료 : 소고기 닭고기 크래미(이후에 재료 추가 가능성)
 *
 * 소스는 케첩 캡사이신중 하나 선택(이후에 맛 추가 가능성)
 *
 * 사용자가 재료, 소스를 선택하면, 브리또 레시피 출력
 *
 * 브리또 레시피는 대체적으로 유사하나 재료에 따라 손질 방법이 조금씩 다르다
 *
 * < 템플릿 적용 >
 *
 * -> 소스 추가, 재료 넣는 부분을 따로 놓는다.
 *
 */

import java.util.*;

// 기본 재료들에 대한 열거형
enum Ingredient{
    beef,
    chicken,
    crab
}

// 소스에 대한 열거형
enum Sauce{
    ketchup,
    capsaicin
}

// 부리또 추상 클래스
abstract class Buritto{
    protected Ingredient ingredient;
    protected Sauce sauce;

    public Buritto(Ingredient ingredient, Sauce sauce){
        this.ingredient = ingredient;
        this.sauce = sauce;
    }

    public void makeBuritto(String name){
        System.out.println("[ " + name + " 레시피 ] ");
        System.out.println("1. 양배추 썰기");
        System.out.print("2. ");
        trimIngredients();
        System.out.println("3. 또띠야 펴기");
        System.out.println("4. 밥 넣기");
        System.out.println("5. 양배추 넣기");
        System.out.print("6. ");
        addSauce();
        System.out.print("7. ");
        addTrimedIngredient();
        System.out.println("8. 또띠야 말기");

    }
    public abstract void trimIngredients();
    public abstract void addTrimedIngredient();
    public abstract void addSauce();
}

class BeefBurrito extends Buritto{
    public BeefBurrito(Ingredient ingredient, Sauce sauce) {
        super(ingredient, sauce);
    }

    @Override
    public void trimIngredients() {
        System.out.println(ingredient + "를 손질합니다.");
    }

    @Override
    public void addTrimedIngredient() {
        System.out.println(ingredient + "를 넣습니다.");
    }

    @Override
    public void addSauce() {
        System.out.println(sauce + "소스를 넣습니다.");
    }
}

class ChickenBurrito extends Buritto{
    public ChickenBurrito(Ingredient ingredient, Sauce sauce) {
        super(ingredient, sauce);
    }

    @Override
    public void trimIngredients() {
        System.out.println(ingredient + "를 손질합니다.");
    }

    @Override
    public void addTrimedIngredient() {
        System.out.println(ingredient + "를 넣습니다.");
    }

    @Override
    public void addSauce() {
        System.out.println(sauce + "소스를 넣습니다.");
    }
}

class CrabBurrito extends Buritto{
    public CrabBurrito(Ingredient ingredient, Sauce sauce) {
        super(ingredient, sauce);
    }

    @Override
    public void trimIngredients() {
        System.out.println(ingredient + "를 손질합니다.");
    }

    @Override
    public void addTrimedIngredient() {
        System.out.println(ingredient + "를 넣습니다.");
    }

    @Override
    public void addSauce() {
        System.out.println(sauce + "소스를 넣습니다.");
    }
}



class mainP{
    public static Object selector(Object[] list){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("---------------");
            for(int i = 0; i < list.length;i++){
                System.out.println(i + ". " + list[i]);
            }
            System.out.println("---------------");
            System.out.print("선택 >> ");
            int selectedNumber = Integer.parseInt(sc.nextLine());
            if(selectedNumber <0 || selectedNumber >= list.length){
                System.out.println("잘못 선택하셨습니다");
            }else{
                return list[selectedNumber];
            }
        }
    }
    public static void stream(){
        Ingredient[] ingredients = Ingredient.values();
        Sauce[] sauces = Sauce.values();
        // Selected Ingredient
        Ingredient selectedIngredient = (Ingredient) selector(ingredients);
        // Selected Sauce
        Sauce selectedSauce = (Sauce) selector(sauces);

        Buritto buritto = null;

        switch (selectedIngredient){
            case beef:
                buritto =  new BeefBurrito(selectedIngredient,selectedSauce);
                break;
            case chicken:
                buritto = new ChickenBurrito(selectedIngredient,selectedSauce);
                break;
            case crab:
                buritto = new CrabBurrito(selectedIngredient,selectedSauce);
                break;
        }
        String name = selectedIngredient + " Burrito";
        buritto.makeBuritto(name);
    }
    public static void main(String[] args){
        stream();
    }
}