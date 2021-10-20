package Decorator;

public class Main {
    public static void main(String[] args) {
        Beverage americano = new Americano();

        // 시럽 추가
        americano = new syrup(americano);
        System.out.println("시럽 추가 : " + americano.getDescription());

        // 샷 추가
        americano = new ExtraShot(americano);
        System.out.println("시럽 + 샷 추가 : " + americano.getDescription());
    }
}
