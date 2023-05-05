@FunctionalInterface
public interface BiUser<T, U> {
    void accept(T t, U u);
}