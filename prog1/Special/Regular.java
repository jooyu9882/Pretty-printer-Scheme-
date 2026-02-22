package Special;

import Tree.*;

public class Regular extends Special {

    // print n spaces for indentation
    private void indent(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");
    }

    // true if the list contains only atoms (no nested Cons)
    private boolean simpleList(Node node) {
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
        if (!(node instanceof Cons))
            return; // safety

        Cons c = (Cons) node;

        // ---- SIMPLE LIST: print inline ----
        if (simpleList(c)) {
            if (!p) indent(n);
            System.out.print("(");

            Node curr = c;
            boolean first = true;
            while (curr instanceof Cons) {
                if (!first) System.out.print(" ");
                ((Cons) curr).getCar().print(0, false);
                first = false;
                curr = ((Cons) curr).getCdr();
            }

            System.out.print(")");
            return;
        }

        // ---- COMPLEX LIST: multiline ----
        if (!p) indent(n);
        System.out.print("(");

        Node curr = c;
        boolean first = true;
        while (curr instanceof Cons) {
            Node elem = ((Cons) curr).getCar();

            if (first) {
                elem.print(0, false); // first element printed inline
                first = false;
            } else {
                System.out.println();
                elem.print(n + 2, false); // indent nested elements
            }

            curr = ((Cons) curr).getCdr();
        }

        System.out.print(")");
    }
}
