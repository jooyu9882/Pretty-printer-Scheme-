package Special;

import Tree.*;

public class Regular extends Special {

    private void indent(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");
    }

    private boolean simpleList(Node node) {
        // true if the first element is an Ident and all following elements are Idents or literals
        if (!(node instanceof Cons)) return false;

        Node curr = node;
        while (curr instanceof Cons) {
            Node car = ((Cons) curr).getCar();
            if (car instanceof Cons) return false; // nested list → complex
            curr = ((Cons) curr).getCdr();
        }
        return true;
    }

    @Override
    public void print(Node node, int n, boolean p) {
        Cons c = (Cons) node;

        if (c == null) return;

        // SIMPLE LIST: inline
        if (simpleList(c)) {
            indent(n);
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

        // COMPLEX LIST: multiline
        indent(n);
        System.out.print("(");
        Node curr = c;
        boolean first = true;
        while (curr instanceof Cons) {
            Node elem = ((Cons) curr).getCar();

            if (first) {
                elem.print(0, false); // first element stays on same line
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
