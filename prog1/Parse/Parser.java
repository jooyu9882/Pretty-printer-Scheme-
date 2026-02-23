package Parse;

import Tree.*;
import Tokens.*;

public class Parser {
    private Scanner scanner;
    private Token lookahead;

    public Parser(Scanner s) {
        scanner = s;
        lookahead = null;
    }

    // parse an expression
    public Node parseExp() {
        Token tok;
        if (lookahead != null) {
            tok = lookahead;
            lookahead = null;
        } else {
            tok = scanner.getNextToken();
        }

        if (tok == null)
            return null;

        switch (tok.getType()) {
            case TRUE:
                return BooleanLit.getInstance(true);
            case FALSE:
                return BooleanLit.getInstance(false);
            case INT:
                return new IntLit(tok.getIntVal());
            case STRING:
                return new StrLit(tok.getStrVal());
            case IDENT:
                return new Ident(tok.getName());
            case QUOTE:
                Node quoted = parseExp();
                return new Cons(new Ident("quote"), new Cons(quoted, Nil.getInstance()));
            case LPAREN:
                return parseRest();
            case RPAREN:
            case DOT:
                System.err.println("Unexpected token: " + tok.getType());
                return parseExp();
            default:
                System.err.println("Unknown token: " + tok.getType());
                return parseExp();
        }
    }

    protected Node parseRest() {
        Token tok = scanner.getNextToken();

        if (tok == null) {
            System.err.println("Unexpected EOF in list");
            return null;
        }

        if (tok.getType() == TokenType.RPAREN) {
            return Nil.getInstance();
        }

        // push token back
        lookahead = tok;

        Node first = parseExp();
        Node rest = parseRestTail();

        return new Cons(first, rest);
    }

    private Node parseRestTail() {
        Token tok = scanner.getNextToken();

        if (tok == null) {
            System.err.println("Unexpected EOF in list");
            return null;
        }

        if (tok.getType() == TokenType.RPAREN)
            return Nil.getInstance();
        else if (tok.getType() == TokenType.DOT) {
            Node last = parseExp();
            Token close = scanner.getNextToken();
            if (close == null || close.getType() != TokenType.RPAREN) {
                System.err.println("Expected ')' after dot expression");
            }
            return last;
        } else {
            lookahead = tok;
            Node first = parseExp();
            Node rest = parseRestTail();
            return new Cons(first, rest);
        }
    }
}
