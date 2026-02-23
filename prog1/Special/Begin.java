// Begin -- Parse tree node strategy for printing the special form begin

package Special;

import Tree.Cons;
import Tree.Node;

public class Begin extends Special {
    @Override
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;

        if (!p) {
            indent(n);
            System.out.print("(");
        }

        System.out.print("begin");

        Node rest = c.getCdr();
        while (!rest.isNull()) {
            System.out.println();
            rest.getCar().print(n + 2, false);
            rest = rest.getCdr();
        }

        System.out.println();
        indent(n);
        System.out.print(")");
    }
}