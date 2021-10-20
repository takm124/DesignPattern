package Decorator;

public class syrup extends subDecorator{
    Beverage beverage;

    public syrup(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public int cost() {
        return beverage.cost() + 300;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 시럽 추가";
    }
}
