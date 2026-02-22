package Special;

import Tree.Node;
import Tree.Cons;
import Tree.Nil;

public class Quote extends Special {

    @Override
    public void print(Node t, int n, boolean p) {
        if (!(t instanceof Cons)) return;
        Cons cons = (Cons) t;
        Node quoted = cons.getCdrCar(); // assume this gives the second element

        // print quote character
        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.print("'");

        // if the quoted element is a list, print as a regular list
        if (quoted instanceof Cons) {
            new Regular().print(quoted, 0, false);
        } else {
            quoted.print(0, false);
        }
        System.out.println();
    }
}
