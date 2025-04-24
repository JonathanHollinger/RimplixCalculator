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
		String expr = "(8 - 2) ÷ 3"; // Should evaluate to 2
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

	@Test
	public void testNegative()
	{
		String expr = "(-3 + 1) - 2"; // Should evaluate to 6 + 12i
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(0.0, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(-4.0, result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	// ---- EXPONENTIATION ----

	@Test
	public void testRealExponentiation() throws Exception
	{
		String expr = "2 ^ 5";
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(0.0, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(32.0, result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	@Test
	public void testComplexToRealExponentiation() throws Exception
	{
		String expr = "(1 + i) ^ 2";
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(2.0, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(0.0, result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	@Test
	public void testComplexToComplexExponentiation() throws Exception
	{
		String expr = "i ^ i";
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(0.0, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(Math.exp(-Math.PI / 2), result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	// ---- SQUARE ROOT ----

	@Test
	public void testSqrtReal() throws Exception
	{
		String expr = "sqrt(16)";
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(0.0, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(4.0, result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	@Test
	public void testSqrtComplex() throws Exception
	{
		String expr = "sqrt(i)";
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(Math.sqrt(2) / 2, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(Math.sqrt(2) / 2, result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	// ---- LOGARITHMS ----

	@Test
	public void testNaturalLogReal() throws Exception
	{
		String expr = "log(10)";
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(0.0, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(Math.log(10), result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	@Test
	public void testNaturalLogComplex() throws Exception
	{
		String expr = "log(i)";
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(Math.PI / 2, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(0.0, result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	@Test
	public void testLogBaseReal() throws Exception
	{
		String expr = "log(8, 2)";
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			assertEquals(0.0, result.getIConst(), 0.0001); // Imaginary part
			assertEquals(3.0, result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}

	@Test
	public void testLogBaseComplex() throws Exception
	{
		String expr = "log(i, 1 + i)";
		BufferedReader reader = new BufferedReader(new StringReader(expr));

		try
		{
			List<Token> tokens = Parser.parse(reader);
			Evaluator evaluator = new Evaluator(tokens);
			ComplexNums result = evaluator.result();

			ComplexNums ln1 = new ComplexNums(0, Math.PI / 2);
			ComplexNums ln2 = new ComplexNums(Math.log(Math.hypot(1, 1)), Math.PI / 4);
			ComplexNums expected = (ComplexNums) ln1.div(ln2);

			assertEquals(expected.getIConst(), result.getIConst(), 0.0001); // Imaginary
																			// part
			assertEquals(expected.getVal(), result.getVal(), 0.0001); // Real part
		} catch (Exception e)
		{
			e.printStackTrace();
			assert false : "Exception occurred during complex number evaluation";
		}
	}
}