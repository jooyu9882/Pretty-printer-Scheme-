package Special;

import Tree.*;

public class Regular extends Special {
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;
        if (!p) {
            for (int i = 0; i < n; i++)
                System.out.print(" ");
            System.out.print("(");
        }

        c.getCar().print(0);

        Node next = c.getCdr();
        if (!next.isNull()) {
            if (next.isPair()) {
                System.out.print(" ");
                next.print(0, true);
            } else {
                System.out.print(" . ");
                next.print(0);
            }
        } else {
            System.out.println(")");
        }
    }
}
