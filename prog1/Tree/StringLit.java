// StrLit -- Parse tree node class for representing string literals

package Tree;

public class StringLit extends Node {
    private String strVal;

    public StringLit(String s) {
        strVal = s;
    }

    public void print(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");

        System.out.println("\"" + strVal + "\"");
    }
}
