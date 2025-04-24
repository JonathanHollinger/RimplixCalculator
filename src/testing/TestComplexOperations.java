package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import utilities.ComplexNums;

class TestComplexOperations
{

	@Test
	void testExponentiate()
	{
		ComplexNums val1 = new ComplexNums(2.0, 0.0);
		ComplexNums val2 = new ComplexNums(5.0, 0.0);

		ComplexNums result = (ComplexNums) val1.exponentiate(val2);

		assertEquals(32.0, result.getVal(), 0.0001);
		assertEquals(0.0, result.getIConst(), 0.0001);
	}

	@Test
	void testLog()
	{
		ComplexNums val1 = new ComplexNums(8.0, 0.0);
		ComplexNums val2 = new ComplexNums(2.0, 0.0);

		ComplexNums result = (ComplexNums) val1.log(val2);

		assertEquals(3.0, result.getVal(), 0.0001);
		assertEquals(0.0, result.getIConst(), 0.0001);
	}

	void testSqrt()
	{
		ComplexNums val1 = new ComplexNums(16.0, 0.0);

		ComplexNums result = (ComplexNums) val1.sqrt();

		assertEquals(4.0, result.getVal(), 0.0001);
		assertEquals(0.0, result.getIConst(), 0.0001);
	}

}
