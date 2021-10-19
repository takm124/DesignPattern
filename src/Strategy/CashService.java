package Strategy;

public class CashService implements  payService{

    @Override
    public void payment(int amount) {
        System.out.println("현금 결제가 완료되었습니다. : " + amount + "원");
    }
}
