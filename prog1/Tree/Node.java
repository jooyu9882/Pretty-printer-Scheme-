package Tree;

// Superclass for all parse tree nodes
public class Node {

    // Print methods
    public void print(int n) {
        // Default: do nothing
    }

    public void print(int n, boolean p) {
        print(n);
    }

    // Type checks
    public boolean isBoolean() { return false; }
    public boolean isNumber() { return false; }
    public boolean isString() { return false; }
    public boolean isSymbol() { return false; }
    public boolean isNull() { return false; }
    public boolean isPair() { return false; }

    // Accessors for pairs
    public Node getCar() {
        System.err.println("getCar() not supported for this node");
        return null;
    }

    public Node getCdr() {
        System.err.println("getCdr() not supported for this node");
        return null;
    }

    public void setCar(Node a) {
        System.err.println("setCar() not supported for this node");
    }

    public void setCdr(Node d) {
        System.err.println("setCdr() not supported for this node");
    }
}
    public void setCdr(Node d) {
    }
}
