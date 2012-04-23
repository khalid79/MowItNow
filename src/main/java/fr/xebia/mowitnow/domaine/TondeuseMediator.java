package fr.xebia.mowitnow.domaine;

import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import fr.xebia.mowitnow.presentation.PushPositionServlet;

/**
 * Le médiateur et l'orchestrateur de communication entre les tondeuse.
 * Initialise est démarre toutes les tondeuses.
 * 
 * @author kbouaabd
 * 
 */
public class TondeuseMediator implements IMediator {

	/**
	 * la liste des tondeuses.
	 */
	private List<Tondeuse> listeTondeuse;
	
	/**
	 * Push servlet
	 */
	private PushPositionServlet pushServlet;
	

	/**
	 * Constructeur du médiateur.
	 */
	public TondeuseMediator() {
		listeTondeuse = new ArrayList<Tondeuse>();
	}

	/**
	 * Interpreteur des instructions. TODO: Déplacer cette méthode dans une
	 * autre classe : par ce que les tondeuse peuvent l'utliser
	 * 
	 * @param lignesInstructions
	 *            lignesInstructions
	 * @throws Exception
	 *             Exception
	 */
	public void construireInstructions(String lignesInstructions)
			throws Exception {
		String[] lines = lignesInstructions.split("\n");
		String[] xy = lines[0].split(" ");
		int width = parseInt(xy[0]);
		int height = parseInt(xy[1]);
		int nbMower = (lines.length - 1) / 2;
		listeTondeuse = new ArrayList<Tondeuse>();
		for (int i = 0; i < nbMower; i++) {
			String[] positionChar = lines[(2 * i) + 1].split(" ");
			Position position = new Position(parseInt(positionChar[0]),
					parseInt(positionChar[1]));
			Tondeuse tondeuse = new Tondeuse(width, height, position,
					Orientation.valueOf(positionChar[2]), this);
			List<Direction> listInstruction = new ArrayList<Direction>();
			for (char c : lines[(2 * i) + 2].toCharArray()) {
				listInstruction.add(Direction.valueOf(Character.toString(c)));
			}
			tondeuse.setListeInstructions(listInstruction);
			listeTondeuse.add(tondeuse);

		}
		demarrerTous();
	}

	/**
	 * Démarrer toutes les tondeuses.
	 * 
	 * @throws Exception
	 *             Exception
	 */
	private void demarrerTous() throws Exception {
		for (Tondeuse tondeuse : listeTondeuse) {
			tondeuse.demarrer();
		}
	}

	/**
	 * Permet de diffuser la position d'une tondeuse à toutes les autres.
	 * @param position position
	 * @param tondeuse tondeuse
	 */
	public void send(Position position, Tondeuse tondeuse) {
		// Avertir toute les tondeuse de la position de celle ci
		for (Tondeuse tondeuseB : listeTondeuse) {
			// ne pas me notifier moi même
			if (tondeuseB != tondeuse) {
				tondeuseB.receive(position);
				// temporaire
				pushServlet.pushPosition(position);
			}
		}
	}

	/**
	 * @param pushServlet the pushServlet to set
	 */
	public void setPushServlet(PushPositionServlet pushServlet) {
		this.pushServlet = pushServlet;
	}
	
	

}
