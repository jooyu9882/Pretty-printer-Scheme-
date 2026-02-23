// Regular -- Parse tree node strategy for printing regular lists
package Special;

import Tree.Cons;
import Tree.Node;

public class Regular extends Special {

    @Override
    public void print(Node t, int n, boolean p) {

        if (!p) {
            indent(n);
            System.out.print("(");
        }

        Node current = t;
        boolean first = true;

        while (current.isPair()) {
            Cons cell = (Cons) current;
            Node elem = cell.getCar();

            if (!first) System.out.print(" ");

            elem.print(0, false);
            current = cell.getCdr();
            first = false;
        }

        if (!current.isNull()) {
            System.out.print(" . ");
            current.print(0, false);
        }

        System.out.print(")");
    }
}