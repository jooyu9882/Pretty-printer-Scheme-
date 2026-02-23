package Parse;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import Tokens.Token;
import Tokens.TokenType;
import Tokens.IdentToken;
import Tokens.IntToken;
import Tokens.StrToken;

public class Scanner {

private PushbackInputStream in;

private int BUFSIZE = 1000;
private byte[] buf = new byte[BUFSIZE];

public Scanner(InputStream i) {
    in = new PushbackInputStream(i);
}

public Token getNextToken() {

    int ch;

    try {

        /* ----------- Skip whitespace and comments ----------- */
        while (true) {
            ch = in.read();

            if (ch == -1)
                return null;

            if (Character.isWhitespace(ch))
                continue;

            if (ch == ';') {
                while (ch != '\n' && ch != -1)
                    ch = in.read();
                continue;
            }

            break;
        }

        /* ----------- Single char tokens ----------- */
        if (ch == '\'')
            return new Token(TokenType.QUOTE);

        if (ch == '(')
            return new Token(TokenType.LPAREN);

        if (ch == ')')
            return new Token(TokenType.RPAREN);

        if (ch == '.')
            return new Token(TokenType.DOT);

        /* ----------- Boolean ----------- */
        if (ch == '#') {
            ch = in.read();

            if (ch == 't')
                return new Token(TokenType.TRUE);
            if (ch == 'f')
                return new Token(TokenType.FALSE);

            System.err.println("Illegal boolean");
            return null;
        }

        /* ----------- String ----------- */
        if (ch == '"') {

            int i = 0;

            while (true) {
                ch = in.read();

                if (ch == '"')
                    return new StrToken(new String(buf, 0, i));

                if (ch == -1) {
                    System.err.println("EOF inside string");
                    return null;
                }

                buf[i++] = (byte) ch;
            }
        }

        /* ----------- Integer ----------- */
        if (Character.isDigit(ch)) {

            int value = ch - '0';

            while (true) {
                ch = in.read();

                if (!Character.isDigit(ch))
                    break;

                value = value * 10 + (ch - '0');
            }

            if (ch != -1)
                in.unread(ch);

            return new IntToken(value);
        }

        /* ----------- Identifier ----------- */
        if (Character.isLetter(ch) || "+-*/<>=!?".indexOf(ch) != -1) {

            int i = 0;
            buf[i++] = (byte) Character.toLowerCase(ch);

            while (true) {
                ch = in.read();

                if (Character.isLetterOrDigit(ch) ||
                    "+-*/<>=!?".indexOf(ch) != -1) {
                    buf[i++] = (byte) Character.toLowerCase(ch);
                }
                else {
                    if (ch != -1)
                        in.unread(ch);
                    break;
                }
            }

            return new IdentToken(new String(buf, 0, i));
        }

        /* ----------- Illegal ----------- */
        System.err.println("Illegal character: " + (char) ch);
        return getNextToken();

    } catch (IOException e) {
        System.err.println("IO Error");
        return null;
    }
}

}
