// If -- Parse tree node strategy for printing the special form if
package Special;

import Tree.Cons;
import Tree.Node;

public class If extends Special {
    
    @Override
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;

        if (!p) {
            indent(n);
            System.out.print("(");
        }

        System.out.print("if ");

        Node test = c.getCdr().getCar();
        test.print(0, false);

        Node rest = c.getCdr().getCdr();
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