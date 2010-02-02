/**
 * 
 */
package input;

/**
 * @author Markus
 * This is an Element that is to be clustered. 
 *
 */
public class Element {

	protected int label;
	/**
	 * This is an id given to the Element. It is supposed to be unique and 
	 * should identifie the vector position in a distance matrix 
	 */
	private long id;

	/**
	 * The label that this vector has
	 * @return
	 */
	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

}
