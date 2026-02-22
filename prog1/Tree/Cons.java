// Cons -- Parse tree node class for representing a Cons node

package Tree;

import Special.Begin;
import Special.Cond;
import Special.Define;
import Special.If;
import Special.Lambda;
import Special.Let;
import Special.Quote;
import Special.Regular;
import Special.Set;
import Special.Special;

public class Cons extends Node {
    private Node car;
    private Node cdr;
    private Special form;

    public Cons(Node a, Node d) {
        car = a;
        cdr = d;
        form = new Regular(); // default
    }

    // Allow Parser to set the Special form
    public void setSpecial(Special s) {
        form = s;
    }

    // Accessors for car and cdr
    public Node getCar() {
        return car;
    }

    public Node getCdr() {
        return cdr;
    }

    public void print(int n) {
        form.print(this, n, false);
    }

    public void print(int n, boolean p) {
        form.print(this, n, p);
    }
}
