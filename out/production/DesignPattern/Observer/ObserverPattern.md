```
옵서버 패턴(observer pattern)은 객체의 상태 변화를 관찰하는 관찰자들, 
즉 옵저버들의 목록을 객체에 등록하여 상태 변화가 있을 때마다 메서드 등을 통해 
객체가 직접 목록의 각 옵저버에게 통지하도록 하는 디자인 패턴이다. 
주로 분산 이벤트 핸들링 시스템을 구현하는 데 사용된다. 
발행/구독 모델로 알려져 있기도 하다.

-- 위키백과
```



- 옵저버 패턴은 1:N 관계에서 1이 변화 할 때 그 변화를 N이 알 수 있게 해주는 패턴이다.
- 예를들어 선생님이 반장(1)에게 지시사항을 내리면, 반장은 학생(N)들에게 그 내용을 전달하는 것과 같다.
- 옵저버 패턴은 객체간의 느슨한 결합(Loose Coupling)을 유지한다.
  - 객체간의 의존성을 낮춰줄 수 있음



![옵저버 패턴](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fdpoa8U%2FbtqZjvUHSeB%2Ff7deiNNAvQGkeu8CGa4Twk%2Fimg.jpg)

- Publisher는 변동사항이 생기면 Observer에 변동사항을 전달해주는 역할을 해줍니다.



### 가장 간단한 형태의 옵저버

- 위에서 언급한 선생님(개발자)이 학생들에게 공지사항을 전달하기 위해 반장(Publisher)에게 지시사항을 내리면 학생들(Observer)이 전달받는 형태로 만들어 보겠다.

- 전체 구조(UML)

![옵저버 패턴 UML](https://user-images.githubusercontent.com/72305146/137848767-e15c31f5-6ae9-4c71-ba94-934fb5ea2774.png)



- 가장 먼저 반장과 학생들의 interface를 만들어 준다.

```java
public interface ClassLeader {
    void addStudent(Student student);
    void removeStudent(Student student);
    void notifyStudents(String msg);
}
```

```java
public interface Student {
    void getMessage(String msg);
}
```



- 반장은 학생(Observer)를 관리할 두 메소드 (add, remove), 그리고 공지를 알릴 메소드(notify)가 필요하다.
- 학생들은 메시지를 성공적으로 받았음을 확인하는 메소드를 만들어 주었다.



- 이제 실제 반장과 학생 객체들을 만들어보았다.

### **반장(Eric)**

```java
import java.util.ArrayList;
import java.util.List;

public class Eric implements  ClassLeader{
    private List<Student> students = new ArrayList<>();

    public void notifyNextClass(String nextClass) {
        System.out.println("반장 : 다음 수업은 " + nextClass + " 입니다.");
        notifyStudents("다음 수업 : " + nextClass + " 확인했습니다. ||");
    }

    @Override
    public void addStudent(Student student) {
        students.add(student);
    }

    @Override
    public void removeStudent(Student student) {
        students.remove(student);
    }

    @Override
    public void notifyStudents(String msg) {
        for (Student stu : students) {
            stu.getMessage(msg);
        }
    }
}
```



- Eric을 통해 메시지를 전달하면 본인이 관리하는 학생들에게 메시지를 전달 해 줄 것이다.





### **학생(Jackson, Liam, Noah)**

```java
public class Jackson implements Student{
    @Override
    public void getMessage(String msg) {
        System.out.println(msg + " 수신자 : Jackson");
    }
}

public class Liam implements Student{
    @Override
    public void getMessage(String msg) {
        System.out.println(msg + " 수신자 : Liam");
    }
}

public class Noah implements Student{
    @Override
    public void getMessage(String msg) {
        System.out.println(msg + " 수신자 : Noah");
    }
}
```



### 실행

```java
public class Main {
    public static void main(String[] args) {
        Eric eric = new Eric();

        eric.addStudent(new Jackson());
        eric.addStudent(new Liam());
        eric.addStudent(new Noah());

        eric.notifyNextClass("국어");
    }
}
```



### 결과

```
다음 수업은 국어 입니다.
다음 수업 : 국어 || 수신자 : Jackson
다음 수업 : 국어 || 수신자 : Liam
다음 수업 : 국어 || 수신자 : Noah
```





## 정리

- 헤드퍼스트 디자인패턴 책에서는 이 부분을 WeatherData를 이용해서 구현했다.
  - 실제 기상정보가 바뀔 때 마다 요소들이 업데이트되어 새로운 정보를 뿌려줘야한다.



- 내가 만든 것은 비교적 간단한 구조이지만 옵저버 패턴 구현의 특징을 보면 특정 데이터에 의존하는 객체들이 많을 경우, 그 데이터를 컨트롤 해주는 객체(publisher)를 만드는 구조이다.
  - 이렇게 하지 않으면 객체마다 데이터를 처리하는 프로세스를 따로 구현해줘야 한다.



- 그러나 단점도 존재합니다.
  - 옵저버 객체를 너무 많이 생성하면 메모리 누수가 발생할 수 있습니다.
  - 비동기 방식이라 순서가 보장되지 않습니다.
  - Thread-safe하지 않습니다.
