// Regular -- Parse tree node stratagy for printing regular lists

package Special;

import Tree.Cons;
import Tree.Node;

public class Regular extends Special {

    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;

        // If caller hasn't already printed '(' then do it here.
        if (!p) {
            indent(n);
            System.out.print("(");
        }

        // Print first element
        Node first = c.getCar();
        first.print(n);

        // Print rest of list
        Node rest = c.getCdr();
        while (!rest.isNull()) {
            if (rest.isPair()) {
                System.out.print(" ");
                Node elem = rest.getCar();
                elem.print(n);
                rest = rest.getCdr();
            } else {
                // dotted pair
                System.out.print(" . ");
                rest.print(n);
                break;
            }
        }

        System.out.print(")");
    }
}