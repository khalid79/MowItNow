package fr.xebia.mowitnow.domaine;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une tondeuse. Avec toutes ces fonctionnalitées.
 * 
 * @author KBOUAABD
 */
public class Tondeuse {

	// ////////////////// Attributs de base de la tondeuse
	// ///////////////////////////
	
	/**
	 * id de la tondeuse.
	 */
	public String idTondeuse;

	/**
	 * la limite à ne pas dépassée sur l'axe : x.
	 */
	public int limiteX;

	/**
	 * la limite à ne pas dépassée sur l'axe : y.
	 */
	public int limiteY;

	/**
	 * la position courante de la tondeuse.
	 */
	public Position position;

	/**
	 * l'orientation de la tondeuse.
	 */
	public Orientation orientation;

	/**
	 * la liste des instruction qui doivent être interprété par la tondeuse.
	 */
	public List<Direction> listeInstructions;

	// ////////////////// Attributs en plus ///////////////////////////

	/**
	 * chaque tonseuse possède un méditeur pour communiquer avec les autres.
	 */
	private IMediator tondeuseMediator;

	/**
	 * les obstacles qui peuvent se trouvés dans l'espace de la tondeuse.
	 */
	public List<Position> listeObstacle;

	/**
	 * Constructeur de la tondeuse.
	 * 
	 * @param limiteX
	 *            limiteX
	 * @param limiteY
	 *            limiteY
	 * @param position
	 *            position
	 * @param orientation
	 *            orientation
	 * @param tondeuseMediator
	 *            tondeuseMediator
	 *             @param idTondeuse idTondeuse
	 */
	public Tondeuse(int limiteX, int limiteY, Position position,
			Orientation orientation, IMediator tondeuseMediator, String idTondeuse) {
		this.limiteX = limiteX;
		this.limiteY = limiteY;
		this.position = position;
		this.orientation = orientation;
		this.tondeuseMediator = tondeuseMediator;
		this.listeObstacle = new ArrayList<Position>();
		this.idTondeuse = idTondeuse;
	}

	/**
	 * Permet de déplacer la tondeuse en parcourant les instructions.
	 * 
	 * @throws Exception
	 *             TODO: changer le type de l'exception
	 */
	public void demarrer() throws Exception {
		// send premier position
		send(position);
		for (Direction instruction : listeInstructions) {
			if (instruction == Direction.A) {
				Position positiontemp = avancer();
				// vérifier que la position temporaire n'est pas présente dans
				// la liste des obtacle
				if (listeObstacle.isEmpty()) {
					position = positiontemp;
				} else {
					for (Position positionB : listeObstacle) {
						if (positionB.getX() != positiontemp.getX()
								|| positionB.getY() != positiontemp.getY()) {
							position = positiontemp;
							break;
						} else {
							System.out.println("Stoooop !!!!");
						}
					}
				}
				System.out.println(informationTondeuse());
				System.out.println("-------------");
				send(position);
			} else {
				// il faut changer d'orientation
				orientation = calculeOrientation(instruction.getDegre());
			}
		}
		// Envoyer la dernier position
		//send(position);
	}

	/**
	 * Permet de calculer l'onrientation en fonction des degre.
	 * 
	 * @param degre
	 *            Exception
	 * @return l'orientation qui corespondant au degre
	 * @throws Exception
	 *             TODO: changer le type de l'exception
	 */
	private Orientation calculeOrientation(int degre) throws Exception {
		int degreeCalcule = ( degre + orientation.getDegre() ) %360;
		if (degreeCalcule < 0) {
			degreeCalcule = 360 + degreeCalcule;
		} 
//		else if (degreeCalcule == 360) {
//			degreeCalcule = 0;
//		}
		for (Orientation orientation : Orientation.values()) {
			if (degreeCalcule == orientation.getDegre()) {
				return orientation;
			}
		}
		throw new Exception("calcule orientation erreur :  ---> "
				+ degreeCalcule);
	}

	/**
	 * Permet à la tondeuse d'avancer en fct de l'orientation.
	 * 
	 * @return la position calculée
	 */
	private Position avancer() {
		Position positionTemp = new Position(position.getX(), position.getY());
		// Avancement
		switch (orientation) {
		case N:
			if (position.getY() < limiteY) {
				positionTemp.setY(position.getY() + 1);
			}
			break;
		case E:
			if (position.getX() < limiteX) {
				positionTemp.setX(position.getX() + 1);
			}
			break;
		case S:
			if (position.getY() > 0) {
				positionTemp.setY(position.getY() - 1);
			}
			break;
		case W:
			if (position.getX() > 0) {
				positionTemp.setX(position.getX() - 1);
			}
			break;
		default:
			break;
		}
		return positionTemp;
	}

	/**
	 * informationTondeuse.
	 * 
	 * @return informationTondeuse
	 */
	public String informationTondeuse() {
		StringBuilder sb = new StringBuilder();
		sb.append("Position x : " + position.getX());
		sb.append("\n");
		sb.append("Position y : " + position.getY());
		sb.append("\n");
		sb.append("Position orientation : " + orientation.getLabel());
		sb.append("\n");
		sb.append("this : " + this);
		return sb.toString();
	}

	/*************************************************************/
	/********************** Mediator ******************************/

	/**
	 * Envoyer un message avec le mediator.
	 * 
	 * @param position
	 *            position
	 */
	public void send(Position position) {
		tondeuseMediator.send(position, this);
	}

	/**
	 * Permet de récupérer les positions des autres tondeuses. Ajouter les
	 * positions comme des obstacles
	 * 
	 * @param position
	 *            position
	 */
	public void receive(Position position) {
		this.listeObstacle.add(position);
		System.out.println("Position nouveau obstacle: " + position.toString()
				+ " this " + this);
	}

	/**
	 * @return the limiteX
	 */
	public int getLimiteX() {
		return limiteX;
	}

	/**
	 * @return the limiteY
	 */
	public int getLimiteY() {
		return limiteY;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @return the orientation
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * @return the listeInstructions
	 */
	public List<Direction> getListeInstructions() {
		return listeInstructions;
	}

	/**
	 * @param listeInstructions
	 *            the listeInstructions to set
	 */
	public void setListeInstructions(List<Direction> listeInstructions) {
		this.listeInstructions = listeInstructions;
	}

	/**
	 * @return the listeObstacle
	 */
	public List<Position> getListeObstacle() {
		return listeObstacle;
	}

	/**
	 * @return the idTondeuse
	 */
	public String getIdTondeuse() {
		return idTondeuse;
	}

	
}
