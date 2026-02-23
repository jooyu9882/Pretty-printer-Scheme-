// Cond -- Parse tree node strategy for printing the special form cond
package Special;

import Tree.Cons;
import Tree.Node;

public class Cond extends Special {
    
    @Override
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;

        if (!p) {
            indent(n);
            System.out.print("(");
        }

        System.out.print("cond");

        Node rest = c.getCdr();
        while (!rest.isNull()) {
            System.out.println();
            indent(n + 2);
            rest.getCar().print(0, false);
            rest = rest.getCdr();
        }

        System.out.println();
        indent(n);
        System.out.print(")");
    }
}