import java.util.Scanner;

interface printMachine{
    public abstract void print();
}
interface copyMachine{
    public abstract void copy();
}
interface faxmachine{
    public abstract void fax();
}

class allInOne implements  printMachine,copyMachine,faxmachine{
    @Override
    public void print() {
        System.out.println("Now use printing");
    }

    @Override
    public void copy() {
        System.out.println("Now use Copying");
    }

    @Override
    public void fax() {
        System.out.println("Now use faxing");
    }
}
enum Options{
    PRINTER_CLIENT,
    COPY_CLIENT,
    FAX_CLIENT,
    EXIT,
}

class main_t2{
    public static void main(String[] args){
        allInOne a;
        Options[] opt = Options.values();
        boolean loop = true;
        while(loop){
            System.out.println("-----------------");
            for(int i = 1; i <= opt.length;i++){
                System.out.println(new StringBuilder().append(i).append(". ").append(opt[i-1]).toString());
            }
            Scanner sc = new Scanner(System.in);
            System.out.println("-----------------");
            System.out.print(">> ");
            int select = Integer.valueOf(sc.nextLine());
            switch (select){
                case 1:
                    printMachine p = new allInOne();
                    p.print();
                    break;
                case 2:
                    copyMachine c = new allInOne();
                    c.copy();
                    break;
                case 3:
                    faxmachine f = new allInOne();
                    f.fax();
                    break;
                case 4:
                    loop = false;
                default:
                    System.out.println("Wrong input value");
            }
        }
    }
}