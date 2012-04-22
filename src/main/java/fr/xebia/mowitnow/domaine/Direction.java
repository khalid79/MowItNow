package fr.xebia.mowitnow.domaine;

/**
 * Les directions possibles pour la tendeuse.
 */
public enum Direction {

	/**
	 * Droite, gauche, et en avant.
	 */
	D("DROITE", 90), G("GAUCHE", -90), A("AVANCER", 0);

	/**
	 * label de la direction.
	 */
	private String label;

	/**
	 * degré pour faire la rotation.
	 */
	private int degre;

	/**
	 * constructeur de la direction.
	 * 
	 * @param label
	 *            label
	 * @param degre
	 *            degre
	 */
	private Direction(String label, int degre) {
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