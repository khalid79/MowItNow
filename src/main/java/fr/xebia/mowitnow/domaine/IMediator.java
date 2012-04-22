package fr.xebia.mowitnow.domaine;

/**
 * Interface du m�diateur. 
 * Le m�diateur est le seul composant � pouvoir communiquer avec toutes les Tondeuse.
 * Pour communiquer avec les autres tondeuse, chaque tondeuse peut utiliser l'interface du m�diateur.
 * @author kbouaabd
 *
 */
public interface IMediator {
	
	/**
	 * Permet de notifier la position d'une tondeuse.
	 * @param position position
	 * @param tondeuse la tondeuse en question
	 */
	public void send(Position position, Tondeuse tondeuse);	
}