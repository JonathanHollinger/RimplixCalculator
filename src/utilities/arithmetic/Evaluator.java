package utilities.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import utilities.ComplexNums;

public class Evaluator
{
	private List<Token> tokens;
	private ComplexNums evaluated;

	public Evaluator(List<Token> tokens)
	{
		this.tokens = tokens;
		this.evaluated = evaluate();
	}

	public ComplexNums result()
	{
		return evaluated;
	}

	private ComplexNums evaluate()
	{
		List<Token> rpn = toRPN(tokens); // Convert infix to postfix
		return evaluateRPN(rpn); // Evaluate postfix expression
	}

	private List<Token> toRPN(List<Token> tokens)
	{
		List<Token> output = new ArrayList<>();
		Stack<Token> operators = new Stack<>();

		for (Token token : tokens)
		{
			switch (token.type)
			{
			case NUMBER:
				output.add(token);
				break;

			case ADD:
			case SUBTRACT:
			case MULTIPLY:
			case DIVIDE:
				while (!operators.isEmpty()
						&& isHigherPrecedence(operators.peek(), token))
				{
					output.add(operators.pop());
				}
				operators.push(token);
				break;

			case LEFT_PAREN:
				operators.push(token);
				break;

			case RIGHT_PAREN:
				while (!operators.isEmpty()
						&& operators.peek().type != Parser.Expression.LEFT_PAREN)
				{
					output.add(operators.pop());
				}
				if (!operators.isEmpty()
						&& operators.peek().type == Parser.Expression.LEFT_PAREN)
				{
					operators.pop(); // Discard left parenthesis
				}
				break;

			default:
				throw new IllegalArgumentException("Unknown token type: " + token.type);
			}
		}

		while (!operators.isEmpty())
		{
			output.add(operators.pop());
		}

		return output;
	}

	private ComplexNums evaluateRPN(List<Token> rpn)
	{
		Stack<ComplexNums> stack = new Stack<>();

		for (Token token : rpn)
		{
			switch (token.type)
			{
			case NUMBER:
				String val = token.value;

				if (val.endsWith("i"))
				{
					// Remove the trailing 'i' and parse as a double
					String numericPart = val.substring(0, val.length() - 1);

					double imag = 1.0;
					if (!numericPart.isEmpty() && !numericPart.equals("+")
							&& !numericPart.equals("-"))
					{
						imag = Double.parseDouble(numericPart);
					} else if (numericPart.equals("-"))
					{
						imag = -1.0;
					}

					stack.push(new ComplexNums(imag, 0));
				} else
				{
					// Standard real number
					stack.push(new ComplexNums(0, Double.parseDouble(val)));
				}
				break;
			case ADD:
			case SUBTRACT:
			case MULTIPLY:
			case DIVIDE:
				ComplexNums b = stack.pop();
				ComplexNums a = stack.pop();
				switch (token.type)
				{
				case ADD:
					stack.push((ComplexNums) a.add(b));
					break;
				case SUBTRACT:
					stack.push((ComplexNums) a.sub(b));
					break;
				case MULTIPLY:
					stack.push((ComplexNums) a.mult(b));
					break;
				case DIVIDE:
					stack.push((ComplexNums) a.div(b));
					break;
				default:
					throw new IllegalArgumentException(
							"Unknown token in RPN: " + token.type);
				}
				break;

			default:
				throw new IllegalArgumentException("Unknown token in RPN: " + token.type);
			}
		}

		return stack.pop();
	}

	private boolean isHigherPrecedence(Token op1, Token op2)
	{
		int p1 = getPrecedence(op1.type);
		int p2 = getPrecedence(op2.type);
		return p1 >= p2;
	}

	private int getPrecedence(Parser.Expression type)
	{
		switch (type)
		{
		case MULTIPLY:
		case DIVIDE:
			return 2;
		case ADD:
		case SUBTRACT:
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public String toString()
	{
		return "Result of Evaluation: " + evaluated.toString();
	}
}
