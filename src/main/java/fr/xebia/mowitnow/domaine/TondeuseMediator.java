package fr.xebia.mowitnow.domaine;

import java.util.List;
import java.util.Observable;
import fr.xebia.mowitnow.presentation.MessageTondeuse;

/**
 * Le médiateur et l'orchestrateur de communication entre les tondeuse.
 * Initialise est démarre toutes les tondeuses.
 * 
 * @author kbouaabd
 * 
 */
public class TondeuseMediator extends Observable implements IMediator {

	/**
	 * la liste des tondeuses.
	 */
	private List<Tondeuse> listeTondeuses;
	
	/**
	 * pseudo.
	 * TODO: Pas une si bonne idée d'ajouter le pseudo au mediateur.
	 */
	private String pseudo;
	
	/**
	 * Constructeur du médiateur.
	 */
	public TondeuseMediator(){
		
	}

	/**
	 * Constructeur du médiateur.
	 * @param listeTondeuses connues par le médiator
	 * @param pseudo permet d'envoyer le message vers le bon consomateur
	 */
	public TondeuseMediator(List<Tondeuse> listeTondeuses, String pseudo) {
		this.listeTondeuses = listeTondeuses;
		this.pseudo = pseudo;
	}

	/**
	 * Permet de diffuser la position d'une tondeuse à toutes les autres.
	 * @param tondeuse tondeuse
	 */
	public void notifierPosition(Tondeuse tondeuse) {
		// Avertir toute les tondeuse de la position de celle ci
		// construire messageTondeuse
		String idTondeuse = tondeuse.getIdTondeuse();
		Position positionTondeuse = tondeuse.getPosition();
		Orientation orientationTondeuse = tondeuse.getOrientation();
		MessageTondeuse messageTondeuse = new MessageTondeuse(idTondeuse, positionTondeuse, orientationTondeuse.getDegre());
		/*
		 * Informer qu'une notification est signalée à tous les observateur
		 */
		setChanged();
		notifyObservers(messageTondeuse);
	}
	
	@Override
	public void notifierObstacle(Tondeuse tondeuse) {
		// Avertir toute les tondeuse de la position de celle ci
		for (Tondeuse tondeuseB : listeTondeuses) {
			Position positionTondeuse = tondeuse.getPosition();
			// ne pas me notifier moi même
			if (tondeuseB != tondeuse) {
				tondeuseB.recevoirPosition(positionTondeuse);
			}
		}
	}

	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * @return the listeTondeuses
	 */
	public List<Tondeuse> getListeTondeuses() {
		return listeTondeuses;
	}
}
