package Tree;

public abstract class Node {
    // The argument of print(int) is the number of characters to indent.
    // Every subclass of Node must implement print(int).
    public abstract void print(int n);

    // print(n, p) defaults to print(n) for all classes other than Cons and Nil
    public void print(int n, boolean p) {
        print(n);
    }

    // Type check methods
    public boolean isBoolean() { return false; }
    public boolean isNumber() { return false; }
    public boolean isString() { return false; }
    public boolean isSymbol() { return false; }
    public boolean isNull() { return false; }
    public boolean isPair() { return false; }

    // Accessor methods, overridden in Cons
    public Node getCar() { return null; }
    public Node getCdr() { return null; }
    public void setCar(Node a) { }
    public void setCdr(Node d) { }
}
