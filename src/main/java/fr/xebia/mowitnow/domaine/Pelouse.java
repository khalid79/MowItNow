package fr.xebia.mowitnow.domaine;

/**
 * Représente une pelouse.
 * @author KBOUAABD
 * 
 */
public class Pelouse {

	/**
	 * largeur de la pelouse.
	 */
	private int largeur;

	/**
	 * hauteur de la pelouse.
	 */
	private int hauteur;
	
	/**
	 * Constructeur de la pelouse.
	 */
	public Pelouse() {
	}

	/**
	 * Constructeur de la pelouse.
	 * @param largeur largeur
	 * @param hauteur  hauteur
	 */
	public Pelouse(int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	/**
	 * @return the largeur
	 */
	public int getLargeur() {
		return largeur;
	}

	/**
	 * @param largeur
	 *            the largeur to set
	 */
	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	/**
	 * @return the hauteur
	 */
	public int getHauteur() {
		return hauteur;
	}

	/**
	 * @param hauteur
	 *            the hauteur to set
	 */
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

}
