package Command;

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
