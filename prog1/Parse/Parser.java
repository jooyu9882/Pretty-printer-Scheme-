package Parse;

import Tree.*;
import Tokens.Token;
import Tokens.TokenType;

public class Parser {
    private Scanner scanner;
    private Token currentToken = null;

    public Parser(Scanner s) {
        scanner = s;
    }

    private Token getToken() {

        if (currentToken == null) {
            currentToken = scanner.getNextToken();
        }

        return currentToken;
    }

    private Token consumeToken() {
        Token t = currentToken;
        currentToken = null;
        return t;
    }

    public Node parseExp() {
        Token tok = getToken();

        if (tok == null) {
            return null; 
        }

        consumeToken();

        switch (tok.getType()) {

            case LPAREN:
                return parseRest();
            case TRUE:
                return BooleanLit.getInstance(true);
            case FALSE:
                return BooleanLit.getInstance(false);
            case QUOTE:
                Node quoted = parseExp();
                return new Cons(new Ident("quote"), new Cons(quoted, Nil.getInstance()));
            case INT:
                return new IntLit(tok.getIntVal());
            case STRING:
                return new StrLit(tok.getStrVal());
            case IDENT:
                return new Ident(tok.getName());

            default:
                return null;
                
        }
    }

    protected Node parseRest() {
        // rest -> )
        Token tok = getToken();
        if (tok == null) return null; // end

        if (tok.getType() == TokenType.RPAREN) {
            consumeToken();
            return Nil.getInstance();
        }

        
        java.util.ArrayList<Node> items = new java.util.ArrayList<>();

        
        items.add(parseExp());
        if (items.get(0) == null) return null;

        while (true) {
            tok = getToken();
            if (tok == null) return null; // unexpected EOF

            if (tok.getType() == TokenType.RPAREN) {
                consumeToken();
                return buildList(items, Nil.getInstance());
            }

            if (tok.getType() == TokenType.DOT) {
                consumeToken();
                Node tail = parseExp();
                if (tail == null) return null;

                tok = getToken();
                if (tok == null || tok.getType() != TokenType.RPAREN) return null;
                consumeToken();

                return buildList(items, tail);
            }

            
            Node nextExp = parseExp();
            if (nextExp == null) return null;
            items.add(nextExp);
        }
    }
    
    // TODO: Add any additional methods you might need.
    private Node buildList(java.util.ArrayList<Node> items, Node tail) {
        Node result = tail;
        for (int i = items.size() - 1; i >= 0; i--) {
            result = new Cons(items.get(i), result);
        }
        return result;
    }
}