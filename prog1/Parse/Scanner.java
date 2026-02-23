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

	// Maximum length of strings and identifers
	private int BUFSIZE = 1000;
	private byte[] buf = new byte[BUFSIZE];

	public Scanner(InputStream i) {
		in = new PushbackInputStream(i);
	}

	public Token getNextToken() {
		int ch;

		try {
			// It would be more efficient if we'd maintain our own
			// input buffer and read characters out of that
			// buffer, but reading individual characters from the
			// input stream is easier.
			ch = in.read();

			// TODO: Skip white space and comments
			while (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r' || ch == ';') {

				if (ch == ';') {
					while (ch != '\n' && ch != -1)
						ch = in.read();
				}

				else
					ch = in.read();
			}
			// Return null on EOF
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
				// We ignore the special identifier `...'.
				return new Token(TokenType.DOT);

			// Boolean constants
			else if (ch == '#') {
				ch = in.read();

				if (ch == 't')
					return new Token(TokenType.TRUE);
				else if (ch == 'f')
					return new Token(TokenType.FALSE);
				else if (ch == -1) {
					System.err.println("Unexpected EOF following #");
					return null;
				} else {
					System.err.println("Illegal character '" +
							(char)ch + "' following #");
					return getNextToken();
				}
			}

			// String constants
			else if (ch == '"') {
				// TODO: Scan a string into the buffer variable buf
				int i = 0;
				while (true) {
					ch = in.read();
					if (ch == '"') {
						return new StrToken(new String(buf,0, i));
					} 

					else if (ch == -1) {
						System.err.println("Unexpected EOF in string");
						return null;
					}

					else {
						buf[i++] = (byte)ch;
					}
				}
			}

			// Integer constants
			else if (ch >= '0' && ch <= '9') {
				int i = ch - '0';
				// TODO: scan the number and convert it to an integer
				 while (true) {
					ch = in.read();

					if (ch >= '0' && ch <= '9') {
						i = i * 10 + (ch - '0');
					}
					
					else {
						in.unread(ch);
						break;
					}
				}

				// Put the character after the integer back into the input
				// in.unread(ch);
				return new IntToken(i);
			}

			// Identifiers
			else if (isIdentStart(ch)
				/* or ch is some other valid first character for an identifier */) {
				// TODO: scan an identifier into the buffer variable buf
				int i = 0;
					buf[i++] = (byte) ch;

					while (true) {
						ch = in.read();
						if (ch == -1 || !isIdentPart(ch)) break;
						buf[i++] = (byte) ch;
					}
					if (ch != -1) in.unread(ch);
				// Put the character after the identifier back into the input
				// in.unread(ch);

				return new IdentToken(new String(buf, 0, i).toLowerCase());
			}

			// Illegal character
			else {
				System.err.println("Illegal input character '" + (char)ch + '\'');
				return getNextToken();
			}
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			return null;
		}
	}
	private boolean isIdentStart(int ch) {
		if (Character.isLetter(ch)) return true;
		switch (ch) {
			case '+': case '-': case '*': case '/':
			case '<': case '>': case '=': case '?':
			case '!': case '_':
			case '$': case '%': case '&': case ':':
			case '^': case '~':
				return true;
			default:
				return false;
		}
	}

	private boolean isIdentPart(int ch) {
		if (Character.isLetterOrDigit(ch)) return true;
		switch (ch) {
			case '+': case '-': case '*': case '/':
			case '<': case '>': case '=': case '?':
			case '!': case '_':
			case '$': case '%': case '&': case ':':
			case '^': case '~':
				return true;
			default:
				
			return false;
		}
	}
}
