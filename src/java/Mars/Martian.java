package Mars;

import java.util.Collection;

public abstract class Martian<T> {
    /**
     * Возвращает родителя
     *
     * @return объект марсианина
     */
    public abstract Martian<T> getParent();

    /**
     * Возвращает всех детей
     *
     * @return колекция марсиан
     */
    public abstract Collection<? extends Martian<T>> getChildren();

    /**
     * Возвращает всех потомков
     *
     * @return коллекция марсиан
     */
    public abstract Collection<? extends Martian<T>> getDescendants();

    /**
     * Ищет ребёнка с данным генетическим кодом
     *
     * @param value код
     * @return true если ребёнок найден, иначе false
     */
    public abstract boolean hasChildWithValue(T value);

    /**
     * Ищет потомка с данным генетическим кодом
     *
     * @param value код
     * @return true если потомок найден, иначе false
     */
    public abstract boolean hasDescendantWithValue(T value);

    /**
     * Находит корень дерева с данным марсианином.
     *
     * @return объект марсианина.
     */
    public abstract Martian<T> findRoot();

    @Override
    public abstract String toString();
}

