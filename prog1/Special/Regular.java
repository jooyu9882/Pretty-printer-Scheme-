package Special;

import Tree.Node;
import Tree.Cons;
import Tree.Nil;

public class Regular extends Special {

    @Override
    public void print(Node t, int n, boolean p) {
        if (!(t instanceof Cons)) return;
        Cons cons = (Cons) t;

        if (!p) {
            for (int i = 0; i < n; i++) System.out.print(" ");
            System.out.print("(");
        }

        Node first = cons.getCar();
        Node rest = cons.getCdr();

        first.print(0, false);

        Node current = rest;
        while (current instanceof Cons) {
            System.out.print(" ");
            ((Cons) current).getCar().print(0, false);
            current = ((Cons) current).getCdr();
        }

        if (current instanceof Nil) {
            System.out.print(")");
        } else {
            System.out.print(" . ");
            current.print(0, false);
            System.out.print(")");
        }

        // IMPORTANT: removed System.out.println();
        // DO NOT print a newline here
    }
}
