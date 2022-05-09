class Car {
    void changeEngineOil(){
        System.out.println("Change Engine Oil : Car");
    }
}

class Audi extends Car{
    @Override
    void changeEngineOil() {
        System.out.println("Change Engine Oil : Audi");
    }
}

class Benz extends Car{
    @Override
    void changeEngineOil() {
        System.out.println("Change Engine Oil : Benz");
    }
}


class main_2{
    void changeEngineOil(Car c){
        c.changeEngineOil();
    }
    public static void main(String[] args){
        Car audi = new Audi();
        Car Benz = new Benz();
    }
}
