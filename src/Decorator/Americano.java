package Decorator;

public class Americano extends Beverage{

    public Americano() {
        super();
        description = "아메리카노";
    }

    @Override
    public int cost() {
        return 2000;
    }
}
