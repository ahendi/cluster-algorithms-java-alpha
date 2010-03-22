/**
 * 
 */
package input;

/**
 * @author Markus
 * This is an Element that is to be clustered. It defines all the functions that 
 * Elements that theses algorithms can cluster must support
 *
 */
public abstract class Element {

	public static final int UNCLASSIFIED = -2;
	public static final int NOISE = -1;
	protected int label;
	/**
	 * This is an id given to the Element. It is supposed to be unique and 
	 * should identify the vector position in a distance matrix 
	 */
	protected int id;
	protected int calculatedClusternumber;


	/**
	 * 
	 */
	public Element(int id) {
		this.id =id;
	}

	/**
	 * The label that this vector has.
	 * @return
	 */
	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public int getCalculatedClusternumber() {
		return calculatedClusternumber;
	}

	public void setCalculatedClusternumber(int calculatedClusternumber) {
		this.calculatedClusternumber = calculatedClusternumber;
	}
	
	/**
	 * Calculates the distance to the other element using a standard distance measure
	 * @param other
	 * @return
	 */
	public abstract float calculateDistance (Element other);

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	} 

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
