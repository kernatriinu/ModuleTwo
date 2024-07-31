package island.util;

public class SingletonNotifier {
    private static final Notifier instance = new ConsoleNotifier();

    public static Notifier getNotifier() {
        return instance;
    }
}
