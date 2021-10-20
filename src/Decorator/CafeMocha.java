package Decorator;

public class CafeMocha extends Beverage{

    public CafeMocha() {
        super();
        description = "카페 모카";
    }

    @Override
    public int cost() {
        return 3700;
    }
}
