```
싱글턴 패턴(Singleton pattern)을 따르는 클래스는, 생성자가 여러 차례 호출되더라도 
실제로 생성되는 객체는 하나이고 최초 생성 이후에 호출된 생성자는 
최초의 생성자가 생성한 객체를 리턴한다. 

이와 같은 디자인 유형을 싱글턴 패턴이라고 한다. 
주로 공통된 객체를 여러개 생성해서 사용하는 DBCP(DataBase Connection Pool)와 같은 상황에서 많이 사용된다.

-위키백과
```

- 인스턴스가 오직 1개만 생성되야 하는 경우에 사용
  - loggin, drivers objects, caching, thread pool
  - Abstract Factory, Builder, Prototype, Facade 패턴에도 쓰인다.
- 장점
  - 새로운 객체를 만들지 않으니 메모리 낭비가 적어짐
  - 싱글 인스턴스로 관리하기 때문에 데이터 공유가 쉬워진다.
- 단점
  - 싱글톤을 구현하기 위한 코드가 많이 필요하다
  - 꼭 1개만 생성된다고 보장되지 않는다.
  - 싱글톤 인스턴스의 볼륨이 커지기 시작하면 결합도가 높아져 수정이 어렵고 테스트가 힘들어진다.





## 예제 코드

- 다른 패턴들과 달리 복잡한 형태가 아니기 때문에 싱글톤을 생성할 수 있는 여러가지 상황에 대해 알아보자





### Eager Initialization

- 클래스 로딩시에 바로 인스턴스가 생성된다.
- 가장 쉬운형태의 싱글톤이지만, 사용하지 않는 경우에도 생성되기 때문에 메모리 낭비가 발생할 수 있다.

```java
public class EagerInitializedSingleton {

    private static final EagerInitializedSingleton Instance = new EagerInitializedSingleton();

    // private 선언하여 외부에서 인스턴스를 만드는 것을 방지한다.
    private EagerInitializedSingleton(){}

    public static EagerInitializedSingleton getInstance() {
        return Instance;
    }
}
```

- 만약 자원을 많이 소모하지 않는 형태의 싱글톤이면 사용해도 상관없다.
- 그러나 대부분의 상황에서 싱글톤은 자원을 꽤나 사용하기 때문에 getInstance를 하기 전까진 인스턴스가 생성되지 않는 것이 낫다.



### Lazy Initialization

- 지연 생성은 싱글톤이 사용 될 때 인스턴스가 생성되는 방식이다.

```java
public class LazyInitializedSingleton {

    private static LazyInitializedSingleton instance;

    private LazyInitializedSingleton(){}

    public static LazyInitializedSingleton getInstance() {

        if (instance == null) {
            instance = new LazyInitializedSingleton();
        }
        
        return instance;
    }
}
```

- 싱글 스레드 환경에서는 문제없이 작동한다.
- 그러나 멀티스레드 환경에서 동시성 보장이 안되기 때문에 문제가 생길 수 있다.



### Thread Safe Singleton





ref : https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples
