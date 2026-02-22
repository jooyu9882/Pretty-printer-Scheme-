package Special;

import Tree.Node;
import Tree.Cons;

public class Cond extends Special {

    @Override
    public void print(Node t, int n, boolean p) {
        if (!(t instanceof Cons)) return;
        Cons cons = (Cons) t;

        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.println("(cond");

        Node rest = cons.getCdr();
        while (rest instanceof Cons) {
            Node caseNode = ((Cons) rest).getCar();
            caseNode.print(n + 2, false);
            rest = ((Cons) rest).getCdr();
        }

        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.println(")");
    }
}
