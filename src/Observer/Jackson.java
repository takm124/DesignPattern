package Observer;

public class Jackson implements Student{
    @Override
    public void getMessage(String msg) {
        System.out.println(msg + " 수신자 : Jackson");
    }
}
