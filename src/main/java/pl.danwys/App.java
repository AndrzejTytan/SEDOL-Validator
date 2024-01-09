package pl.danwys;

public class App {
    public static void main(String[] args) {
        String sedol = "0989529";
        System.out.println(SedolValidator.validate(sedol));
    }
}
