package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import utilities.ComplexNums;

public class TestConjugate {

    @Test
    public void testPositiveImaginary() {
        ComplexNums c = new ComplexNums(3, 4);
        ComplexNums conj = c.conjugate();
        assertEquals("3.0-4.0i", conj.toString());
    }

    @Test
    public void testNegativeImaginary() {
        ComplexNums c = new ComplexNums(3, -4);
        ComplexNums conj = c.conjugate();
        assertEquals("3.0+4.0i", conj.toString());
    }

    @Test
    public void testPureImaginary() {
        ComplexNums c = new ComplexNums(0, 5);
        ComplexNums conj = c.conjugate();
        assertEquals("-5.0i", conj.toString());
    }

    @Test
    public void testPureReal() {
        ComplexNums c = new ComplexNums(7, 0);
        ComplexNums conj = c.conjugate();
        assertEquals("7.0", conj.toString());
    }
}