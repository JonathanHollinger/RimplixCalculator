package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import utilities.ComplexNums;

public class TestNegativeParts {

    @Test
    public void testAdditionWithNegativeParts() {
        ComplexNums a = new ComplexNums(-3, -4);
        ComplexNums b = new ComplexNums(2, 5);
        assertEquals("-1.0+1.0i", a.add(b).toString());
    }

    @Test
    public void testSubtractionWithNegativeParts() {
        ComplexNums a = new ComplexNums(-3, -4);
        ComplexNums b = new ComplexNums(2, 5);
        assertEquals("-5.0-9.0i", a.sub(b).toString());
    }

    @Test
    public void testMultiplicationWithNegativeParts() {
        ComplexNums a = new ComplexNums(-3, -4);
        ComplexNums b = new ComplexNums(2, 5);
        assertEquals("14.0-23.0i", a.mult(b).toString());
    }

    @Test
    public void testDivisionWithNegativeParts() {
        ComplexNums a = new ComplexNums(-3, -4);
        ComplexNums b = new ComplexNums(2, 5);
        ComplexNums result = (ComplexNums) a.div(b);
        assertEquals(-0.90, result.getVal(), 0.05);
        assertEquals(0.24, result.getIConst(), 0.01);     
    }
}
