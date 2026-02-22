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

    public Cond(Node a, Node d) {
        car = a;
        cdr = d;
        parseList();
    }

    // parseList() `parses' special forms, constructs an appropriate
    // object of a subclass of Special, and stores a pointer to that
    // object in variable form. It would be possible to fully parse
    // special forms at this point. Since this causes complications
    // when using (incorrect) programs as data, it is easiest to let
    // parseList only look at the car for selecting the appropriate
    // object from the Special hierarchy and to leave the rest of
    // parsing up to the interpreter.
    void parseList() {
    

    // TODO: Add any helper functions for parseList

    form = new Regular();

    if (car instanceof Ident) {

        String name = ((Ident) car).getName();

        if (name.equals("if"))
            form = new If();
        else if (name.equals("lambda"))
            form = new Lambda();
        else if (name.equals("quote"))
            form = new Quote();
        else if (name.equals("begin"))
            form = new Begin();
        else if (name.equals("define"))
            form = new Define();
        else if (name.equals("set!"))
            form = new Set();
        else if (name.equals("let"))
            form = new Let();
        else if (name.equals("cond"))
            form = new Cond();
    }
    }

    // to the class hierarchy as needed.

    public void print(int n) {
        form.print(this, n, false);
    }

    public void print(int n, boolean p) {
        form.print(this, n, p);
    }
}
