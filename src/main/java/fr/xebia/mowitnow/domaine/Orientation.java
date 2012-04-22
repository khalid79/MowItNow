package fr.xebia.mowitnow.domaine;

/**
 * Orientation de la tondeuse.
 */
public enum Orientation {

	/**
	 * nord, east , west , south.
	 */
	N("NORD", 0), E("EAST", 90), W("WEST", 270), S("SOUTH", 180);

	/**
	 * label de la direction.
	 */
	private String label;

	/**
	 * degré pour faire la rotation.
	 */
	private int degre;

	/**
	 * constructeur de l'orientation.
	 * 
	 * @param label
	 *            label
	 * @param degre
	 *            degre
	 */
	private Orientation(String label, int degre) {
		this.label = label;
		this.degre = degre;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the degre
	 */
	public int getDegre() {
		return degre;
	}

}
