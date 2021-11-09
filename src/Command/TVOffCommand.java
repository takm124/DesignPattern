package Command;

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
