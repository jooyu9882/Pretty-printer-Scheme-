// Set -- Parse tree node strategy for printing the special form set!

package Special;

import Tree.Cons;
import Tree.Node;

public class Set extends Special {
 
    public void print(Node t, int n, boolean p) {
        Cons c = (Cons) t;
        
        if (!p) {
            indent(n);
            System.out.print("(");
        }
        
        System.out.print("set! ");
        
        Node var = c.getCdr().getCar();
        var.print(0, false);
        
        System.out.println();
        indent(n + 2);
        
        Node expr = c.getCdr().getCdr().getCar();
        expr.print(0, false);
    
        System.out.print(")");
    }
}