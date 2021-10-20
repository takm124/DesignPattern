package Decorator;

public class ExtraShot extends subDecorator{
    Beverage beverage;

    public ExtraShot(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public int cost() {
        return beverage.cost() + 500;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 샷 추가";
    }
}
