/**
 * 
 */
package distance;

/**
 * @author Markus
 *
 */
public interface DistanceMeasure {
	
	/**
	 * This method returns the distance between to points of arbitrary dimension.
	 * Both points must be of the same length.
	 * @param vect1 this is the first point
	 * @param vect2 this is the second point
	 * @return distance between the two poins
	 */
	public float calculate (float[] vect1, float[] vect2);

}
