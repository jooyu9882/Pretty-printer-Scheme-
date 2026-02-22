private Node parseExp() {
    Token tok = nextToken();
    if (tok == null) return null;  // EOF

    switch (tok.getType()) {
        case TRUE: return BoolLit.getInstance(true);
        case FALSE: return BoolLit.getInstance(false);
        case INT: return new IntLit(tok.getIntVal());
        case STRING: return new StringLit(tok.getStrVal());
        case IDENT: return new Ident(tok.getName().toLowerCase());
        case QUOTE:
            Node quoted = parseExp();
            if (quoted == null) {
                System.err.println("Unexpected EOF after quote");
                quoted = Nil.getInstance();
            }
            return new Cons(new Ident("quote"), new Cons(quoted, Nil.getInstance()), new Quote());
        case LPAREN:
            return parseList();
        case RPAREN:
            System.err.println("Unexpected ')'");
            return Nil.getInstance();
        default:
            System.err.println("Unexpected token: " + tok.getType());
            return Nil.getInstance();
    }
}

private Node parseList() {
    Token tok = nextToken();
    if (tok == null) {
        System.err.println("Unexpected EOF in list");
        return Nil.getInstance();
    }

    if (tok.getType() == TokenType.RPAREN) {  // empty list
        return Nil.getInstance();
    }

    pushBack(tok);
    Node first = parseExp();
    if (first == null) first = Nil.getInstance();

    // peek at next token
    tok = nextToken();
    if (tok != null && tok.getType() == TokenType.DOT) {
        Node second = parseExp();
        if (second == null) second = Nil.getInstance();
        tok = nextToken();
        if (tok == null || tok.getType() != TokenType.RPAREN) {
            System.err.println("Expected ')' after dotted pair");
        }
        Cons consNode = new Cons(first, second, new Regular());
        return consNode;
    }

    if (tok != null && tok.getType() == TokenType.RPAREN) {
        Cons consNode = new Cons(first, Nil.getInstance(), determineSpecial(first));
        return consNode;
    }

    if (tok != null) pushBack(tok);

    Node rest = parseList();
    Cons consNode = new Cons(first, rest, determineSpecial(first));
    return consNode;
}

private Special determineSpecial(Node first){
    if (!(first instanceof Ident)) return new Regular();
    switch (((Ident)first).getName()) {
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
