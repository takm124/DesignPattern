```
커맨드 패턴(Command pattern)이란 요청을 객체의 형태로 캡슐화하여 
사용자가 보낸 요청을 나중에 이용할 수 있도록 매서드 이름, 매개변수 등 요청에 필요한 정보를 
저장 또는 로깅, 취소할 수 있게 하는 패턴이다.

커맨드 패턴에는 명령(command), 수신자(receiver), 발동자(invoker), 클라이언트(client)의 네개의 용어가 항상 따른다. 
커맨드 객체는 수신자 객체를 가지고 있으며, 수신자의 메서드를 호출하고, 
이에 수신자는 자신에게 정의된 메서드를 수행한다. 
커맨드 객체는 별도로 발동자 객체에 전달되어 명령을 발동하게 한다. 
발동자 객체는 필요에 따라 명령 발동에 대한 기록을 남길 수 있다. 
한 발동자 객체에 다수의 커맨드 객체가 전달될 수 있다. 
클라이언트 객체는 발동자 객체와 하나 이상의 커맨드 객체를 보유한다.
클라이언트 객체는 어느 시점에서 어떤 명령을 수행할지를 결정한다.
명령을 수행하려면, 클라이언트 객체는 발동자 객체로 커맨드 객체를 전달한다.

-- 위키백과
```



- 커맨드 패턴의 핵심은 '요청'을 캡슐화 한다는 것이다.
  - 이 말이 가장 이해하기 어려웠지만 쉽게 이야기 하면 다음과 같다.
  - '캡슐화'는 연관있는 변수와 함수를 하나로 묶는 작업입니다.
    - 즉, 관련있는 것 끼리만 묶어놓는 다는 의미입니다.
  - 개발자는 '요청'을 구분하고 '처리'를 해야합니다.
  - 그러나 '요청'이 너무 많아지면 '구분'하는 작업이 길어지고 복잡해질 것입니다.
  - 그래서 개발자는 '처리' 작업을 기준으로 '요청'을 캡슐화 시킬 것입니다.
    - 처리 시에 필요한 요청 작업을 위한 클래스를 불러온다는 의미입니다.
    - 요청 -> 구분 -> 처리가 아닌 요청 -> 처리로 작동하게끔 만들 것입니다.



## 상황

- 버튼이 단 하나 있는 리모컨이 있다.
  - 이 리모컨의 버튼을 한번 클릭하면 티비가 켜지고 다시한번 클릭하면 티비가 꺼진다.
  - 여기서 버튼은 본인이 무엇을 끄고 키는지 기억할 필요 없다.
  - '버튼'의 역할은 단순히 버튼이 눌러졌을 때 기능을 execute 해주기만 하면 된다.



- 먼저 전체 구조를 보자

![UML](https://user-images.githubusercontent.com/72305146/140867419-fc457ea8-8670-47db-99c5-141a15000030.png)

- RemoteController : Invoker(호출자)
- Command : Command(커맨드)
- TVOnCommand / TVOffCommand : ConcreteCommand (구체적 커맨드)
- TV : Receiver(수신자)



- 전체 구성
  - Invoker가 Command에 요청을 하면 Receiver가 행동을 한다.



- 결국 Receiver가 행동을 해야하니 행동 객체 부터 만들어 준다.



### TV

```java
public class TV {
    public void on() {
        System.out.println("TV를 켰습니다.");
    }

    public void off() {
        System.out.println("TV를 껐습니다.");
    }
}
```



### Command / ConcreteCommand

```java
public interface Command {
    void execute();
}
```



```java
public class TVOnCommand implements Command{ // 수신자

    private final TV tv;

    public TVOnCommand(final TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.on();
    }
}
```



```java
public class TVOffCommand implements Command{ // 수신자

    private final TV tv;

    public TVOffCommand(final TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.off();
    }
}
```



### Invoker

```java
public class RemoteController {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }
}
```





## 실행

```java
public class Main {
    public static void main(String[] args) {
        RemoteController rc = new RemoteController();

        TVOnCommand tvOnCommand = new TVOnCommand(new TV());
        rc.setCommand(tvOnCommand);
        rc.pressButton();

        TVOffCommand tvOffCommand = new TVOffCommand(new TV());
        rc.setCommand(tvOffCommand);
        rc.pressButton();
    }
}
```



- 이렇게 하면 Invoker (RemoteController)는 요청마다 pressButton만 해주면 되고

  기능을 추가하려면 새로운 Command를 만들어 세팅해주면 된다.





## 정리

- 커맨드 패턴의 두드러지는 포인트는 '요청' 부분과 '실행' 부분이 나뉜다는 것이다(캡슐화)
  - 객체 간 결합도를 낮출 수 있음
  - 수정에 용이해짐



- 그러나 TV를 켜기 위해 우리는 3개의 클래스를 생성했다.
  - 앞으로 기능이 무수히 많이 추가된다면 클래스의 수 또한 너무 많아질 것이다.