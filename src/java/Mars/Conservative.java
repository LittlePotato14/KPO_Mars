package Mars;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Conservative<T> extends Martian<T> {

    // генетический код
    private final T code;
    // родитель
    private final Conservative<T> parent;
    // дети
    private final Collection<Conservative<T>> children;

    public T getCode(){
        return code;
    }

    /**
     * Конструктор семейства консерваторов из рандомного новатора, создаст корень семейства
     *
     * @param randomMartianSource рандомный новатор
     */
    public Conservative(Innovator<T> randomMartianSource) throws NullPointerException{
        // находим корень дерева и делаем из него консерватора
        // кинет NullPointerException, если переданный марсианин null
        this(randomMartianSource.findRoot(), null);
    }

    /**
     * Конструктор консерватора от данного новатора и консерватора-родителя
     *
     * @param martianSource новатор
     * @param parent        родитель консерватор
     */
    private Conservative(Innovator<T> martianSource, Conservative<T> parent) {
        this.code = martianSource.getCode();
        this.parent = parent;
        this.children = new ArrayList<>();
        // делаем консерваторов из потомков
        for (var child : martianSource.getChildren())
            children.add(new Conservative<>(child, this));
    }

    /**
     * Возвращает родителя
     *
     * @return объект консерватора
     */
    @Override
    public Conservative<T> getParent() {
        return parent;
    }

    /**
     * Возвращает всех детей
     *
     * @return колекция консерваторов
     */
    @Override
    public Collection<Conservative<T>> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    /**
     * Возвращает всех потомков.
     *
     * @return коллекция консерваторов.
     */
    @Override
    public Collection<Conservative<T>> getDescendants() {
        Collection<Conservative<T>> descendants = new ArrayList<>() {
        };

        for (var martian : getChildren()) {
            descendants.add(martian);
            descendants.addAll(martian.getDescendants());
        }

        return Collections.unmodifiableCollection(descendants);
    }

    /**
     * Есть ли ребёнок с таким генетическим кодом
     *
     * @param value код
     * @return true если ребёнок найден, иначе false
     */
    @Override
    public boolean hasChildWithValue(T value) {
        for (var martian : getChildren())
            if (martian.code == value)
                return true;

        return false;
    }

    /**
     * Есть ли потомок с таким генетическим кодом
     *
     * @param value код
     * @return true если потомок найден, иначе false
     */
    @Override
    public boolean hasDescendantWithValue(T value) {
        for (var martian : getDescendants())
            if (martian.code == value)
                return true;

        return false;
    }

    /**
     * Находит корень дерева с данным марсианином.
     *
     * @return объект новатора.
     */
    @Override
    public Conservative<T> findRoot() {
        Conservative<T> current = this;
        while (current.parent != null)
            current = current.parent;

        return current;
    }

    @Override
    public String toString() {
        return "ConservativeMartian(" +
                code.getClass().getSimpleName() + ":" + code +
                ")";
    }
}
