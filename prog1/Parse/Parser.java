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
                return new Cons(new Ident("quote"), new Cons(quoted, Nil.getInstance()));

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
    /* Parse List — iterative for flat Cons chain                 */
    /*------------------------------------------------------------*/

    private Node parseList() {
        Token tok = nextToken();
        if (tok == null) {
            System.err.println("Unexpected EOF in list");
            return Nil.getInstance();
        }

        // empty list ()
        if (tok.getType() == TokenType.RPAREN)
            return Nil.getInstance();

        // parse first element
        pushBack(tok);
        Node first = parseExp();

        // create head of the list
        Cons head = new Cons(first, Nil.getInstance());
        Cons current = head;

        // parse remaining elements iteratively
        while (true) {
            tok = nextToken();
            if (tok == null) {
                System.err.println("Unexpected EOF in list");
                break;
            } else if (tok.getType() == TokenType.RPAREN) {
                break; // end of list
            } else if (tok.getType() == TokenType.DOT) {
                // dotted pair (a . b)
                Node second = parseExp();
                current.setCdr(second);

                Token close = nextToken();
                if (close == null || close.getType() != TokenType.RPAREN)
                    System.err.println("Expected ')' after dotted pair");
                return head;
            } else {
                // normal element
                pushBack(tok);
                Node element = parseExp();
                Cons newCons = new Cons(element, Nil.getInstance());
                current.setCdr(newCons);
                current = newCons;
            }
        }

        // attach Special form to head
        if (first instanceof Ident) {
            String name = ((Ident) first).getName();
            switch (name) {
                case "quote": head.setForm(new Quote()); break;
                case "lambda": head.setForm(new Lambda()); break;
                case "begin": head.setForm(new Begin()); break;
                case "if": head.setForm(new If()); break;
                case "let": head.setForm(new Let()); break;
                case "cond": head.setForm(new Cond()); break;
                case "define": head.setForm(new Define()); break;
                case "set!": head.setForm(new Set()); break;
                default: head.setForm(new Regular());
            }
        } else {
            head.setForm(new Regular());
        }

        return head;
    }
}
