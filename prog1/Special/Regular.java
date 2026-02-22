package Special;

import Tree.Node;
import Tree.Cons;
import Tree.Nil;

public class Regular extends Special {

    @Override
    public void print(Node t, int n, boolean p) {
        if (!(t instanceof Cons)) return;
        Cons cons = (Cons) t;

        // Only indent if we are not inside parentheses already
        if (!p) {
            for (int i = 0; i < n; i++) System.out.print(" ");
        }

        System.out.print("(");

        Node current = cons;
        boolean first = true;

        while (current instanceof Cons) {
            Cons c = (Cons) current;

            if (!first)
                System.out.print(" ");

            c.getCar().print(0, true);  // IMPORTANT: true keeps it inline

            current = c.getCdr();
            first = false;
        }

        // dotted pair
        if (!(current instanceof Nil)) {
            System.out.print(" . ");
            current.print(0, true);
        }

        System.out.print(")");
        // NO println here — this is the critical fix
    }
}
