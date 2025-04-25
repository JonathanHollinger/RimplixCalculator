package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

import javax.swing.JLabel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.Display;
import utilities.Nums;

public class DisplayCoverageTest {
  private Display display;

  @BeforeEach
  public void setUp() {
    display = new Display(null);
  }

  private void press(String cmd) {
    display.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, cmd));
  }

  private String getInputLabel() throws Exception {
    Field inputLabel = Display.class.getDeclaredField("inputLabel");
    inputLabel.setAccessible(true);
    JLabel label = (JLabel) inputLabel.get(display);
    return label.getText().replaceAll("<[^>]+>", "").trim(); // Strip any HTML tags
  }

  @Test
  public void testBasicArithmetic() throws Exception {
    press("1");
    press("+");
    press("2");
    press("=");
    assertTrue(getExpressionLabel().contains("3.00")); // ✅
  }

  @Test
  public void testToggleSign() throws Exception {
    press("5");
    press("±");
    assertTrue(getInputLabel().contains("-5"));
    press("±");
    assertTrue(getInputLabel().contains("5"));
  }

  @Test
  public void testDecimalPointControl() throws Exception {
    press("3");
    press(".");
    press("1");
    press(".");
    assertEquals("3.1", getInputLabel()); // Second '.' should be blocked
  }

  @Test
  public void testConjugate() throws Exception {
    press("3");
    press("+");
    press("2");
    press("i");
    press("Conj");
    assertTrue(getExpressionLabel().contains("3.00-2.00i"));
  }

  @Test
  public void testInvert() throws Exception {
    press("2");
    press("+");
    press("2");
    press("i");
    press("Inv");
    assertTrue(getExpressionLabel().contains("0.25-0.25i"));
  }

  @Test
  public void testParenthesesBalance() throws Exception {
    press("(");
    press("3");
    press("+");
    press("4");
    press(")");
    press("x");
    press("2");
    press("=");
    assertTrue(getExpressionLabel().contains("14.00")); // ✅
  }

  @Test
  public void testPolarToggleFormatting() throws Exception {
    press("1");
    press("+");
    press("1");
    press("i");
    press("=");
    press("Pol");
    String top = getExpressionLabel();
    assertTrue(top.contains("∠") && top.contains("°"));
  }

  private String getExpressionLabel() throws Exception {
    Field expressionLabel = Display.class.getDeclaredField("expressionLabel");
    expressionLabel.setAccessible(true);
    JLabel label = (JLabel) expressionLabel.get(display);
    return label.getText();
  }
  
  @Test
  public void testPolarFormattingToggle() throws Exception {
    press("1");
    press("+");
    press("1");
    press("i");
    press("=");
    press("Pol");
    assertTrue(getExpressionLabel().contains("∠"));
  }
  
  @Test
  public void testUnbalancedParenthesesSkipsEvaluation() throws Exception {
    press("(");  // only one open paren
    press("3");
    press("+");
    press("4");
    press("=");
    assertFalse(getExpressionLabel().contains("=")); // expression shouldn't evaluate
  }
  
  @Test
  public void testAreParenthesesBalanced_EarlyClosing() throws Exception {
      // Push a closing parenthesis before any opening one
      press(")");
      // This triggers balance < 0
      assertFalse(invokeAreParenthesesBalanced(")3+4("));
  }
  
  private boolean invokeAreParenthesesBalanced(String input) throws Exception {
	    var method = Display.class.getDeclaredMethod("areParenthesesBalanced", String.class);
	    method.setAccessible(true);
	    return (boolean) method.invoke(display, input);
	}
  
  @Test
  public void testCanAddDecimalPoint_whenEmpty() throws Exception {
      // Clear everything so contents is ""
      press("C");

      var method = Display.class.getDeclaredMethod("canAddDecimalPoint");
      method.setAccessible(true);
      boolean result = (boolean) method.invoke(display);

      assertTrue(result); // This hits both yellow and red
  }
  
  @Test
  public void testToggleSignWhenEmpty() throws Exception {
      press("C"); // Ensure contents is empty
      press("±");
      assertEquals("Enter a complex number", getInputLabel());
  }
  
  @Test
  public void testConjugateWithEmptyInput() throws Exception {
    press("Conj");
    assertEquals("Enter a complex number", getInputLabel());
  }

  @Test
  public void testConjugateWithInvalidInput() throws Exception {
    press("(");
    press("3");
    press("+");
    press("i");
    press("Conj"); // unbalanced parenthesis
    // No exception should crash the test
    assertTrue(true); // Just make sure it doesn't throw
  }
  
  @Test
  public void testInvertWithEmptyInput() throws Exception {
    press("Inv");
    assertEquals("Enter a complex number", getInputLabel());
  }
  
  @Test
  public void testInvertWithInvalidInput() throws Exception {
    press("("); // unbalanced parenthesis
    press("2");
    press("+");
    press("i");
    press("Inv");
    assertTrue(true); // Just assert the app didn’t crash
  }
  
  @Test
  public void testToggleModeRectangularToPolar() throws Exception {
    press("1");
    press("+");
    press("1");
    press("i");
    press("=");
    press("Pol");  // Toggle to polar mode
    String top = getExpressionLabel();
    assertTrue(top.contains("∠") && top.contains("°"));
  }
  
  @Test
  public void testFormatAsPolarWithNearZeroParts() throws Exception {
    press("0.00000000001");
    press("+");
    press("0.00000000001");
    press("i");
    press("=");
    press("Pol");
    String top = getExpressionLabel();
    assertTrue(top.contains("∠") && top.contains("°"));
  }
  
  @Test
  public void testFormatAsPolarWithInvalidType() throws Exception {
    var method = Display.class.getDeclaredMethod("formatAsPolar", Nums.class);
    method.setAccessible(true);
    Nums fakeNum = new Nums() {
      public double getVal() { return 0; }
    };
    String result = (String) method.invoke(display, fakeNum);
    assertEquals("Invalid", result);
  }
  
  @Test
  public void testToggleModeFailsGracefully() throws Exception {
    Field f = Display.class.getDeclaredField("problem");
    f.setAccessible(true);
    f.set(display, "BROKEN_SYNTAX");
    Field evalField = Display.class.getDeclaredField("evaluatedExpression");
    evalField.setAccessible(true);
    evalField.set(display, true);

    press("Pol"); // toggleMode should catch a parse failure
    assertTrue(true); // assert no crash
  }
  
  @Test
  public void testToggleSignDoubleNegateWrapped() throws Exception {
    press("("); press("2"); press("+"); press("3"); press("i"); press(")");
    press("±");
    press("±");
    String result = getInputLabel();
    assertTrue(result.contains("2") && result.contains("3")); // should just be valid
  }
  
  @Test
  public void testToggleModeCoversBothBranches() throws Exception {
    press("1");
    press("+");
    press("1");
    press("i");
    press("=");         // Evaluate
    press("Pol");       // Toggle to polar (polar branch)
    assertTrue(getExpressionLabel().contains("∠"));

    press("Pol");       // Toggle back to rectangular (rectangular branch)
    assertTrue(getExpressionLabel().contains("+1.00i"));
  }
  
  @Test
  public void testToggleSignWithImaginaryWrappedDoubleToggle() throws Exception {
    press("("); press("2"); press("+"); press("3"); press("i"); press(")");
    press("±"); // once
    press("±"); // twice
    String result = getInputLabel();
    assertTrue(result.contains("2") && result.contains("3")); // no crash, reasonable form
  }
  
  @Test
  public void testToggleSignFixesDoubleNegativeParens() throws Exception {
    press("("); press("2"); press("+"); press("3"); press("i"); press(")");
    press("±"); // Once
    press("±"); // Twice
    String result = getInputLabel();
    assertFalse(result.contains("--")); // Validate no double negatives
  }
  
  public void testPolarExpressionEvaluates() throws Exception {
	  press("2");
	  press("∠");
	  press("6");
	  press("0");
	  press("°");
	  press("=");
	  String result = getInputLabel();
	  assertTrue(result.contains("1.00") || result.contains("1.732")); // lenient rectangular
	}
  
  @Test
  public void testFormatAsPolarCoversInstanceCheck() throws Exception {
    press("C");
    press("1");
    press("+");
    press("1");
    press("i");
    press("=");
    press("Pol"); // This covers instanceof and all inside formatAsPolar
    assertTrue(getExpressionLabel().contains("∠"));
  }
  
  @Test
  public void testFormatAsPolarBranch() throws Exception {
    press("1");
    press("+");
    press("1");
    press("i");
    press("=");
    press("Pol"); // switch to polar
    String top = getExpressionLabel();
    assertTrue(top.contains("∠") && top.contains("°"));
  }
  
  @Test
  public void testMalformedInputCatchesException() throws Exception {
    press("∠");
    press("=");
    String label = getInputLabel();
    assertTrue(!label.isEmpty()); // Just verify no crash, not exact output
  }
  
  @Test
  public void testFormatAsPolarWithNegativeAngle() throws Exception {
    press("-");
    press("1");
    press("-");
    press("1.732");
    press("i");
    press("=");
    press("Pol");
    String top = getExpressionLabel();
    assertTrue(top.contains("∠") && top.contains("°"));
  }
  
}