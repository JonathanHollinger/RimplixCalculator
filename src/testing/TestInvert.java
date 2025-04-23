package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import utilities.ComplexNums;

public class TestInvert {

    @Test
    public void testInvertRealOnly() {
        ComplexNums c = new ComplexNums(4, 0);
        ComplexNums inv = c.invert();
        assertEquals(0.25, inv.getVal(), 0.001);
        assertEquals(0.0, inv.getIConst(), 0.001);
    }

    @Test
    public void testInvertImaginaryOnly() {
        ComplexNums c = new ComplexNums(0, 2);
        ComplexNums inv = c.invert();
        assertEquals(0.0, inv.getVal(), 0.001);
        assertEquals(-0.5, inv.getIConst(), 0.001);
    }

    @Test
    public void testInvertComplex() {
        ComplexNums c = new ComplexNums(3, 4);
        ComplexNums inv = c.invert();
        assertEquals(0.12, inv.getVal(), 0.01);     // 3 / 25 = 0.12
        assertEquals(-0.16, inv.getIConst(), 0.01); // -4 / 25 = -0.16
    }
}