package fr.xebia.mowitnow.presentation;

import fr.xebia.mowitnow.domaine.Position;

/**
 * Message envoyer vers l'ihm.
 * @author kbouaabd
 *
 */
public class MessageTondeuse {
	
	/**
	 * idTondeuse.
	 */
	private String idTondeuse;
	
	/**
	 * position .
	 */
	private Position position;
	
	/**
	 * Constructeur.
	 */
	public MessageTondeuse() {
	}
	
	/**
	 * Constructeur.
	 * @param idTondeuse idTondeuse
	 * @param position position
	 */
	public MessageTondeuse(String idTondeuse, Position position) {
		super();
		this.idTondeuse = idTondeuse;
		this.position = position;
	}


	/**
	 * @return the idTondeuse
	 */
	public String getIdTondeuse() {
		return idTondeuse;
	}

	/**
	 * @param idTondeuse the idTondeuse to set
	 */
	public void setIdTondeuse(String idTondeuse) {
		this.idTondeuse = idTondeuse;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	
	


}
