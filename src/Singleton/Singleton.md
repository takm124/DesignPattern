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

- 가장 쉽게 thread-safe한 싱글톤을 만드는 방법이다.
- synchronized 키워드를 사용하면 자바에서 동시성 보장을 해줌



```java
public class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;

    private ThreadSafeSingleton(){}

    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }

        return instance;
    }
}
```

- 간단하게 동시성을 보장하지만 synchronized 키워드를 사용하면 프로그램 내부적으로 성능저하를 일으킬 수 있다.
  - '동시성'을 보장하기 위한 방법들을 생각해보고, 그런 작업들을 대신 해주고 있다고 생각하면 충분히 납득 가능하다.

- 스레드가 많지 않을 경우에는 사용해도 괜찮다.
- 오버헤드를 피하기 위해 double checked locking 방식을 사용하기도 한다.



```java
// DoubleChecked Locking
    public static ThreadSafeSingleton getInstance2() {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
```



### Using Reflection to destroy Singleton Pattern

- java의 Reflection을 통해 위에서 만든 싱글톤 패턴을 다 파훼시킬 수 있다.
  - Reflection은 클래스, 인터페이스, 메소드를 찾거나 객체 생성, 변수 변경, 메소드 호출을 지원함
  - 스프링에서 bean factory가 reflection을 사용해 생성자를 만듦

```java
public class ReflectionSingletonTest {
    public static void main(String[] args) {
        EagerInitializedSingleton instanceOne = EagerInitializedSingleton.getInstance();
        EagerInitializedSingleton instanceTwo = null;

        try {
            Constructor[] constructors = EagerInitializedSingleton.class.getDeclaredConstructors();

            for (Constructor constructor : constructors) {
                // 여기서 싱글톤 패턴을 파괴함
                constructor.setAccessible(true);
                instanceTwo = (EagerInitializedSingleton) constructor.newInstance();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(instanceOne.hashCode());
        System.out.println(instanceTwo.hashCode());
    }
}
```

```
result

instanceOne : 460141958
instanceTwo : 1163157884
```



### Enum Singleton

- Reflection으로 싱글톤 패턴이 망가지는 것을 막기 위한 방법
- 열거형으로 만들면 싱글톤을 보장할 수 있지만 열거형 자체가 유연하지 못한 방식이기 때문에 지연 실행이 어렵다.

```java
public enum EnumSingleton {

    INSTANCE;
    
    public static void doSomething(){
        //do something
    }
}
```



## 정리

- 싱글톤은 안티패턴이라 불린다.
  - 싱글톤의 코드가 복잡해질수록, 다른코드들의 의존성이 함께 커져 결합도가 커진다.
  - 또한 싱글톤이라는 것 자체가 하나의 인스턴스로 작업이 진행된다는 의미이므로 수정이 잦을 수 있는데 이는 객체지향 원칙 중 OCP를 위반 할 수 있다는 것이다.
  - 전역 상태로 만드는 것은 객체 지향적이지 않다.



- 그럼에도 싱글톤은 쓰인다. 위의 문제점들을 커버할 수 있을 만큼의 적당한 규모라면 큰 문제가 없겠지만 언제든지 위 문제들이 발생 할 수 있다는 것을 인지해야 한다.

ref : https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples
