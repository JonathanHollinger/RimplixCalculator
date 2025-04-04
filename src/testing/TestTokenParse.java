package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestTokenParse
{

  @Test
  void testCalculate()
  {
    String equation = "5 + 4";
    //assertEquals(9, calculate(equation));
    String eqTwo = "3 * (4 + 1)";
    //assertEquals(15, calculate(eqTwo));
    String eqThree = "5 / 2";
    //assertEquals(2.5, calculate(eqThree));
    fail("Not yet implemented");
  }

}
