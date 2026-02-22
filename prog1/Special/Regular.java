package Special;

import Tree.*;

public class Regular extends Special {

    private void indent(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");
    }

    private boolean simpleList(Node node) {
        // true if all elements are atoms (not Cons)
        while (node instanceof Cons) {
            Node car = ((Cons) node).getCar();
            if (car instanceof Cons)
                return false;
            node = ((Cons) node).getCdr();
        }
        return true;
    }

    @Override
    public void print(Node node, int n, boolean p) {

        Cons c = (Cons) node;

        /* ---------- SIMPLE LIST (INLINE) ---------- */

        if (simpleList(c)) {

            indent(n);
            System.out.print("(");

            Node curr = c;
            boolean first = true;

            while (curr instanceof Cons) {

                if (!first)
                    System.out.print(" ");

                ((Cons) curr).getCar().print(0, false);

                first = false;
                curr = ((Cons) curr).getCdr();
            }

            System.out.print(")");
            return;
        }

        /* ---------- COMPLEX LIST (MULTILINE) ---------- */

        indent(n);
        System.out.print("(");

        Node curr = c;
        boolean first = true;

        while (curr instanceof Cons) {

            Node elem = ((Cons) curr).getCar();

            if (first) {
                elem.print(0, false);
                first = false;
            } else {
                System.out.println();
                elem.print(n + 2, false);
            }

            curr = ((Cons) curr).getCdr();
        }

        System.out.print(")");
    }
}
