package Special;

import Tree.*;

public class Regular extends Special {

    private void indent(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");
    }

    private boolean simpleList(Node node) {
        if (!(node instanceof Cons)) return false;
        Node curr = node;
        while (curr instanceof Cons) {
            Node car = ((Cons) curr).getCar();
            if (car instanceof Cons) return false; // nested sublist → complex
            curr = ((Cons) curr).getCdr();
        }
        return true;
    }

    @Override
    public void print(Node node, int n, boolean p) {
        if (!(node instanceof Cons)) return;

        Cons c = (Cons) node;

        // If list is simple, print inline
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

        // Complex list: multiline formatting
        indent(n);
        System.out.print("(");

        Node curr = c;
        boolean first = true;
        while (curr instanceof Cons) {
            Node elem = ((Cons) curr).getCar();
            if (!first) System.out.println();
            if (first) {
                // first element stays on same line
                elem.print(0, false);
                first = false;
            } else {
                // indent nested expressions by +2 spaces
                elem.print(n + 2, false);
            }
            curr = ((Cons) curr).getCdr();
        }

        System.out.print(")");
    }
}
