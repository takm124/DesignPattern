package Observer;

public class Main {
    public static void main(String[] args) {
        Eric eric = new Eric();

        eric.addStudent(new Jackson());
        eric.addStudent(new Liam());
        eric.addStudent(new Noah());

        eric.notifyNextClass("국어");
    }
}
