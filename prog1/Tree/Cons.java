// Cons -- Parse tree node class for representing a Cons nodepackage Tree;

import Special.*;

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
    public Node getCar() { return car; }
    public Node getCdr() { return cdr; }

    // Convenience methods used by Special subclasses
    public Node getCdrCar() {
        if (cdr instanceof Cons) return ((Cons)cdr).getCar();
        return null;
    }

    public Node getCdrCdr() {
        if (cdr instanceof Cons) return ((Cons)cdr).getCdr();
        return null;
    }

    public Node getCdrCdrCar() {
        Node cdr2 = getCdrCdr();
        if (cdr2 instanceof Cons) return ((Cons)cdr2).getCar();
        return null;
    }

    public Node getCdrCdrCdrCar() {
        Node cdr3 = getCdrCdr();
        if (cdr3 instanceof Cons) return ((Cons)cdr3).getCdrCar();
        return null;
    }

    // Printing methods
    public void print(int n) { form.print(this, n, false); }
    public void print(int n, boolean p) { form.print(this, n, p); }
}
