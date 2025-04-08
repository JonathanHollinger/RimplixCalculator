package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import utilities.Check;

class CheckTest
{

  @Test
  public void testEmptyString() {
      assertFalse(Check.isValid(""), "Empty string should be invalid");
  }

  @Test
  public void testSinglePairParentheses() {
      assertTrue(Check.isValid("()"));
  }

  @Test
  public void testSinglePairBrackets() {
      assertTrue(Check.isValid("[]"));
  }

  @Test
  public void testSinglePairBraces() {
      assertTrue(Check.isValid("{}"));
  }

  @Test
  public void testMismatchedPairs() {
      assertFalse(Check.isValid("(]"));
      assertFalse(Check.isValid("[}"));
      assertFalse(Check.isValid("{)"));
  }

  @Test
  public void testUnmatchedOpenings() {
      assertFalse(Check.isValid("("));
      assertFalse(Check.isValid("[["));
      assertFalse(Check.isValid("{{{"));
  }

  @Test
  public void testUnmatchedClosings() {
      assertFalse(Check.isValid(")"));
      assertFalse(Check.isValid("]]"));
      assertFalse(Check.isValid("}}}"));
  }

  @Test
  public void testNestedValid() {
      assertTrue(Check.isValid("({[]})"));
      assertTrue(Check.isValid("(([]))"));
  }

  @Test
  public void testNestedInvalid() {
      assertFalse(Check.isValid("({[})"));
      assertFalse(Check.isValid("([)]"));
  }

  @Test
  public void testOddLength() {
      assertFalse(Check.isValid("(()"));
      assertFalse(Check.isValid("{[()]}("));
  }
}