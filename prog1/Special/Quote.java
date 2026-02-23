// Quote -- Parse tree node strategy for printing the special form quote

package Special;

import Tree.Cons;
import Tree.Node;

public class Quote extends Special {
    @Override
    public void print(Node t, int n, boolean p) {
        if (!p) indent(n);

        // t = (quote <exp>)
        Cons c = (Cons) t;
        Node quoted = c.getCdr().getCar();

        System.out.print("'");
        quoted.print(0, false);
    }
}