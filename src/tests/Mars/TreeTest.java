package Mars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Test
    void getReport() {
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

        String res = a1.toString() + "\n" +
                "    " + a2.toString() + "\n" +
                "        " + a5.toString() + "\n" +
                "        " + a6.toString() + "\n" +
                "    " + a3.toString() + "\n" +
                "    " + a4.toString() + "\n" +
                "        " + a7.toString() + "\n";
        System.out.println(res);
        assertEquals(res, Tree.getReport(a3));
    }

    @Test
    void getTreeFromReport() {
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

        String report = Tree.getReport(a1);
        String report2 = Tree.getReport(b1);

        try {
            Conservative<Integer> root = (Conservative<Integer>) Tree.<Integer>getTreeFromReport(report2);
            System.out.println(Tree.getReport(root));
            assertEquals(report2, Tree.getReport(root));
            assertEquals(1, root.getCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}