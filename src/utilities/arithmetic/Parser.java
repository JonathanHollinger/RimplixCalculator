package utilities.arithmetic;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Parser
{

	public enum Expression
	{
		ADD, SUBTRACT, MULTIPLY, DIVIDE, LEFT_PAREN, RIGHT_PAREN, NEGATE, NUMBER
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
			default:
				if (Character.isDigit(token) || token == '.' || token == '-'
						|| token == '+')
				{
					StringBuilder number = new StringBuilder();
					number.append(token);
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
