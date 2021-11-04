package Singleton;

public class EagerInitializedSingleton {

    private static final EagerInitializedSingleton Instance = new EagerInitializedSingleton();

    // private 선언하여 외부에서 인스턴스를 만드는 것을 방지한다.
    private EagerInitializedSingleton(){}

    public static EagerInitializedSingleton getInstance() {
        return Instance;
    }
}
