package util;

public class CalculationUtil {
	
	/**
	 * Returns the zerobased index of the minimal element of the collection
	 * @param elements a collection of floating point numbers
	 * @return the index of the smallest element in the collection
	 */
	public static int getIndexOfMinElement(Float[] elements){
		float min = elements[0];
		int index = 0;
		for (int i = 1; i < elements.length; i++) {
			if (elements[i] < min){
				min = elements[i];
				index = i;
			}
		}
		return index;
	}
	
	public static float getMaxElement(float[] elements){
		float max = elements[0];
		for (int i = 1; i < elements.length; i++) {
			if (elements[i]>max ){
				max = elements[i];
			}
		}
		return max;
	}
	
	/**
	 * returns the maximum element of an int array
	 * @param elements
	 * @return
	 */
	public static int getMaxElement(int[] elements){
		int max = elements[0];
		for (int i = 1; i < elements.length; i++) {
			if (elements[i]>max ){
				max = elements[i];
			}
		}
		return max;
	}
	
	/**
	 * Adds to vectors together. If their Lenth is not equal throw an exception
	 * @param point1
	 * @param point2
	 * @return
	 */
	public static float[] vectorAddition(float[] point1, float[]point2){
		float [] result = new float[point1.length];
		if (point1.length != point2.length){
			throw new  ArithmeticException();
		}
		for (int i = 0; i < point1.length; i++) {
			result[i] = point1[i] + point2[i];
		}
		return result;
	}
	
	public static float[] scalarMultiplication (int lambda,float[] point){
		for (int i = 0; i < point.length; i++) {
			point [i] = point[i] * lambda;
		}
		return point;
	}

	/**
	 * @param d
	 * @param sum
	 * @return
	 */
	public static float[] scalarMultiplication(float lambda, float[] point) {
		for (int i = 0; i < point.length; i++) {
			point [i] = point[i] * lambda;
		}
		return point;
	}

}
