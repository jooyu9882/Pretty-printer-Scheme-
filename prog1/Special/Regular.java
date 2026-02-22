package Special;

import Tree.*;

public class Regular extends Special {

    // print n spaces for indentation
    private void indent(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");
    }

    // check if a list is simple (all elements are atoms or Nil, no nested Cons)
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
    public void print(Node node, int n, boolean parenthesized) {
        if (!(node instanceof Cons)) return;
        Cons cons = (Cons) node;

        // If the list is simple, print entirely inline
        if (simpleList(cons)) {
            indent(n);
            System.out.print("(");
            Node curr = cons;
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

        // Complex list (multiline)
        indent(n);
        System.out.print("(");
        Node curr = cons;
        boolean first = true;

        while (curr instanceof Cons) {
            Node car = ((Cons) curr).getCar();

            if (first) {
                car.print(0, false); // first element printed inline
                first = false;
            } else {
                System.out.println();
                car.print(n + 2, false); // subsequent elements indented
            }

            curr = ((Cons) curr).getCdr();
        }

        System.out.print(")");
    }
}
