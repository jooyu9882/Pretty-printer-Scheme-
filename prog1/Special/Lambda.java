// Lambda -- Parse tree node strategy for printing the special form lambda

package Special;

import Tree.Cons;
import Tree.Node;

public class Lambda extends Special {
    @Override
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;

        if (!p) {
            indent(n);
            System.out.print("(");
        }

        System.out.print("lambda ");

        Node params = c.getCdr().getCar();
        params.print(0, false);           // (x y)

        Node body = c.getCdr().getCdr();
        while (!body.isNull()) {
            System.out.println();
            indent(n + 4);                // +4
            body.getCar().print(0, false);
            body = body.getCdr();
        }

        System.out.println();
        indent(n + 2);                    // )  +2
        System.out.print(")");
        System.out.println();
    }
}