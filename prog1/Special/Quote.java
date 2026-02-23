package Special;

import Tree.*;

public class Quote extends Special {
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;
        System.out.print("'");
        c.getCdr().getCar().print(0);
    }
}
