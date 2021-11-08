```
팩토리 메서드 패턴(Factory method pattern)은 객체지향 디자인 패턴이다. 
Factory method는 부모(상위) 클래스에 알려지지 않은 구체 클래스를 생성하는 패턴이며. 
자식(하위) 클래스가 어떤 객체를 생성할지를 결정하도록 하는 패턴이기도 하다. 
부모(상위) 클래스 코드에 구체 클래스 이름을 감추기 위한 방법으로도 사용한다.

-- 위키백과
```

- 객체를 생성하는 부분을 캡슐화 하는 것



```
추상 팩토리 패턴(Abstract factory pattern)은 다양한 구성 요소 별로 '객체의 집합'을 생성해야 할 때 유용하다. 
이 패턴을 사용하여 상황에 알맞은 객체를 생성할 수 있다.

-- 위키백과
```





## 팩토리 메소드 패턴 구현



### 피자 주문 메소드

```java
Pizza orderPizza(String type) {
    Pizza pizza;
    
    if (type.equals("cheese")) {
        pizza = new CheesePizza();
    } else if (type.equals("greek")) {
        pizza = new GreekPizza();
    } else if (tpye.equals("pepperoni")) {
        pizza = new PepperoniPizza();
    }
    
    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();
    return pizza;
}
```



- 피자 가게에서 피자 주문을 위한 메소드를 만들었습니다.
- 주문한 피자에 따라 알맞은 객체가 생성되고 주문 과정이 진행됩니다.
- 위와 같이 메소드를 만들었을 때 다음과 같은 문제점이 발생합니다.
  - OCP를 위반합니다. 메뉴 변경을 위해서는  직접 코드를 수정해야합니다.
  - prepare() 메소드 부분부터는 변하지 않겠지만, 메뉴가 변할때 마다 if문의 내용이 변경될 것입니다.
- 결국 if문이 계속 변경될 여지가 있으니 캡슐화 해줍니다.
  - if문 처럼 객체 생성을 담당하는 클래스를 팩토리라고 부릅니다.



### 간단한 피자 팩토리 구현

```java
public class SimplePizzaFactory {
    public Pizza createPizza(String type) {
        Pizza pizza = null;
        
        if (type.equals("cheese")) {
        	pizza = new CheesePizza();
    	} else if (type.equals("greek")) {
        	pizza = new GreekPizza();
   	 	} else if (tpye.equals("pepperoni")) {
        	pizza = new PepperoniPizza();
    	}
    }
}
```

- 이렇게 피자 팩토리를 만들게 되면 단순히 orderPizza 메소드 외에 피자 객체를 생성해야 하는 경우에 피자 팩토리를 활용할 수 있습니다.
- 피자 팩토리를 구현 후 수정된 코드입니다.



```java
public class PizzaStore {
    SimplePizzaFactory factory;
    
    public PizzaStore(SimplePizzaFactory factory) {
        this.factory = factory;
    }
    
    public Pizza orderPizza(String type) {
        Pizza pizza;
        
        pizza = factory.createPizza(type);
        
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
```



- 여기서는 팩토리를 SimplePizzaFactory 하나만을 두었지만, PizzaStore가 다양해질 경우 NYPizzaFacotry, ChicagoPizzaFactory등 다양한 팩토리를 만들 수도 있습니다.
- 여기에 추상 메소드를 사용하면 각각 PizzaStore만의 특색을 가진 피자 객체를 만들 수 있게 됩니다.



### 피자 가게 프레임워크

```java
public abstract class PizzaStore {
    public Pizza orderPizza(String type) {
        Pizza pizza;
        
        pizza = createPizza(type);
        
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
    
    abstract PIZZA createPizza(String type);
}
```

- 팩토리 객체 대신 추상 메소드(createPizza)를 이용해 객체를 삽입 할 것입니다.



```java
public class NYPizzaStore extends PizzaStore {
    Pizza createPizza(String item) {
        if (type.equals("cheese")) {
        	pizza = new NYCheesePizza();
    	} else if (type.equals("greek")) {
        	pizza = new NYGreekPizza();
   	 	} else if (tpye.equals("pepperoni")) {
        	pizza = new NYPepperoniPizza();
    	}
    }
}
```



- 팩토리 객체를 만들었던 예전과 달리 팩토리 '역할'을 하는 메소드를 통해 Pizza 인스턴스 생성을 하게됐습니다.





## 피자 클래스

- 앞에서 만들지 않았던 피자 클래스입니다.

```java
public abstract class Pizza {
    String name;
    String dough;
    String sauce;
    ArrayList toppings = new ArrayList();
    
    void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Tossing dough...");
        System.out.println("Adding sauce...");
        System.out.println("Adding toppings : ");
        for (int i = 0; i < toppings.size(); i++) {
            System.out.println("  " + toppings.get(i));
        }
    }
    
    void bake() {
        System.out.println("Bake for 25 minutes at 350");
    }
    
    void cut() {
        System.out.println("Cutting the Pizza into diagnoal slices");
    }
    
    void box() {
        System.out.println("Place pizza in official PizzaStore box");
    }
    
    public String getName() {
        return name;
    }
}
```





## 구상 서브 클래스

- 뉴옥풍 치즈 피자

```java
public class NYStyleCheesePizza extends Pizza {
    public NYStyleCheesePizza() {
        name = "NY Style Sauce and Cheese Pizza";
        dough = "Thin Crust Dough";
        sauce = "Marinara Sauce";
        
        toppings.add("Grated Reggiano Cheese");
    }
}
```



- 시카고 스타일 피자

```java
public class ChicagoStyleCheesePizza extends Pizza {
    public NYStyleCheesePizza() {
        name = "Chicago Style Deep Dish Cheese Pizza";
        dough = "Extra Thick Crust Dough";
        sauce = "Plum Tomato Sauce";
        
        toppings.add("Shredded Mozzarella Cheese");
    }
    
    void cut() {
        System.out.println("Cutting pizza into square slices");
    }
}
```



## 피자 생성

```java
public class PizzaTestDrive {
    public static void main(String[] args) {
        PizzaStore nyStore = new NYPizzaStore();
        PizzaStore chicagoStroe = new ChicagoPizzaStore();
        
        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println("Ethan ordered a " + pizza.getName());
        
        pizza = chicagoStore.orderPizza("cheese");
        System.out.println("Joel ordered a " + pizza.getName());
    }
}
```





## 정리

- 팩토리 장점
  - 객체 생성 코드를 전부 한 객체 또는 메소드에 넣으면서 중복된 내용을 제거할 수 있음
  - 관리에 용이함
  - 유연성과 확장성이 뛰어난 코드



- 이번 장에서는 두개의 팩토리 패턴을 다뤘음
  - 추상 팩토리
  - 팩토리 메소드