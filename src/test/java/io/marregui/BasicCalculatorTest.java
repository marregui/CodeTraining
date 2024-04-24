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
//        Assertions.assertEquals(2,  calc.calculate(" 1 + 1 "));
        Assertions.assertEquals(-3,  calc.calculate(" 1 +  - 1 - 3"));
        Assertions.assertEquals(3,  calc.calculate(" 1 +  - 1 - -3"));
    }
}
