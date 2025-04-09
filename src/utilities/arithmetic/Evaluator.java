package utilities.arithmetic;

import java.util.ArrayList;
import java.util.List;

import utilities.ComplexNums;

public class Evaluator
{
	/*
	 * List of Tokens which will be Evaluated.
	 * 
	 * Token list can be retrieved from the Parse function in the Parser class.
	 * Tokens represent values such as number, left parenthesis, right parenthesis,
	 * add, etc.
	 */
	private List<Token> tokens;

	// Evaluated answer of the evaluate process.
	private ComplexNums evaluated;

	public Evaluator(List<Token> tokens)
	{
		this.tokens = tokens;
		evaluated = evaluate();
	}

	public ComplexNums result()
	{
		return evaluated;
	}

	public ComplexNums evaluate()
	{
		ComplexNums result = new ComplexNums();

		ComplexNums currentValue = new ComplexNums();
		Parser.Expression currentOperation = null;

		for (int i = 0; i < tokens.size(); i++)
		{
			Token tok = tokens.get(i);

			switch (tok.type)
			{
			case Parser.Expression.LEFT_PAREN:
				// Handle sub-expression inside parentheses
				// For simplicity, recursively call evaluate on the tokens inside
				// parentheses
				// (You would need to handle nested parentheses properly)
				i++; // Skip the opening parenthesis
				List<Token> subExpression = new ArrayList<>();
				while (tokens.get(i).type != Parser.Expression.RIGHT_PAREN)
				{
					subExpression.add(tokens.get(i));
					i++;
				}
				// Now evaluate the sub-expression
				Evaluator subEvaluator = new Evaluator(subExpression);
				currentValue = subEvaluator.result();
				break;

			case Parser.Expression.NUMBER:
				// Handle number token
				currentValue = new ComplexNums(Integer.parseInt(tok.value));
				break;

			case Parser.Expression.ADD:
			case Parser.Expression.SUBTRACT:
			case Parser.Expression.MULTIPLY:
			case Parser.Expression.DIVIDE:
				// If there is a pending operation, evaluate it with the current value
				if (currentOperation != null)
				{
					result = calculate(result, currentValue, currentOperation);
				}
				// Set the new operation
				currentOperation = tok.type;
				break;

			case Parser.Expression.RIGHT_PAREN:
				// Handle closing parenthesis (you already processed it inside the
				// parenthesis)
				break;

			default:
				// Unhandled case (e.g., unknown token type)
				break;
			}
		}
		return result;
	}

	public ComplexNums calculate(ComplexNums result, ComplexNums cur,
			Parser.Expression op)
	{
		return new ComplexNums(); // [TEMPORARY] Remove this when method is complete.
	}

	public String toString()
	{
		return "Result of Evaluation: " + evaluated.toString();
	}
}