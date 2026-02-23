// Nil -- Parse tree node class for representing the empty list
package Tree;

public class Nil extends Node {
    private static Nil instance = new Nil();

    private Nil() {}

    public static Nil getInstance() {
        return instance;
    }

    
    public void print(int n) {
        print(n, false);
    }

    
    public void print(int n, boolean p) {
        // indent는 p==false (top-level)일 때만
        if (!p) {
            for (int i = 0; i < n; i++) System.out.print(" ");
        }

        if (p) {
            // caller가 "("를 이미 찍었음 → 닫기만 해서 "()" 완성
            System.out.print(")");
        } else {
            // 혼자 전체 출력
            System.out.print("()");
        }
    }

    
    public boolean isNull() {
        return true;
    }
}