package fr.xebia.mowitnow.domaine;

/**
 * Position de la tondeuse.
 * @author kbouaabd
 *
 */
public class Position {

	/**
	 * position axe : x .
	 */
	private int x;

	/**
	 * position axe : y .
	 */
	private int y;

	/**
	 * constructeur de la position.
	 */
	public Position() {
		
	}
	
	/**
	 * constructeur de la position.
	 * @param x x
	 * @param y y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

}
