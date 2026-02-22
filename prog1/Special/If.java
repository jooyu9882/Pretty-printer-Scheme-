package Special;

import Tree.Node;
import Tree.Cons;
import Tree.Nil;

public class If extends Special {

    @Override
    public void print(Node t, int n, boolean p) {
        if (!(t instanceof Cons)) return;
        Cons cons = (Cons) t;

        Node test = cons.getCdrCar();
        Node trueBranch = cons.getCdrCdrCar();
        Node falseBranch = cons.getCdrCdrCdrCar();

        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.print("(if ");
        test.print(0, false);
        System.out.println();

        trueBranch.print(n + 2, false);

        if (falseBranch != null && !(falseBranch instanceof Nil)) {
            falseBranch.print(n + 2, false);
        }

        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.println(")");
    }
}
