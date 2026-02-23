package Special;

import Tree.*;

public class Define extends Special {
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;
        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.print("(define ");
        c.getCdr().getCar().print(0);
        Node rest = c.getCdr().getCdr();
        while (!rest.isNull()) {
            System.out.println();
            rest.getCar().print(n + 2);
            rest = rest.getCdr();
        }
        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.println(")");
    }
}
