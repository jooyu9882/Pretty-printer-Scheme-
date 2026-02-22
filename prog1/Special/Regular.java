package Special;

import Tree.*;

public class Regular extends Special {

    private void indent(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");
    }

    // true if the list contains only atoms (not Cons)
    private boolean isSimpleList(Node node) {
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
        if (!(node instanceof Cons)) return;
        Cons cons = (Cons) node;

        if (!p) {
            indent(n);
            System.out.print("(");
        }

        Node first = cons.getCar();
        Node rest = cons.getCdr();

        // Print the first element
        first.print(0, false);

        Node current = rest;
        boolean firstPrinted = true;

        while (current instanceof Cons) {
            Node car = ((Cons) current).getCar();

            if (isSimpleList(car)) {
                // Simple element: print inline
                System.out.print(" ");
                car.print(0, false);
            } else {
                // Nested list: print newline + indentation
                System.out.println();
                car.print(n + 2, false);
            }

            current = ((Cons) current).getCdr();
            firstPrinted = false;
        }

        if (current instanceof Nil) {
            System.out.print(")");
        } else {
            System.out.print(" . ");
            current.print(0, false);
            System.out.print(")");
        }

        // Only print a newline if this is a top-level call
        if (!p) System.out.println();
    }
}
