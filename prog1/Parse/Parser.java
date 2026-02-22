// Parser -- Recursive-descent parser for Scheme Pretty-Printer 

package Parse;

import Tree.*;
import Tokens.*;

public class Parser {
    private Scanner scanner;
    private Token lookahead = null; // one-token lookahead

    public Parser(Scanner s) {
        scanner = s;
    }

    // Get next token, using lookahead if available
    private Token nextToken() {
        if (lookahead != null) {
            Token t = lookahead;
            lookahead = null;
            return t;
        }
        return scanner.getNextToken();
    }

    // Push back one token
    private void pushBack(Token t) {
        lookahead = t;
    }

    // Parse an expression (exp)
    public Node parseExp() {
        Token tok = nextToken();
        if (tok == null) return null;

        switch (tok.getType()) {
            case TRUE: return BoolLit.getInstance(true);
            case FALSE: return BoolLit.getInstance(false);
            case INT: return new IntLit(tok.getIntVal());
            case STRING: return new StringLit(tok.getStrVal());
            case IDENT: return new Ident(tok.getName().toLowerCase());
            case QUOTE:
                Node quoted = parseExp();
                return new Cons(new Ident("quote"), new Cons(quoted, Nil.getInstance()));
            case LPAREN: return parseRest();
            case RPAREN:
            case DOT:
            default:
                System.err.println("Unexpected token: " + tok.getType());
                return Nil.getInstance(); // skip invalid token safely
        }
    }

    // Parse a list (rest)
    protected Node parseRest() {
        Token tok = nextToken();
        if (tok == null) {
            System.err.println("Unexpected EOF in list");
            return Nil.getInstance();
        }

        // Empty list
        if (tok.getType() == TokenType.RPAREN) {
            return Nil.getInstance();
        }

        // Put back first token of first element
        pushBack(tok);
        Node first = parseExp();

        // Check for dotted list
        tok = nextToken();
        if (tok != null && tok.getType() == TokenType.DOT) {
            Node second = parseExp();
            Token closing = nextToken();
            if (closing == null || closing.getType() != TokenType.RPAREN) {
                System.err.println("Expected ')' after dotted pair");
            }
            return new Cons(first, second);
        } else if (tok != null && tok.getType() == TokenType.RPAREN) {
            return new Cons(first, Nil.getInstance());
        } else {
            // Regular list: put back token and parse recursively
            if (tok != null) pushBack(tok);
            Node rest = parseRest();
            Cons consNode = new Cons(first, rest);

            // Determine special form if first is an identifier
            if (first instanceof Ident) {
                String name = ((Ident) first).getName();
                switch (name) {
                    case "quote": consNode.setForm(new Special.Quote()); break;
                    case "lambda": consNode.setForm(new Special.Lambda()); break;
                    case "begin": consNode.setForm(new Special.Begin()); break;
                    case "if": consNode.setForm(new Special.If()); break;
                    case "let": consNode.setForm(new Special.Let()); break;
                    case "cond": consNode.setForm(new Special.Cond()); break;
                    case "define": consNode.setForm(new Special.Define()); break;
                    case "set!": consNode.setForm(new Special.Set()); break;
                    default: consNode.setForm(new Special.Regular());
                }
            } else {
                consNode.setForm(new Special.Regular());
            }

            return consNode;
        }
    }
}
