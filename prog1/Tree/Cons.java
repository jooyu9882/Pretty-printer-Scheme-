package Tree;

import Special.*;

public class Cons extends Node {
    private Node car;
    private Node cdr;
    private Special form;

    public Cons(Node a, Node d) {
        car = a;
        cdr = d;
        parseList();
    }

    // Determines the form for printing (special forms or regular list)
    private void parseList() {
        form = new Regular();

        if (car instanceof Ident) {
            String name = ((Ident) car).getName();
            switch (name) {
                case "if":      form = new If(); break;
                case "lambda":  form = new Lambda(); break;
                case "quote":   form = new Quote(); break;
                case "begin":   form = new Begin(); break;
                case "define":  form = new Define(); break;
                case "set!":    form = new Set(); break;
                case "let":     form = new Let(); break;
                case "cond":    form = new Cond(); break;
            }
        }
    }

    // Printing
    public void print(int n) {
        form.print(this, n, false);
    }

    public void print(int n, boolean p) {
        form.print(this, n, p);
    }

    // Getters and setters
    public Node getCar() { return car; }
    public Node getCdr() { return cdr; }

    public void setCar(Node a) { 
        car = a; 
        parseList(); // Re-evaluate special form when car changes
    }

    public void setCdr(Node d) { cdr = d; }

    // Type check
    public boolean isPair() { return true; }
}
