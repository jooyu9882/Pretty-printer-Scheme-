// BoolLit -- Parse tree node class for representing boolean literals

package Tree;

public class BoolLit extends Node {
    private boolean boolVal;

    // Singleton instances for #t and #f
    private static final BoolLit trueInstance = new BoolLit(true);
    private static final BoolLit falseInstance = new BoolLit(false);

    // Static constants for easy access in Parser.java
    public static final BoolLit TRUE  = trueInstance;
    public static final BoolLit FALSE = falseInstance;

    // Private constructor
    private BoolLit(boolean b) {
        boolVal = b;
    }

    // Optional getter method if you want to get instance dynamically
    public static BoolLit getInstance(boolean val) {
        return val ? trueInstance : falseInstance;
    }

    // Print method with n-space indentation
    public void print(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");

        System.out.println(boolVal ? "#t" : "#f");
    }
}
