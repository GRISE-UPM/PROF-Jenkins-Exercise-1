package es.upm.grise.profundizacion.jenkins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class SquareTest {

    @Test
    public void test() throws IncorrectSideLengthException {
        float SIDE = 10;
        float AREA = 100;

        Square s = new Square(SIDE);
        assertEquals(AREA, s.getArea(), 0.001);
    }

    @Test
    public void testWrongArea() throws IncorrectSideLengthException {
        float SIDE = -10;
        assertThrows(IncorrectSideLengthException.class, () -> new Square(SIDE));
    }

}
