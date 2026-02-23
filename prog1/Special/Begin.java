package Special;

import Tree.*;

public class Begin extends Special {
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;
        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.println("(begin");
        Node rest = c.getCdr();
        while (!rest.isNull()) {
            rest.getCar().print(n + 2);
            rest = rest.getCdr();
        }
        for (int i = 0; i < n; i++) System.out.print(" ");
        System.out.println(")");
    }
}
