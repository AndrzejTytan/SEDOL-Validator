package pl.danwys;

public class App {
    public static void main(String[] args) {
        for (String sedol : args) {
            System.out.println(SedolValidator.validate(sedol));
        }
    }
}
