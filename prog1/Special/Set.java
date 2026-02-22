// Set -- Parse tree node strategy for printing the special form set!

package Special;

import Tree.Node;
import Tree.Cons;
import Tree.Nil;

public class Set extends Special {

    @Override
    public void print(Node t, int n, boolean p) {
        if (!(t instanceof Cons)) return;
        Cons cons = (Cons) t;

        // print opening line
        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.print("(set! ");

        // first element after "set!" (the variable)
        Node varNode = cons.getCdrCar();
        varNode.print(0, false);

        // rest of expression(s), e.g., value(s)
        Node valueNodes = cons.getCdrCdr();
        while (valueNodes instanceof Cons) {
            Node val = ((Cons) valueNodes).getCar();
            System.out.print(" "); // space between elements
            val.print(0, false);
            valueNodes = ((Cons) valueNodes).getCdr();
        }

        System.out.println(")");
    }
}
