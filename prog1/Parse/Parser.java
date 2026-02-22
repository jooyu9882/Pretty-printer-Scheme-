package Parse;

import Tree.*;
import Tokens.*;
import Special.*;

public class Parser {
private Scanner scanner;
private Token lookahead = null;   // one-token lookahead

```
public Parser(Scanner s) {
    scanner = s;
}

/*------------------------------------------------------------*/
/* Token helpers                                               */
/*------------------------------------------------------------*/

private Token nextToken() {
    if (lookahead != null) {
        Token t = lookahead;
        lookahead = null;
        return t;
    }
    return scanner.getNextToken();
}

private void pushBack(Token t) {
    lookahead = t;
}

/*------------------------------------------------------------*/
/* Parse Expression                                            */
/*------------------------------------------------------------*/

public Node parseExp() {
    Token tok = nextToken();
    if (tok == null)
        return null;

    switch (tok.getType()) {

        case TRUE:
            return BoolLit.getInstance(true);

        case FALSE:
            return BoolLit.getInstance(false);

        case INT:
            return new IntLit(tok.getIntVal());

        case STRING:
            return new StringLit(tok.getStrVal());

        case IDENT:
            return new Ident(tok.getName().toLowerCase());

        case QUOTE:
            // 'x  →  (quote x)
            Node quoted = parseExp();
            return new Cons(new Ident("quote"),
                    new Cons(quoted, Nil.getInstance()));

        case LPAREN:
            return parseList();   // start a list

        case RPAREN:
        case DOT:
        default:
            System.err.println("Unexpected token: " + tok.getType());
            return Nil.getInstance();
    }
}

/*------------------------------------------------------------*/
/* Parse List  (THIS is the important fix)                     */
/*------------------------------------------------------------*/

private Node parseList() {
    Token tok = nextToken();

    if (tok == null) {
        System.err.println("Unexpected EOF in list");
        return Nil.getInstance();
    }

    // ()
    if (tok.getType() == TokenType.RPAREN)
        return Nil.getInstance();

    // first element
    pushBack(tok);
    Node first = parseExp();

    // dotted pair handling: (a . b)
    tok = nextToken();
    if (tok != null && tok.getType() == TokenType.DOT) {
        Node second = parseExp();

        Token close = nextToken();
        if (close == null || close.getType() != TokenType.RPAREN)
            System.err.println("Expected ')' after dotted pair");

        return new Cons(first, second);
    }

    // otherwise normal list — put token back
    if (tok != null)
        pushBack(tok);

    Node rest = parseList();

    /*---------------- CRITICAL PART ----------------*/

    Cons list = new Cons(first, rest);

    // Attach special form to THIS cons cell (the head)
    if (first instanceof Ident) {
        String name = ((Ident) first).getName();

        switch (name) {
            case "quote":
                list.setForm(new Quote());
                break;

            case "lambda":
                list.setForm(new Lambda());
                break;

            case "begin":
                list.setForm(new Begin());
                break;

            case "if":
                list.setForm(new If());
                break;

            case "let":
                list.setForm(new Let());
                break;

            case "cond":
                list.setForm(new Cond());
                break;

            case "define":
                list.setForm(new Define());
                break;

            case "set!":
                list.setForm(new Set());
                break;

            default:
                list.setForm(new Regular());
        }
    } else {
        list.setForm(new Regular());
    }

    return list;
}
```

}

}
