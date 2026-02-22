package Special;

import Tree.*;

public class Define extends Special {

    private void indent(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");
    }

    public void print(Cons c, int n, boolean p) {

        indent(n);
        System.out.print("(define ");

        Node signature = c.getCdrCar();   // second element
        Node body = c.getCdrCdr();        // rest

        /* -----------------------------
           Case 1: Variable definition
           (define x 5)
        ------------------------------*/
        if (!(signature instanceof Cons)) {

            signature.print(0, false);

            if (body instanceof Cons) {
                System.out.println();
                ((Cons) body).getCar().print(n + 2, false);
            }

            System.out.print(")");
            return;
        }

        /* -----------------------------
           Case 2: Function definition
           (define (f x y) body...)
        ------------------------------*/

        // Print header inline
        signature.print(0, false);

        Node exprs = body;

        while (exprs instanceof Cons) {
            Node exp = ((Cons) exprs).getCar();
            System.out.println();
            exp.print(n + 2, false);
            exprs = ((Cons) exprs).getCdr();
        }

        System.out.print(")");
    }
}
