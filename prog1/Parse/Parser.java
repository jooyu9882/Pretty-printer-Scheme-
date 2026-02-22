// Parser -- Recursive-descent parser for the Scheme printer and interpreter

package Parse;

import Tree.*;

import Tokens.*;

public class Parser {
    private Scanner scanner;
    private Token lookahead = null;  // one-token lookahead

    public Parser(Scanner s) {
        scanner = s;
    }

    // Utility: get next token, using lookahead if available
    private Token nextToken() {
        if (lookahead != null) {
            Token t = lookahead;
            lookahead = null;
            return t;
        } else {
            return scanner.getNextToken();
        }
    }

    // Utility: push back one token
    private void pushBack(Token t) {
        lookahead = t;
    }

    // Parse an expression (exp)
    public Node parseExp() {
        Token tok = nextToken();
        if (tok == null) return null;  // EOF

        switch (tok.getType()) {
            case TRUE:
                return BoolLit.TRUE;
            case FALSE:
                return BoolLit.FALSE;
            case INT:
                return new IntLit(tok.getIntVal());
            case STRING:
                return new StringLit(tok.getStrVal());
            case IDENT:
                return new Ident(tok.getName().toLowerCase()); // Scheme is case-insensitive
            case QUOTE:
                // 'exp => (quote exp)
                Node quoted = parseExp();
                return new Cons(new Ident("quote"), new Cons(quoted, Nil.NIL));
            case LPAREN:
                return parseRest();
            case RPAREN:
            case DOT:
            default:
                // unexpected token
                System.err.println("Unexpected token: " + tok.getType());
                return parseExp(); // skip and try next
        }
    }

    // Parse a list (rest)
    protected Node parseRest() {
        Token tok = nextToken();

        // Empty list
        if (tok.getType() == TokenType.RPAREN) {
            return Nil.NIL;
        }

        // Put back first token of the first element
        pushBack(tok);
        Node first = parseExp();

        // Check if next token is a dot (for dotted pair)
        tok = nextToken();
        if (tok.getType() == TokenType.DOT) {
            Node second = parseExp();
            Token closing = nextToken();
            if (closing.getType() != TokenType.RPAREN) {
                System.err.println("Expected ')' after dotted pair");
            }
            return new Cons(first, second);
        } else if (tok.getType() == TokenType.RPAREN) {
            return new Cons(first, Nil.NIL);
        } else {
            // Regular list: put back token and parse rest recursively
            pushBack(tok);
            Node rest = parseRest();
            return new Cons(first, rest);
        }
    }
}
