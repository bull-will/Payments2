package sample;

/* A Main class to be packed into executable jar must not extends another classes
 * so I invoke the SubMain extending Application from Main */
public class Main {
    public static void main(String[] args) {
//        System.out.println("Running SubMain from Main");
        SubMain.main(args);
    }
}

