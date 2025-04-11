package testing;

import utilities.ComplexNums;
import utilities.Nums;
import utilities.SimpleNums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestComplexMult
{

  @Test
  void testMultComplex()
  {
    ComplexNums test = new ComplexNums(3, 2);
    
    ComplexNums expected = new ComplexNums(5, 12);
    
    Nums a = test.mult(test);
    
    ComplexNums actual = (ComplexNums) a;
    
    
    
    assertEquals(expected.getVal(), actual.getVal());
    assertEquals(expected.getMult(), actual.getMult());
  }
  
//  @Test
//  void testSimpleMult()
//  {
//    ComplexNums test = new ComplexNums(3, 2);
//    SimpleNums simp = new SimpleNums(2);
//    
//    ComplexNums expected = new ComplexNums(6, 4);
//    
//    Nums a = test.mult(simp);
//    
//    ComplexNums actual = (ComplexNums) a;
//    
//    
//    
//    assertEquals(expected.getVal(), actual.getVal());
//    assertEquals(expected.getMult(), actual.getMult());
//  }

}
