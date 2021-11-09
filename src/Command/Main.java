package Command;

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
