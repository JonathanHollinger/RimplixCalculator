package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

import utilities.arithmetic.Parser;
import utilities.arithmetic.Token;

class TestTokenParse
{

//	@Test
//	void testCalculate()
//	{
//		String equation = "5 + 4";
//		// assertEquals(9, calculate(equation));
//		String eqTwo = "3 * (4 + 1)";
//		// assertEquals(15, calculate(equation));
//		String eqThree = "5 / 2";
//		// assertEquals(2.5, calculate(equation));
//		fail("Not yet implemented");
//	}

	@Test
	void testParse() throws IOException
	{
		// This will parse the expression: (42 + 3)
		String input = "(42 + 3)";
		BufferedReader in = new BufferedReader(new StringReader(input));

		List<Token> tokens = Parser.parse(in);

		assertEquals(5, tokens.size());

		assertEquals(Parser.Expression.LEFT_PAREN, tokens.get(0).type);
		assertEquals(Parser.Expression.NUMBER, tokens.get(1).type);
		assertEquals("42", tokens.get(1).value);
		assertEquals(Parser.Expression.ADD, tokens.get(2).type);
		assertEquals(Parser.Expression.NUMBER, tokens.get(3).type);
		assertEquals("3", tokens.get(3).value);
		assertEquals(Parser.Expression.RIGHT_PAREN, tokens.get(4).type);
	}

}
