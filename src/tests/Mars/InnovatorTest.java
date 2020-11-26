package Mars;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


class InnovatorTest {

    @Test
    void getCode() {
        Innovator<Integer> a1 = new Innovator<>(1);

        assertEquals(1,a1.getCode());
    }

    @Test
    void getParent() {
        // a1
        //  |
        // a2
        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2, a1, null);

        // проверяем родителя у a2
        assertEquals(a1, a2.getParent());
        // проверяем, что a1 - корень
        assertNull(a1.getParent());
    }

    @Test
    void getChildren() {
        //      a1
        //    /  |  \
        //  a2  a3   a4
        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2, a1, null);
        Innovator<Integer> a3 = new Innovator<>(3, a1, null);
        Innovator<Integer> a4 = new Innovator<>(3, a1, null);

        // получаем детей у a1
        Collection<Innovator<Integer>> res = a1.getChildren();
        // проверяем детей у a1
        assertEquals(3, res.size());
        assertTrue(res.contains(a2));
        assertTrue(res.contains(a3));
        assertTrue(res.contains(a4));
        // проверяем отсутствие детей у a2
        assertEquals(0, a2.getChildren().size());
        // проверяем что не можем поменять коллекцию
        assertThrows(UnsupportedOperationException.class, ()->{res.add(a1);});
    }

    @Test
    void getDescendants() {
        //                a1
        //              /  |  \
        //            a2  a3   a4
        //           /  \       |
        //          a5   a6     a7

        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2, a1, null);
        Innovator<Integer> a3 = new Innovator<>(3, a1, null);
        Innovator<Integer> a4 = new Innovator<>(4, a1, null);
        Innovator<Integer> a5 = new Innovator<>(5, a2, null);
        Innovator<Integer> a6 = new Innovator<>(6, a2, null);
        Innovator<Integer> a7 = new Innovator<>(7, a4, null);

        // получаем потомков у a1
        Collection<Innovator<Integer>> res = a1.getDescendants();
        // проверяем потомков у a1
        assertEquals(6, res.size());
        assertTrue(res.contains(a2));
        assertTrue(res.contains(a3));
        assertTrue(res.contains(a4));
        assertTrue(res.contains(a5));
        assertTrue(res.contains(a6));
        assertTrue(res.contains(a7));
        // проверяем отсутствие потоков у a3
        assertEquals(0, a3.getDescendants().size());
        // получаем потомков у a2
        Collection<Innovator<Integer>>res2 = a2.getDescendants();
        // проверяем потомков у a2
        assertEquals(2, res2.size());
        assertTrue(res.contains(a5));
        assertTrue(res.contains(a6));
        // проверяем что не можем поменять коллекцию
        assertThrows(UnsupportedOperationException.class, ()->{res.add(a1);});
    }

    @Test
    void hasChildWithValue() {
        //      a1
        //    /  |  \
        //  a2  a3   a4
        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2, a1, null);
        Innovator<Integer> a3 = new Innovator<>(3, a1, null);
        Innovator<Integer> a4 = new Innovator<>(3, a1, null);

        // проверяем имеющегося ребенка
        assertTrue(a1.hasChildWithValue(2));
        // проверяем отсутствующего ребенка
        assertFalse(a1.hasChildWithValue(-1));
        // проверяем самого себя
        assertFalse(a1.hasChildWithValue(1));
    }

    @Test
    void hasDescendantWithValue() {
        //                a1
        //              /  |  \
        //            a2  a3   a4
        //           /  \       |
        //          a5   a6     a7

        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2, a1, null);
        Innovator<Integer> a3 = new Innovator<>(3, a1, null);
        Innovator<Integer> a4 = new Innovator<>(4, a1, null);
        Innovator<Integer> a5 = new Innovator<>(5, a2, null);
        Innovator<Integer> a6 = new Innovator<>(6, a2, null);
        Innovator<Integer> a7 = new Innovator<>(7, a4, null);

        // проверяем имеющегося потомка
        assertTrue(a1.hasDescendantWithValue(3));
        assertTrue(a1.hasDescendantWithValue(6));
        // проверяем отсутствующего потомка
        assertFalse(a1.hasDescendantWithValue(-1));
        // проверяем самого себя
        assertFalse(a1.hasDescendantWithValue(1));
    }

    @Test
    void setNewCode() {
        Innovator<Integer> a1 = new Innovator<>(1);
        a1.setNewCode(2);
        assertEquals(2, a1.getCode());
    }

    @Test
    void setParent() {
        // a1
        //  |
        // a2
        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2);
        // устанавливаем связь
        a2.setParent(a1);
        assertEquals(a1, a2.getParent());
        assertEquals(1, a1.getChildren().size());
        assertTrue(a1.getChildren().contains(a2));
        // разрушаем связь
        a2.setParent(null);
        assertNull(a2.getParent());
        assertEquals(0, a1.getChildren().size());

        //                a1
        //              /  |  \
        //            a2  a3   a4
        //           /  \       |
        //          a5   a6     a7

        // проверяем что не можем нарушить иерархию и зациклить

        a1 = new Innovator<>(1);
        a2 = new Innovator<>(2, a1, null);
        Innovator<Integer> a3 = new Innovator<>(3, a1, null);
        Innovator<Integer> a4 = new Innovator<>(4, a1, null);
        Innovator<Integer> a5 = new Innovator<>(5, a2, null);
        Innovator<Integer> a6 = new Innovator<>(6, a2, null);
        Innovator<Integer> a7 = new Innovator<>(7, a4, null);

        assertFalse(a2.setParent(a6));
        assertFalse(a2.setParent(a2));
        assertTrue(a2.setParent(a3));
        assertEquals(2, a1.getChildren().size());
        assertEquals(a3, a2.getParent());
        assertEquals(1, a3.getChildren().size());
    }

    @Test
    void setDescendants() {
        //                a1
        //              /  |  \
        //            a2  a3   a4
        //           /  \       |
        //          a5   a6     a7

        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2, a1, null);
        Innovator<Integer> a3 = new Innovator<>(3, a1, null);
        Innovator<Integer> a4 = new Innovator<>(4, a1, null);
        Innovator<Integer> a5 = new Innovator<>(5, a2, null);
        Innovator<Integer> a6 = new Innovator<>(6, a2, null);
        Innovator<Integer> a7 = new Innovator<>(7, a4, null);

        Collection<Innovator<Integer>> toSet = new ArrayList<>();
        toSet.add(a2);
        toSet.add(a6);
        assertTrue(a4.setDescendants(toSet));
        //                a1
        //               /  \
        //             a3   a4
        //                 /  \
        //                a2  a6
        //                |
        //                a5
        assertEquals(2, a4.getChildren().size());
        assertEquals(3, a4.getDescendants().size());
        assertEquals(a4, a2.getParent());
        assertEquals(a2, a5.getParent());
        assertEquals(a4, a6.getParent());
        assertEquals(2, a1.getChildren().size());
        assertNull(a7.getParent());

        // мы не можем добавить себя самого
        assertFalse(a2.setDescendants(toSet));
        // мы не можем добавить марсианина, который выше по данной ветке
        assertFalse(a5.setDescendants(toSet));

        // попробуем закинуть двух одинаковых, должен добавиться только один экземпляр
        Collection<Innovator<Integer>> toSet2 = new ArrayList<>();
        toSet2.add(a5);
        toSet2.add(a5);
        assertTrue(a6.setDescendants(toSet2));
        assertEquals(0, a2.getChildren().size());
        assertEquals(1, a6.getChildren().size());
    }

    @Test
    void addChild() {
        //                a1
        //              /  |  \
        //            a2  a3   a4
        //           /  \       |
        //          a5   a6     a7

        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2, a1, null);
        Innovator<Integer> a3 = new Innovator<>(3, a1, null);
        Innovator<Integer> a4 = new Innovator<>(4, a1, null);
        Innovator<Integer> a5 = new Innovator<>(5, a2, null);
        Innovator<Integer> a6 = new Innovator<>(6, a2, null);
        Innovator<Integer> a7 = new Innovator<>(7);

        assertTrue(a4.addChild(a7));
        assertEquals(a4, a7.getParent());
        assertEquals(1, a4.getChildren().size());

        // можем добавить из другой ветки
        assertTrue(a2.addChild(a7));
        assertEquals(a2, a7.getParent());
        assertEquals(0, a4.getChildren().size());
        assertEquals(3, a2.getChildren().size());

        // не можем добавить себя
        assertFalse(a2.addChild(a2));

        // не можем добавять стоящие выше в данной ветке
        assertFalse(a5.addChild(a2));
    }

    @Test
    void removeChild() {
        //                a1
        //              /  |  \
        //            a2  a3   a4
        //           /  \       |
        //          a5   a6     a7

        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2, a1, null);
        Innovator<Integer> a3 = new Innovator<>(3, a1, null);
        Innovator<Integer> a4 = new Innovator<>(4, a1, null);
        Innovator<Integer> a5 = new Innovator<>(5, a2, null);
        Innovator<Integer> a6 = new Innovator<>(6, a2, null);
        Innovator<Integer> a7 = new Innovator<>(7);

        // не можем удалить ничего
        assertFalse(a1.removeChild(null));
        // не можем ребенка не этого марсианина
        assertFalse(a2.removeChild(a1));

        assertTrue(a1.removeChild(a2));
        assertEquals(2, a1.getChildren().size());
        assertNull(a2.getParent());
        assertFalse(a1.getChildren().contains(a2));
    }

    @Test
    void hasAncestor() {
        //                a1
        //              /  |  \
        //            a2  a3   a4
        //           /  \       |
        //          a5   a6     a7

        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2, a1, null);
        Innovator<Integer> a3 = new Innovator<>(3, a1, null);
        Innovator<Integer> a4 = new Innovator<>(4, a1, null);
        Innovator<Integer> a5 = new Innovator<>(5, a2, null);
        Innovator<Integer> a6 = new Innovator<>(6, a2, null);
        Innovator<Integer> a7 = new Innovator<>(7, a4, null);

        assertTrue(a7.hasAncestor(a1));
        assertTrue(a6.hasAncestor(a1));
        assertFalse(a1.hasAncestor(a1));
        assertFalse(a6.hasAncestor(a4));
    }

    @Test
    void findRoot() {
        //                a1
        //              /  |  \
        //            a2  a3   a4
        //           /  \       |
        //          a5   a6     a7

        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2, a1, null);
        Innovator<Integer> a3 = new Innovator<>(3, a1, null);
        Innovator<Integer> a4 = new Innovator<>(4, a1, null);
        Innovator<Integer> a5 = new Innovator<>(5, a2, null);
        Innovator<Integer> a6 = new Innovator<>(6, a2, null);
        Innovator<Integer> a7 = new Innovator<>(7, a4, null);

        // проверяем что находится верный корень
        assertEquals(a1, a1.findRoot());
        assertEquals(a1, a6.findRoot());
        assertEquals(a1, a4.findRoot());
    }
}