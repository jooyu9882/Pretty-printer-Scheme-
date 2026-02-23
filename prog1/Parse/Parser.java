package Parse;

import Tree.*;
import Tokens.*;
import Special.*;

/**
 * Parser.java
 * A clean, compilable Parser class for a Scheme-like language.
 */
public class Parser {
    private Scanner scanner;
    private Token lookahead = null;   // one-token lookahead

    public Parser(Scanner s) {
        this.scanner = s;
    }

    /*----------------------------*/
    /* Token helpers             */
    /*----------------------------*/

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

    /*----------------------------*/
    /* Parse expressions          */
    /*----------------------------*/

    public Node parseExp() {
        Token tok = nextToken();
        if (tok == null) return null;  // EOF

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
                Node quoted = parseExp();
                if (quoted == null) quoted = Nil.getInstance();
                return new Cons(new Ident("quote"), new Cons(quoted, Nil.getInstance()));
            case LPAREN:
                return parseList();
            case RPAREN:
            case DOT:
            default:
                System.err.println("Unexpected token: " + tok.getType());
                return Nil.getInstance();
        }
    }

    /*----------------------------*/
    /* Parse lists                */
    /*----------------------------*/

    private Node parseList() {
        Token tok = nextToken();
        if (tok == null) {
            System.err.println("Unexpected EOF in list");
            return Nil.getInstance();
        }

        if (tok.getType() == TokenType.RPAREN) {
            return Nil.getInstance();  // empty list
        }

        pushBack(tok);
        Node first = parseExp();
        if (first == null) first = Nil.getInstance();

        tok = nextToken();
        if (tok != null && tok.getType() == TokenType.DOT) {
            Node second = parseExp();
            if (second == null) second = Nil.getInstance();
            tok = nextToken();
            if (tok == null || tok.getType() != TokenType.RPAREN)
                System.err.println("Expected ')' after dotted pair");
            return new Cons(first, second);
        }

        if (tok != null && tok.getType() != TokenType.RPAREN)
            pushBack(tok);

        Node rest = parseList();
        Cons consNode = new Cons(first, rest);

        // Determine if this is a special form
        if (first instanceof Ident) {
            consNode.setForm(determineSpecial(first));
        } else {
            consNode.setForm(new Regular());
        }

        return consNode;
    }

    /*----------------------------*/
    /* Determine special forms    */
    /*----------------------------*/

    private Special determineSpecial(Node first) {
        if (!(first instanceof Ident)) return new Regular();
        String name = ((Ident) first).getName();
        switch (name) {
            case "quote": return new Quote();
            case "lambda": return new Lambda();
            case "begin": return new Begin();
            case "if": return new If();
            case "let": return new Let();
            case "cond": return new Cond();
            case "define": return new Define();
            case "set!": return new Set();
            default: return new Regular();
        }
    }
}
