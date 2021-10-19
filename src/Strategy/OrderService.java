package Strategy;

public class OrderService {

    payService pay;

    public void setPayment(payService payStrategy) {
        this.pay = payStrategy;
    }
}
