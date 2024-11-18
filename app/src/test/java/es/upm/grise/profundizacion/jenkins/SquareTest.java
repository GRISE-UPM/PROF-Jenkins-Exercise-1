package es.upm.grise.profundizacion.jenkins;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SquareTest {

	@Test
	public void test() throws IncorrectSideLengthException {
		float SIDE = 10;
		float AREA = 100;
		
		Square s = new Square(SIDE);
		assertEquals(AREA, s.getArea(), 0.001);
	}

}
