package Special;

import Tree.*;

public class If extends Special {
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;
        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.print("(if ");
        c.getCdr().getCar().print(0);
        Node rest = c.getCdr().getCdr();
        if (!rest.isNull()) {
            System.out.println();
            rest.getCar().print(n + 2);
            Node last = rest.getCdr();
            if (!last.isNull()) {
                last.getCar().print(n + 2);
            }
        }
        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.println(")");
    }
}
