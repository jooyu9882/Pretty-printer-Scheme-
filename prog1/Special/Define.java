// Define -- Parse tree node strategy for printing the special form define

package Special;

import Tree.Cons;
import Tree.Node;

public class Define extends Special {
    @Override
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;

        if (!p) {
            indent(n);
            System.out.print("(");
        }

        System.out.print("define ");

        Node first = c.getCdr().getCar();     
        first.print(0, false);

        Node rest = c.getCdr().getCdr();      // body
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