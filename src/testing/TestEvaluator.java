package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

import utilities.ComplexNums;
import utilities.arithmetic.Evaluator;
import utilities.arithmetic.Parser;
import utilities.arithmetic.Token;

public class TestEvaluator
{
	@Test
	public void testSimpleAdditionAndMultiplication()
	{
		String expr = "4 + 2 x 3"; // Should evaluate to 10
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(0.0, result.getIConst(), 0.0001); // Ensure no imaginary part
			assertEquals(10.0, result.getVal(), 0.0001); // 2 * 3 = 6 + 4 = 10
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during evaluation";
		}
	}

	@Test
	public void testParentheses()
	{
		String expr = "(4 + 2) x 3"; // Should evaluate to 18
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(0.0, result.getIConst(), 0.0001);
			assertEquals(18.0, result.getVal(), 0.0001);
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during evaluation";
		}
	}

	@Test
	public void testNegativeAndDivision()
	{
		String expr = "(8 - 2) / 3"; // Should evaluate to 2
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(0.0, result.getIConst(), 0.0001);
			assertEquals(2.0, result.getVal(), 0.0001);
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during evaluation";
		}
	}

	@Test
	public void testComplexMultiplication()
	{
		String expr = "(1 + 2i) x (3 + 4i)"; // Should evaluate to -5 + 10i
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(10.0, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(-5.0, result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	@Test
	public void testComplexAddition()
	{
		String expr = "(11 + 3i) - (6 - 2i)"; // Should evaluate to -5 + 10i
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(5.0, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(5.0, result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	@Test
	public void testIOnly()
	{
		String expr = "5i - 2i"; // Should evaluate to -5 + 10i
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(3.0, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(0.0, result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	@Test
	public void testComplexEquation()
	{
		String expr = "(1 x 2i) x (6 - 3i)"; // Should evaluate to 6 + 12i
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(12.0, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(6.0, result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

}
