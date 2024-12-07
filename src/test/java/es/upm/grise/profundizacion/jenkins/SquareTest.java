package es.upm.grise.profundizacion.jenkins;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import es.upm.grise.profundizacion.jenkins.Square;
public class SquareTest {

	@Test
	public void test() throws IncorrectSideLengthException {
		float SIDE = 10;
		float AREA = 100;
		
		Square s = new Square(SIDE);
		assertEquals(AREA, s.getArea(), 0.001);
	}

}
