package fr.xebia.mowitnow.domaine;


import java.util.List;
import java.util.Observable;


import fr.xebia.mowitnow.presentation.MessageTondeuse;
import fr.xebia.mowitnow.presentation.PushPositionServlet;

/**
 * Le m�diateur et l'orchestrateur de communication entre les tondeuse.
 * Initialise est d�marre toutes les tondeuses.
 * 
 * @author kbouaabd
 * 
 */
public class TondeuseMediator  extends Observable implements IMediator {

	/**
	 * la liste des tondeuses.
	 */
	private List<Tondeuse> listeTondeuses;
	
	
	/**
	 * Constructeur du m�diateur.
	 */
	public TondeuseMediator(){
		
	}

	/**
	 * Constructeur du m�diateur.
	 * @param listeTondeuses connues par le m�diator
	 */
	public TondeuseMediator(List<Tondeuse> listeTondeuses) {
		this.listeTondeuses = listeTondeuses;
	}

	/**
	 * Permet de diffuser la position d'une tondeuse � toutes les autres.
	 * @param tondeuse tondeuse
	 */
	public void notifierPosition(Tondeuse tondeuse) {
		// Avertir toute les tondeuse de la position de celle ci
		for (Tondeuse tondeuseB : listeTondeuses) {
			// temporaire
			String idTondeuse = tondeuse.getIdTondeuse();
			Position positionTondeuse = tondeuse.getPosition();
			Orientation orientationTondeuse = tondeuse.getOrientation();
			MessageTondeuse messageTondeuse = new MessageTondeuse(idTondeuse, positionTondeuse, orientationTondeuse.getDegre());
			/*
			 * Informer qu'une notification est signal�e � tous les observateur
			 */
			setChanged();
			notifyObservers(messageTondeuse);
			// ne pas me notifier moi m�me
			if (tondeuseB != tondeuse) {
				tondeuseB.recevoirPosition(positionTondeuse);
			}
		}
	}

}
