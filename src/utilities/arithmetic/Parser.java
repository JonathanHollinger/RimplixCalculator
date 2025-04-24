package utilities.arithmetic;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Parser
{

	public enum Expression
	{
		ADD, SUBTRACT, MULTIPLY, DIVIDE, EXPONENT, LOG, LOG2, SQRT, LEFT_PAREN,
		RIGHT_PAREN, COMMA, NEGATE, NUMBER
	}

	public static List<Token> parse(BufferedReader in) throws IOException
	{
		List<Token> tokens = new ArrayList<>();
		int ch;
		boolean hasBuffered = false;
		char bufferedChar = 0;

		while ((ch = hasBuffered ? bufferedChar : in.read()) != -1)
		{
			char token = (char) ch;
			hasBuffered = false;

			if (Character.isWhitespace(token))
			{
				continue;
			}

			switch (token)
			{
			case '(':
				tokens.add(new Token(Expression.LEFT_PAREN, null));
				break;
			case ')':
				tokens.add(new Token(Expression.RIGHT_PAREN, null));
				break;
			case '+':
				tokens.add(new Token(Expression.ADD, null));
				break;
			case '-':
				if (!tokens.isEmpty() && (tokens.getLast().type == Expression.NUMBER
						|| tokens.getLast().type == Expression.RIGHT_PAREN))
				{
					tokens.add(new Token(Expression.SUBTRACT, null));
				} else
				{
					tokens.add(new Token(Expression.NEGATE, null));
				}
				break;

			case 'x':
				tokens.add(new Token(Expression.MULTIPLY, null));
				break;
			case '÷':
				tokens.add(new Token(Expression.DIVIDE, null));
				break;
			case '^':
				tokens.add(new Token(Expression.EXPONENT, null));
				break;

			case 'l':
				// possible "log("
				in.mark(4); // enough for "og("
				char[] bufL = new char[3];
				if (in.read(bufL) == 3 && bufL[0] == 'o' && bufL[1] == 'g'
						&& bufL[2] == '(')
				{
					// Check if the log has 1 value or 2 values
					in.mark(256);
					StringBuilder inside = new StringBuilder();
					int depth = 1;
					int cha;
					while (depth > 0 && (cha = in.read()) != -1)
					{
						char c = (char) cha;
						if (c == '(')
							depth++;
						else if (c == ')')
							depth--;
						inside.append(c);
					}
					in.reset();

					if (inside.indexOf(",") >= 0)
					{
						tokens.add(new Token(Expression.LOG2, null));
					} else
					{
						tokens.add(new Token(Expression.LOG, null));
					}
					tokens.add(new Token(Expression.LEFT_PAREN, null));
				} else
				{
					// not "log(", rewind and treat as error or fallback
					in.reset();
					throw new IllegalArgumentException("Unknown token starting with 'l'");
				}
				break;

			case 's':
				// possible "sqrt("
				in.mark(6); // enough for "qrt("
				char[] bufS = new char[4];
				if (in.read(bufS) == 4 && bufS[0] == 'q' && bufS[1] == 'r'
						&& bufS[2] == 't' && bufS[3] == '(')
				{
					tokens.add(new Token(Expression.SQRT, null));
					tokens.add(new Token(Expression.LEFT_PAREN, null));
				} else
				{
					in.reset();
					throw new IllegalArgumentException("Unknown token starting with 's'");
				}
				break;

			case ',':
				tokens.add(new Token(Expression.COMMA, null));
				break;

			default:
				if (Character.isDigit(token) || token == '.' || token == '-'
						|| token == '+' || token == 'i')
				{
					StringBuilder number = new StringBuilder();
					if (token == 'i')
					{
						number.append("1i");
					} else
					{
						number.append(token);
					}
					boolean seenDecimal = (token == '.');
					while ((ch = in.read()) != -1)
					{
						char next = (char) ch;

						if (Character.isDigit(next) || (!seenDecimal && next == '.'))
						{
							number.append(next);
							if (next == '.')
								seenDecimal = true;
						} else if (next == 'i')
						{
							number.append('i');
							break;
						} else
						{
							hasBuffered = true;
							bufferedChar = next;
							break;
						}
					}

					tokens.add(new Token(Expression.NUMBER, number.toString()));
				}
				break;
			}
		}

		in.close();
		return tokens;
	}

}
