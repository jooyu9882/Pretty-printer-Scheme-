// Scanner -- The lexical analyzer for the Scheme printer and interpreter.

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

    // Maximum length of strings and identifiers
    private int BUFSIZE = 1000;
    private byte[] buf = new byte[BUFSIZE];

    public Scanner(InputStream i) {
        in = new PushbackInputStream(i);
    }

    public Token getNextToken() {
        int ch;

        try {
            ch = in.read();

            // Skip whitespace and comments
            while (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r' || ch == '\f' || ch == ';') {
                if (ch == ';') {
                    while (ch != '\n' && ch != -1)
                        ch = in.read();
                } else {
                    ch = in.read();
                }
            }

            // EOF
            if (ch == -1)
                return null;

            // Special characters
            else if (ch == '\'')
                return new Token(TokenType.QUOTE);
            else if (ch == '(')
                return new Token(TokenType.LPAREN);
            else if (ch == ')')
                return new Token(TokenType.RPAREN);
            else if (ch == '.')
                return new Token(TokenType.DOT);

            // Boolean constants
            else if (ch == '#') {
                ch = in.read();
                if (ch == 't' || ch == 'T')
                    return new Token(TokenType.TRUE);
                else if (ch == 'f' || ch == 'F')
                    return new Token(TokenType.FALSE);
                else if (ch == -1) {
                    System.err.println("Unexpected EOF following #");
                    return null;
                } else {
                    System.err.println("Illegal character '" + (char) ch + "' following #");
                    return getNextToken();
                }
            }

            // String constants
            else if (ch == '"') {
                int i = 0;
                while (true) {
                    ch = in.read();
                    if (ch == '"') {
                        return new StrToken(new String(buf, 0, i));
                    } else if (ch == -1) {
                        System.err.println("Unexpected EOF in string");
                        return null;
                    } else {
                        buf[i++] = (byte) ch;
                        if (i >= BUFSIZE) {
                            System.err.println("String too long");
                            return null;
                        }
                    }
                }
            }

            // Integer constants
            else if (ch >= '0' && ch <= '9') {
                int value = ch - '0';
                while (true) {
                    ch = in.read();
                    if (ch >= '0' && ch <= '9') {
                        value = value * 10 + (ch - '0');
                    } else {
                        if (ch != -1)
                            in.unread(ch);
                        break;
                    }
                }
                return new IntToken(value);
            }

            // Identifiers
            else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') ||
                     "!$%&*/:<=>?~_^+-".indexOf(ch) != -1) {

                int i = 0;
                buf[i++] = (byte) ch;

                while (true) {
                    ch = in.read();
                    if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') ||
                        (ch >= '0' && ch <= '9') ||
                        "!$%&*/:<=>?~_^+-".indexOf(ch) != -1) {
                        buf[i++] = (byte) ch;
                        if (i >= BUFSIZE) {
                            System.err.println("Identifier too long");
                            return null;
                        }
                    } else {
                        if (ch != -1) in.unread(ch);
                        break;
                    }
                }

                return new IdentToken(new String(buf, 0, i).toLowerCase()); // lowercase for Scheme
            }

            // Illegal character
            else {
                System.err.println("Illegal input character '" + (char) ch + "'");
                return getNextToken();
            }

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            return null;
        }
    }
}
