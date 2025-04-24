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
		List<Token> rpn = toRPN(tokens);
		return evaluateRPN(rpn);
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

			case LOG:
			case LOG2:
			case SQRT:
				operators.push(token);
				break;

			case ADD:
			case SUBTRACT:
			case MULTIPLY:
			case DIVIDE:
			case NEGATE:
			case EXPONENT:
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
					operators.pop();
				}

				if (!operators.isEmpty())
				{
					Parser.Expression t = operators.peek().type;
					if (t == Parser.Expression.LOG || t == Parser.Expression.SQRT
							|| t == Parser.Expression.EXPONENT)
					{
						output.add(operators.pop());
					}
				}
				break;

			case COMMA:
				// pop operators until left paren, so next argument is separate
				while (!operators.isEmpty()
						&& operators.peek().type != Parser.Expression.LEFT_PAREN)
				{
					output.add(operators.pop());
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

					stack.push(new ComplexNums(0, imag)); // (real, imaginary)
				} else
				{
					// Standard real number
					stack.push(new ComplexNums(Double.parseDouble(val), 0)); // (real,
																				// imaginary)
				}
				break;

			case NEGATE:
				ComplexNums x = stack.pop();
				stack.push(new ComplexNums(-x.getVal(), -x.getIConst()));
				break;

			case LOG:
				// natural log: pop one
				ComplexNums v1 = stack.pop();
				stack.push((ComplexNums) v1.log(new ComplexNums(Math.E, 0)));
				break;

			case LOG2:
				// arbitrary log: pop base then value
				ComplexNums logBase = stack.pop();
				ComplexNums logVal = stack.pop();
				stack.push((ComplexNums) logVal.log(logBase));
				break;

			case SQRT:
				ComplexNums in = stack.pop();
				stack.push((ComplexNums) in.sqrt());
				break;

			case EXPONENT:
				ComplexNums exponent = stack.pop();
				ComplexNums base = stack.pop();
				stack.push((ComplexNums) base.exponentiate(exponent));
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
		case LOG:
		case SQRT:
			return 5;
		case NEGATE:
			return 4;
		case EXPONENT:
			return 3;
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
		return evaluated.toString();
	}
}
