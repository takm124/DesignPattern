package Observer;

public class Liam implements Student{
    @Override
    public void getMessage(String msg) {
        System.out.println(msg + " 수신자 : Liam");
    }
}
