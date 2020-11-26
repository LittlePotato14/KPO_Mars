package Mars;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Innovator<T> extends Martian<T> {

    // генетический код
    private T code;
    // родитель
    private Innovator<T> parent;
    // дети
    private Collection<Innovator<T>> children;

    public T getCode(){
        return code;
    }

    public Innovator(){
        this(null, null,null);
    }

    /**
     * Создает новатора с кодом и пустой коллекцией детей
     *
     * @param code код
     */
    public Innovator(T code) {
        this(code, null, null);
    }

    /**
     * Создает новатора с кодом, родителем и коллекцией детей
     *
     * @param code     код
     * @param children колекция детей
     */
    public Innovator(T code, Innovator<T> parent, Collection<Innovator<T>> children) {
        this.code = code;
        this.setParent(parent);
        this.children = new ArrayList<>();

        if (children != null)
            this.setDescendants(children);
    }

    /**
     * Возвращает родителя
     *
     * @return объект новатора
     */
    @Override
    public Innovator<T> getParent() {
        return parent;
    }

    /**
     * Возвращает всех детей
     *
     * @return колекция новаторов
     */
    @Override
    public Collection<Innovator<T>> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    /**
     * Возвращает всех потомков.
     *
     * @return коллекция новаторов.
     */
    @Override
    public Collection<Innovator<T>> getDescendants() {
        Collection<Innovator<T>> descendants = new ArrayList<>();

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
        for (var martian : children)
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
     * Устанавливает новое значение генетического кода
     *
     * @param code новое значение
     */
    public void setNewCode(T code) {
        this.code = code;
    }

    /**
     * Устанавливает нового родителя
     *
     * @param parent родитель
     * @return true если удалось установить, иначе false (если устанавливаем самого к себе или устанавливаем потомка)
     */
    public boolean setParent(Innovator<T> parent) {
        // если устанавливаю null в родителя
        if (parent == null) {
            // если была связь с другим родителем, мы её разрушаем
            if (this.parent != null) {
                this.parent.removeChild(this);
                this.parent = null;
            }
            // null - тоже успех в данном случае
            return true;
        }
        // равносильно реализованому добавлению ребёнка this переданному родителю
        return parent.addChild(this);
    }

    /**
     * Заменяет старых потомков на новых
     *
     * @param newChildren потомки (все переданные установятся как дети)
     * @return true если удалось установить всех, иначе false (в таком случае никто не установится)
     */
    public boolean setDescendants(Collection<Innovator<T>> newChildren) {
        if (newChildren == null)
            return false;

        // проверяем что всех этих потомков мы можем добавить, чтобы пользователь не гадал, что смогло добавиться, а что - нет
        for (var child : newChildren)
            if ((child == this) || hasAncestor(child))
                return false;

        // удаляем старых детей
        for(var child : children)
            child.parent = null;
        children = new ArrayList<>();

        // добавляем потомков
        for (var child : newChildren)
            addChild(child);

        return true;
    }

    /**
     * добавляет нового ребенка марсианину
     *
     * @param child ребенок
     * @return true если удалось добавить, иначе false (если добавляем самого к себе или добавляем предка)
     */
    public boolean addChild(Innovator<T> child) {
        if (!(child == null) && !(child == this) && !hasAncestor(child)) {
            // разрушаем связь с прошлым родителем
            if (child.parent != null)
                child.parent.removeChild(child);
            // добавляем связь с новым родителем
            child.parent = this;
            children.add(child);
            return true;
        }

        return false;
    }

    /**
     * Разрывает связь ребёнок-родитель
     *
     * @param child ребёнок
     * @return true если получилось удалить, иначе false (ребёнок не был у данного родителя)
     */
    public boolean removeChild(Innovator<T> child) {
        if (child == null)
            return false;

        if (child.parent == this) {
            child.parent = null;
            children.remove(child);
            return true;
        }

        return false;
    }

    /**
     * Проверяет, есть ли данный предок у марсианина
     *
     * @param ancestor предок
     * @return true если предок найден, иначе false
     */
    public boolean hasAncestor(Innovator<T> ancestor) {
        Innovator<T> current = this;
        while (current.parent != null && current.parent != ancestor)
            current = current.parent;

        return current.parent != null;
    }

    /**
     * Находит корень дерева с данным марсианином.
     *
     * @return объект новатора.
     */
    @Override
    public Innovator<T> findRoot() {
        Innovator<T> current = this;
        while (current.parent != null)
            current = current.parent;

        return current;
    }

    @Override
    public String toString() {
        return "InnovatorMartian(" +
                code.getClass().getSimpleName() + ":" + code +
                ")";
    }
}
