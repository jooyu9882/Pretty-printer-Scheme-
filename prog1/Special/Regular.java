// Regular -- Parse tree node stratagy for printing regular lists

package Special;

import Tree.Cons;
import Tree.Node;

public class Regular extends Special {

    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;

        if (!p) {
            indent(n);
            System.out.print("(");
        }

        Node current = t;

        boolean firstElem = true;

        while (current.isPair()) {
            Cons cell = (Cons) current;

            if (!firstElem) {
                System.out.print(" ");
            }

            cell.getCar().print(n, true);

            current = cell.getCdr();
            firstElem = false;
        }

        if (!current.isNull()) {
            System.out.print(" . ");
            current.print(n, true);
        }

        System.out.print(")");
    }
}