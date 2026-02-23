package Special;

import Tree.*;

public class Set extends Special {
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;
        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.print("(set! ");
        c.getCdr().getCar().print(0);
        Node rest = c.getCdr().getCdr();
        if (!rest.isNull()) {
            rest.getCar().print(0);
        }
        System.out.println(")");
    }
}
