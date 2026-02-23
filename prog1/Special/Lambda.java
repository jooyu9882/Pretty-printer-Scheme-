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

        Node params = c.getCdr().getCar();
        params.print(n, true);

        Node body = c.getCdr().getCdr();

        while (!body.isNull()) {
            System.out.print(" ");
            body.getCar().print(n, true);
            body = body.getCdr();
        }

        System.out.print(")");
    }
}