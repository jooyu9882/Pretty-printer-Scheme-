package Tree;

import Special.*;

public class Cons extends Node {

    private Node car;
    private Node cdr;
    private Special form;

    public Cons(Node a, Node d) {
        car = a;
        cdr = d;
        form = new Regular();   // default printing behavior
    }

    // Parser assigns a special form printer (define, if, lambda...)
    public void setForm(Special s) {
        form = s;
    }

    // Allow Parser to link nodes in iterative list construction
    public void setCdr(Node n) {
        cdr = n;
    }

    // Accessors
    public Node getCar() { return car; }
    public Node getCdr() { return cdr; }

    // Convenience navigation helpers used by Special classes
    public Node getCdrCar() {
        if (cdr instanceof Cons)
            return ((Cons)cdr).getCar();
        return null;
    }

    public Node getCdrCdr() {
        if (cdr instanceof Cons)
            return ((Cons)cdr).getCdr();
        return null;
    }

    public Node getCdrCdrCar() {
        Node temp = getCdrCdr();
        if (temp instanceof Cons)
            return ((Cons)temp).getCar();
        return null;
    }

    public Node getCdrCdrCdrCar() {
        Node temp = getCdrCdr();
        if (temp instanceof Cons)
            return ((Cons)temp).getCdrCar();
        return null;
    }

    // Printing dispatch (Special handles formatting)
    @Override
    public void print(int n) {
        form.print(this, n, false);
    }

    @Override
    public void print(int n, boolean p) {
        form.print(this, n, p);
    }
}
