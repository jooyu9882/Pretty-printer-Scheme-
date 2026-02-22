package Parse;

import Tree.*;
import Tokens.*;
import Special.*;

public class Parser {
    private Scanner scanner;
    private Token lookahead = null;   // one-token lookahead

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
    /* Parse List (flat sequences)                                 */
    /*------------------------------------------------------------*/

    private Node parseList() {
        Token tok = nextToken();

        if (tok == null) {
            System.err.println("Unexpected EOF in list");
            return Nil.getInstance();
        }

        // empty list ()
        if (tok.getType() == TokenType.RPAREN) {
            return Nil.getInstance();
        }

        // push back first token to parse as element
        pushBack(tok);
        Node first = parseExp();

        // check for dotted pair
        tok = nextToken();
        if (tok != null && tok.getType() == TokenType.DOT) {
            Node second = parseExp();
            Token close = nextToken();
            if (close == null || close.getType() != TokenType.RPAREN)
                System.err.println("Expected ')' after dotted pair");
            return new Cons(first, second);
        }

        // if not RPAREN, push back for recursion
        if (tok != null && tok.getType() != TokenType.RPAREN)
            pushBack(tok);

        Node rest = parseList();

        Cons consNode = new Cons(first, rest);

        // Attach special form only to first element
        if (first instanceof Ident) {
            String name = ((Ident) first).getName();
            switch (name) {
                case "quote": consNode.setForm(new Quote()); break;
                case "lambda": consNode.setForm(new Lambda()); break;
                case "begin": consNode.setForm(new Begin()); break;
                case "if": consNode.setForm(new If()); break;
                case "let": consNode.setForm(new Let()); break;
                case "cond": consNode.setForm(new Cond()); break;
                case "define": consNode.setForm(new Define()); break;
                case "set!": consNode.setForm(new Set()); break;
                default: consNode.setForm(new Regular());
            }
        } else {
            consNode.setForm(new Regular());
        }

        // if we hit RPAREN, stop recursion and return list
        if (tok != null && tok.getType() == TokenType.RPAREN)
            return consNode;

        return consNode;
    }
}
