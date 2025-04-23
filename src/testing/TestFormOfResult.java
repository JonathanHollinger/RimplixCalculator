package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import utilities.ComplexNums;

public class TestFormOfResult {

    @Test
    public void testRealOnly() {
        ComplexNums c = new ComplexNums(5, 0);
        assertEquals("5.00", c.formattedString());
    }

    @Test
    public void testImaginaryOnlyPositive() {
        ComplexNums c = new ComplexNums(0, 3);
        assertEquals("3.00i", c.formattedString());
    }

    @Test
    public void testImaginaryOnlyNegative() {
        ComplexNums c = new ComplexNums(0, -4);
        assertEquals("-4.00i", c.formattedString());
    }

    @Test
    public void testRealAndImaginaryPositive() {
        ComplexNums c = new ComplexNums(2, 5);
        assertEquals("2.00+5.00i", c.formattedString());
    }

    @Test
    public void testRealAndImaginaryNegative() {
        ComplexNums c = new ComplexNums(2, -5);
        assertEquals("2.00-5.00i", c.formattedString());
    }

    @Test
    public void testBothZero() {
        ComplexNums c = new ComplexNums(0, 0);
        assertEquals("0.00", c.formattedString());
    }

    @Test
    public void testDecimalRoundingRealOnly() {
        ComplexNums c = new ComplexNums(3.14159, 0);
        assertEquals("3.14", c.formattedString());
    }

    @Test
    public void testDecimalRoundingImaginaryOnly() {
        ComplexNums c = new ComplexNums(0, -2.71828);
        assertEquals("-2.72i", c.formattedString());
    }

    @Test
    public void testDecimalRoundingFullComplex() {
        ComplexNums c = new ComplexNums(4.567, -3.1415);
        assertEquals("4.57-3.14i", c.formattedString());
    }
}