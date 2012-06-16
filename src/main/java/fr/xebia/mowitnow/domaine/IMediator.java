package fr.xebia.mowitnow.domaine;

/**
 * Interface du médiateur. 
 * Le médiateur est le seul composant à pouvoir communiquer avec toutes les Tondeuse.
 * Pour communiquer avec les autres tondeuse, chaque tondeuse peut utiliser l'interface du médiateur.
 * @author kbouaabd
 *
 */
public interface IMediator {
	
	/**
	 * Permet de notifier la position d'une tondeuse.
	 * @param tondeuse la tondeuse en question
	 */
	public void notifierPosition(Tondeuse tondeuse);
	
	/**
	 * Permet un obstacle : la dernière position d'une tondeuse.
	 * @param tondeuse la tondeuse en question
	 */
	public void notifierObstacle(Tondeuse tondeuse);	
}
