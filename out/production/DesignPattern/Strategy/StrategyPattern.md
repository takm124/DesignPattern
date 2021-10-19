다양한 결제 방식에 전략 패턴 적용시켜보기



- 전략 패턴(Strategy Pattern)은 디자인 패턴 중 상황에 따라 다른 전략을 사용하는 패턴이다
  - 같은 기능을 하는 메소드가 있는 경우 전략에 따라 다른 결과물이 나오게 한다는 것이다.
  - 예를 들어 커피를 끓인다(brew)라는 메소드가 있다면 전략(객체)을 Americano, Latte 등을 넣어주어 상황에 맞는 결과물을 뽑아내게 된다.



- 나는 쇼핑몰에서 여러가지 결제 방식(현금, 카드, 카카오페이, 네이버페이 등)이 있는 경우 생길 수 있는 문제점과 보완하는 방법을 전략 패턴을 통해 풀어나가 보겠다.





## 상황

- 처음에 쇼핑몰 A에서 결제방식은 단순히 현금과 결제 뿐이었다.
- 개발자는 선택지가 두개밖에 되지 않으니 분기문을 통해 결제 프로세스를 만들었다.



```java
public void payment(String type) {
        if (type.equals("CASH")) {
            // 현금 결제 프로세스
        } else if (type.equals("CARD")) {
            // 카드 결제 프로세스
        }
}

// 사실 여기서도 equals보다는 type Enum을 만드는게 더 좋은 방식이지만 일단 이렇게 진행한다.
```



- 정말 간단하지 않은가! 개발자는 큰 어려움 없이 결제 프로세스를 만들게 되었다.
- 그러나 쇼핑몰 측에서 이번에 카카오페이를 추가시켜야 겠다고 개발자에게 말한다.
- 개발자는 분기문 하나정도 추가되는 것이 크게 어렵지 않으니 카카오 결제 분기를 설정한다.



```java
public void payment(String type) {
        if (type.equals("CASH")) {
            // 현금 결제 프로세스
        } else if (type.equals("CARD")) {
            // 카드 결제 프로세스
        } else if (type.equals("KAKAOPAY")) {
            // 카카오 페이 결제 프로세스
        }
}
```



- 개발자는 뭔가 찜찜하긴 하지만 카카오 결제를 추가시켰다.
- 얼마되지 않아 쇼핑몰측에서 네이버페이와 페이코 결제를 추가시켜달라고 한다.
- 개발자는 이쯤되었을 때 무언가 잘못됨을 느낄 것이다.
  - 앞으로 계속해서 결제 방식이 늘어날 수 있다는 점과 그러면 분기문이 기하급수적으로 늘어날 것이라는 느낌 말이다.
  - 그렇게 될경우 코드의 재사용성도 떨어지게되고 만약 결제 프로세스마다 회원 등급별 결제 방식의 변화 같은 복잡한 절차가 추가될 경우 유지보수도 어려워지게 될 것이다.





## 문제

- 결제 방식이 기하급수로 증가하게 되면 SRP를 위반하게 될 수도 있고, 일단 코드 자체가 지저분해질것이다.
- 결제 방식의 공통 모듈을 수정해줘야 할 경우에 하나하나 수정해줘야 하기 때문에 유지보수 측면에서도 불편함이 생길 것이다.







## 해결

- '결제'와 관련된 처리를 공통적으로 처리할 인터페이스를 만들어준다.

```java
public interface payService {
    public void payment(int amount);
}
```



- 인터페이스를 상속받은 각각의 결제 전략(클래스)을 만들어준다.

```java
public class CardService implements payService{

    @Override
    public void payment(int amount) {
        System.out.println("카드 결제가 완료되었습니다. : " + amount + "원");
    }
}
```

```java
public class CashService implements  payService{

    @Override
    public void payment(int amount) {
        System.out.println("현금 결제가 완료되었습니다. : " + amount + "원");
    }
}
```



![결제 UML](https://user-images.githubusercontent.com/72305146/137828891-464d65f1-fdf1-4a77-b771-c87736eb096f.png)





- 주문을 처리하는 부분에서는 결제 별 메소드를 따로 생성해준다.

```java
public class OrderService {

    public void payByCash() {
        payService pay = new CashService();
    }
    
    public void payByCard() {
        payService pay = new CardService();
    }
}
```



혹은



```java
public class OrderService {

    payService pay;

    public void setPayment(payService payStrategy) {
        pay = payStrategy;
    }
}
```



- 전역변수로 pay 객체를 선언하고 추후에 삽입하는 방법도 있다.

- 그런데 여기까지 오니 또 다른 문제와 궁금증이 생긴다.

- 결국 저런식으로 결제 방식을 나눠놓아도 클라이언트에서 요청이 올 때 알맞은 메소드를 어떻게 호출하는가 하는가이다.

  - 사실 결국 분기문이 만들어지더라도 유지보수성과 클래스들 간의 역할을 확실히 나누게 되었으니 유지보수성은 높였다고 봐도 될것이다.
  - 요청이 올때 결제 타입별로 호출하는 서비스를 나눠주는 메소드를 따로 만드는 방법도 있고
  - 애초에 호출 할 때 URL에 타입을 동적으로 담아 보내 매핑을 다르게 해주는 방법도 있을 것이다.

  



## 정리

- 대부분의 전략 패턴 예제는 Duck이나 Robot 예제라서 실무에서 사용 할 수 있을 수 있는 예제로 구현해보고 싶었다.
- 전략 패턴의 핵심은 런타임시에 전략을 선택할 수 있게 한다는 점이다.
- 나는 구현을 자바로만 했지만 스프링의 의존성 주입과 연계한다면 조금더 구현이 편해지지 않을 까 하는 생각이 들었다.



- 결국 이렇게 역할(책임)별로 나누는 것이 객체 지향적인 설계라고 생각한다.