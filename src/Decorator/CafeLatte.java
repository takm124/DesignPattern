package Decorator;

public class CafeLatte extends Beverage{

    public CafeLatte() {
        super();
        description = "카페 라떼";
    }

    @Override
    public int cost() {
        return 3500;
    }
}
