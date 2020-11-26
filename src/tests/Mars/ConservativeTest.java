package Mars;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ConservativeTest {
    @Test
    void constructor(){
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

        // создаем семью консерваторов из рандомного новатора (получаем корень)
        Conservative<Integer> b1 = new Conservative<>(a6);

        // проверяем зависимости
        assertNull(b1.getParent());
        assertEquals(3, b1.getChildren().size());
        assertEquals(6, b1.getDescendants().size());
    }

    @Test
    void getCode() {
        Innovator<Integer> a1 = new Innovator<>(1);

        // создаем семью консерваторов из рандомного новатора (получаем корень)
        Conservative<Integer> b1 = new Conservative<>(a1);
        assertEquals(1, b1.getCode());
    }

    @Test
    void getParent() {
        // a1
        //  |
        // a2
        Innovator<Integer> a1 = new Innovator<>(1);
        Innovator<Integer> a2 = new Innovator<>(2, a1, null);

        Conservative<Integer> b1 = new Conservative<>(a1);

        // проверяем родителя у ребенка
        assertEquals(b1, b1.getChildren().iterator().next().getParent());
        // проверяем, что b1 - корень
        assertNull(b1.getParent());
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

        Conservative<Integer> b1 = new Conservative<>(a1);

        // получаем детей у b1
        Collection<Conservative<Integer>> res = b1.getChildren();
        // проверяем детей у b1
        assertEquals(3, res.size());
        // проверяем отсутствие детей у копии консерватора a2
        assertEquals(0, res.iterator().next().getChildren().size());
        // проверяем что не можем поменять коллекцию
        assertThrows(UnsupportedOperationException.class, ()->{res.add(b1);});
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

        Conservative<Integer> b1 = new Conservative<>(a1);

        // получаем потомков у b1
        Collection<Conservative<Integer>> res = b1.getDescendants();
        // проверяем потомков у b1
        assertEquals(6, res.size());
        // проверяем отсутствие потоков у b3
        ArrayList<Conservative<Integer>> res2 = new ArrayList<>(b1.getDescendants());
        Conservative<Integer> b2 = res2.get(0);
        Conservative<Integer> b3 = res2.get(1);
        assertEquals(0, b3.getDescendants().size());
        // получаем потомков у b2
        Collection<Conservative<Integer>>res3 = b2.getDescendants();
        // проверяем потомков у b1
        assertEquals(2, res3.size());
        // проверяем что не можем поменять коллекцию
        assertThrows(UnsupportedOperationException.class, ()->{res.add(b1);});
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

        Conservative<Integer> b1 = new Conservative<>(a1);

        // проверяем имеющегося ребенка
        assertTrue(b1.hasChildWithValue(2));
        // проверяем отсутствующего ребенка
        assertFalse(b1.hasChildWithValue(-1));
        // проверяем самого себя
        assertFalse(b1.hasChildWithValue(1));
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

        Conservative<Integer> b1 = new Conservative<>(a1);

        // проверяем имеющегося потомка
        assertTrue(b1.hasDescendantWithValue(3));
        assertTrue(b1.hasDescendantWithValue(6));
        // проверяем отсутствующего потомка
        assertFalse(b1.hasDescendantWithValue(-1));
        // проверяем самого себя
        assertFalse(b1.hasDescendantWithValue(1));
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

        Conservative<Integer> b1 = new Conservative<>(a1);
        ArrayList<Conservative<Integer>> children = new ArrayList<>(b1.getChildren());

        // проверяем что находится верный корень
        assertEquals(b1, b1.findRoot());
        assertEquals(b1, children.get(0).findRoot());
    }
}