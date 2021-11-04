```
데코레이터 패턴(Decorator pattern)이란 주어진 상황 및 용도에 따라 어떤 객체에 책임을 덧붙이는 패턴으로, 
기능 확장이 필요할 때 서브클래싱 대신 쓸 수 있는 유연한 대안이 될 수 있다.
```



- 객체지향 원칙 중 OCP를 위반하지 않고 객체를 데코레이터를 이용하여 확장한다.

- 많은 예제가 음료 주문의 예시를 들고 있는데, 다른 적당한 예시가 떠오르지 않아 같은 예시로 구현해 보고자 한다.



## 상황

- 요즘의 카페는 예전과 달리 완제품만을 그대로 팔지 않는다.
- 누군가는 시럽을 추가하고 누군가는 샷을 추가하고 누군가는 휘핑크림을 추가한다.
  - 또한 첨가물의 가격또한 바뀌는 경우가 생긴다면 주문 시스템을 디자인 하는게 매우 어려워진다.
  - 단, 여기서 '빼는' 경우는 생각하지 않는다. 데코레이터는 말 그대로 기본 객체에 기능을 '추가' 하는 것이기 때문이다.



- 개발자는 처음에 Beverage라는 부모 클래스를 생성한다.
  - 그리고 이 Beverage를 상속받는 Americano, Espresso, Latte와 같은 자식 클래스를 만들었다.
  - 그러나 부재료를 추가한 음료를 주문하는 손님이 점차 많아져 AmericanoWithExtraShot과 같은 추가 클래스를 만들기 시작했다.
  - 그러나 샷 하나만 추가하는 것이 아닌 두 개, 세 개를 추가하는 손님들이 생기자 클래스는 기하급수적으로 늘어나기 시작했다.
  - 개발자는 등골이 서늘해짐을 느끼며 유지보수를 시작한다.



- 그럼 그냥 자식클래스에 분기문으로 첨가물에 대한 작업을 해주면 되지 않느냐? 라고 물어본다면
  - 첫 째 샷을 두개 이상 넣을 경우 파라미터 작업을 또 해주어야하고
  - 추후에 첨가물이 또 생긴다면 부모 클래스에 작업을 해줘야한다
    - 이 부분이 OCP를 위반하게 됩니다.



## 문제 해결

- 개발자는 다행이 데코레이터 패턴을 알게되어 해결책을 제시했습니다.

- 전체 UML

![데코레이터 UML](https://user-images.githubusercontent.com/72305146/138048269-89cc2d50-7c96-4c4f-91ea-44ec3913b1bb.png)





- 먼저 Beverage 추상클래스와 상속 받는 메뉴판에 있는 메뉴 클래스들을 만듭니다.

### **Beverage**

```java
public abstract class Beverage {
	
    // 메뉴 설명
    String description;
	
    // 가격
    public abstract int cost();

    public String getDescription() {
        return description;
    }
}
```



### **Americano, CaffeLatte, CaffeMocha **

```java
public class Americano extends Beverage{

    public Americano() {
        super();
        description = "아메리카노";
    }

    @Override
    public int cost() {
        return 2000;
    }
}

public class CafeLatte extends Beverage{

    public CafeLatte() {
        super();
        description = "카페 라떼";
    }

    @Override
    public int cost() {
        return 3500;
    }
}

public class CafeMocha extends Beverage{

    public CafeMocha() {
        super();
        description = "카페 모카";
    }

    @Override
    public int cost() {
        return 3700;
    }
}
```





- 여기에 첨가물을 추가해줄 Decorator를 만들고 각종 첨가물 클래스를 만들어줍니다.

### **subDecorator **

```java
public abstract class subDecorator extends Beverage{
    Beverage beverage;

    public abstract String getDescription();
}
```



### **syrup, ExtraShot **

```java
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
```



- 일단 구현을 다 했으니 결과물 부터 확인해 봅시다.

### **Main **

```java
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
```

```
result

시럽 추가 : 아메리카노, 시럽 추가
시럽 + 샷 추가 아메리카노, 시럽 추가, 샷 추가
```





## 정리

- 사실 우리가 눈여겨 보아야 할 부분은 상속이 계층으로 이루어져 있고, 객체 자체를 넘겨주었을 때 데이터가 어떻게 쌓이느냐 입니다.
- 제일 이해가 안갈 수 있는 부분이 americano를 new Americano()로 생성했으면서 왜 다시 new syrup(americano)를 하는거지? 일것입니다. (제가 그랬습니다.)
  - 일단 우리는 americano 객체를 만들었습니다. 이 시점에서 cost는 2000 입니다.
  - 그리고 새로운 객체를 만들면서 기존 americano 구현체에 다시 삽입을 해줍니다.
    - 여기서 가장 처음, 아무것도 첨가되지 않은 americano 객체가 syrup 생성자 파라미터로 넘어가며 기존에 가지고 있던 정보를 다 넘겨줍니다.
    - 같은 방식으로 Extra shot까지 추가되었습니다.



- Description 같은 경우 처음엔 아메리카노만 존재했다가
  - syrup을 거치면서 시럽추가
  - Extrashot을 거치며 샷 추가가 얹어집니다.



- 이렇게 만들면 또 샷 추가를 하고 싶다면 다시 new ExtraShot(americano)를 해주면 됩니다.



- 처음의 근본적인 문제를 다 해결했지만 단점이 명확히 보입니다.
  - 클래스가 많아지기 시작하면 UML이 없는 상황에선 정말 이해하기 어려운 코드가 될 것입니다.
  - 또한 특정한 조합마다 경우가 생긴다면 수정이 쉽진 않을 것입니다.



