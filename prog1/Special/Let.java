package Special;

import Tree.Node;
import Tree.Cons;
import Tree.Nil;

public class Let extends Special {

    @Override
    public void print(Node t, int n, boolean p) {
        if (!(t instanceof Cons)) return;
        Cons cons = (Cons) t;

        // opening line
        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.println("(let");

        Node rest = cons.getCdr();
        while (rest instanceof Cons) {
            Node element = ((Cons) rest).getCar();
            element.print(n + 2, false);
            rest = ((Cons) rest).getCdr();
        }

        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.println(")");
    }
}
