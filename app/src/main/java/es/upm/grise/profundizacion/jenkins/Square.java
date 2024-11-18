package es.upm.grise.profundizacion.jenkins;

public class Square {

	float sideLength;

	public Square(float sideLength) throws IncorrectSideLengthException {
		
		if(sideLength <= 0) {
			throw new IncorrectSideLengthException();
		} else {
			this.sideLength = sideLength;
		}
	}

	
	
	public float getArea() {

		return sideLength * sideLength;
	
	}
	
	
}
