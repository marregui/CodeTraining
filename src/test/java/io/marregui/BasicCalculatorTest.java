package io.marregui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasicCalculatorTest {

    private BasicCalculator calc;

    @BeforeEach
    public void setUp() {
        calc = new BasicCalculator();
    }



    @Test
    public void testSimple() {
        Assertions.assertEquals(6,  calc.calculate(" ((1) + (1 + 1 ) --3)"));
        Assertions.assertEquals(2,  calc.calculate(" (1 + 1 )"));
        Assertions.assertEquals(2,  calc.calculate(" 1 + 1 "));
        Assertions.assertEquals(-3,  calc.calculate(" 1 +  - 1 - 3"));
        Assertions.assertEquals(3,  calc.calculate(" 1 +  - 1 - -3"));
        Assertions.assertEquals(2147483647,  calc.calculate("2147483647"));
        Assertions.assertEquals(-12,  calc.calculate("- (3 + (4 + 5))"));
        Assertions.assertEquals(-12,  calc.calculate("- (3 - (- (4 + 5) ) )"));
        Assertions.assertEquals(-2,  calc.calculate("- (3 - - (-1))"));
    }
}
