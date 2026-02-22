package Special;

import Tree.Node;
import Tree.Cons;

public class Lambda extends Special {

    @Override
    public void print(Node t, int n, boolean p) {
        if (!(t instanceof Cons)) return;
        Cons cons = (Cons) t;

        Node params = cons.getCdrCar();
        Node body = cons.getCdrCdr();

        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.print("(lambda ");
        params.print(0, false);
        System.out.println();

        Node current = body;
        while (current instanceof Cons) {
            ((Cons) current).getCar().print(n + 2, false);
            current = ((Cons) current).getCdr();
        }

        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.println(")");
    }
}
