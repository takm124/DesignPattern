package Strategy;

public class CardService implements payService{

    @Override
    public void payment(int amount) {
        System.out.println("카드 결제가 완료되었습니다. : " + amount + "원");
    }
}
