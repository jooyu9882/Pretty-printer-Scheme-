// Lambda -- Parse tree node strategy for printing the special form lambda

package Special;

import Tree.Cons;
import Tree.Node;

public class Lambda extends Special {
 
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;

        if (!p) {
            indent(n);
            System.out.print("(");
        }

        System.out.print("lambda ");

        // parameter list
        Node params = c.getCdr().getCar();
        params.print(n + 2);

        // body
        Node body = c.getCdr().getCdr();
        while (!body.isNull()) {
            System.out.println();
            indent(n + 2);
            body.getCar().print(n + 2);
            body = body.getCdr();
        }

        System.out.print(")");
    }
}