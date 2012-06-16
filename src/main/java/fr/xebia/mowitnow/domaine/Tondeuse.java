package fr.xebia.mowitnow.domaine;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.xebia.mowitnow.autres.TondeuseException;

/**
 * Repr�sente une tondeuse. Avec toutes ces fonctionnalit�es.
 * 
 * @author KBOUAABD
 */
public class Tondeuse {
	
	/**
	 * Logger.
	 */
	private static final Logger log = Logger.getLogger(Tondeuse.class.getName());

	/***** Attributs de base de la tondeuse *****/

	/**
	 * id de la tondeuse.
	 */
	private String idTondeuse;

	/**
	 * la limite � ne pas d�pass�e sur l'axe : x.
	 */
	private int limiteX;

	/**
	 * la limite � ne pas d�pass�e sur l'axe : y.
	 */
	private int limiteY;

	/**
	 * la position courante de la tondeuse.
	 */
	private Position position;

	/**
	 * l'orientation de la tondeuse.
	 */
	private Orientation orientation;

	/**
	 * la liste des instruction qui doivent �tre interpr�t� par la tondeuse.
	 */
	private List<Direction> listeInstructions;

	/***** Attributs en plus *****/

	/**
	 * chaque tonseuse poss�de un m�diteur pour communiquer avec les autres.
	 */
	private IMediator tondeuseMediator;

	/**
	 * les obstacles qui peuvent se trouv�s dans l'espace de la tondeuse.
	 */
	private List<Position> listeObstacle;

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
	 * @param idTondeuse
	 *            idTondeuse
	 */
	public Tondeuse(int limiteX, int limiteY, Position position,
			Orientation orientation, IMediator tondeuseMediator,
			String idTondeuse) {
		this.limiteX = limiteX;
		this.limiteY = limiteY;
		this.position = position;
		this.orientation = orientation;
		this.tondeuseMediator = tondeuseMediator;
		this.listeObstacle = new ArrayList<Position>();
		this.idTondeuse = idTondeuse;
	}
	
	

	/**
	 * Constructeur avec l'orientation.
	 * @param orientation orientation
	 */
	public Tondeuse(Orientation orientation) {
		super();
		this.orientation = orientation;
	}



	/**
	 * Permet de d�placer la tondeuse en parcourant les instructions.
	 * @throws TondeuseException  TondeuseException
	 */
	public void demarrer() throws TondeuseException  {
		// notifier la position initiale
		tondeuseMediator.notifierPosition(this);
		for (Direction instruction : listeInstructions) {
			if (instruction == Direction.A) {
				Position positiontemp = avancer();
				verifierObstacle(positiontemp);
				log.info(informationTondeuse());
				
			} else {
				// il faut changer d'orientation
				orientation = calculeOrientation(instruction.getDegre());
			}
			tondeuseMediator.notifierPosition(this);
		}
		// notifier ma position entant qu'obstacle pour les autre tondeuses.
		tondeuseMediator.notifierObstacle(this);
	}

	/**
	 * Permet de v�rifier si la position en parametre fais partie des obstacle.
	 * @param positionTemporaire position calcul�e temporaire
	 */
	private void verifierObstacle(Position positionTemporaire) {
		boolean obstacle = false;
		// v�rifier que la position temporaire n'est pas pr�sente dans la liste des obtacle
		if (listeObstacle.isEmpty()) {
			position = positionTemporaire;
		} else {
			for (Position positionObstacle : listeObstacle) {
				if (positionObstacle.getX() == positionTemporaire.getX() && positionObstacle.getY() == positionTemporaire.getY()) {
					obstacle = true;
					break;
				} 
			}
			if(!obstacle){
				position = positionTemporaire;
			}else{
				log.warning("Attention pr�sence d'un obstacle sur la position : " + positionTemporaire.toString());
			}
		}
	}
	
	
	

	/**
	 * Permet de calculer l'onrientation en fonction des degre.
	 * 
	 * @param degre
	 *            Exception
	 * @return l'orientation qui corespondant au degre
	 * @throws TondeuseException  TondeuseException
	 */
	protected Orientation calculeOrientation(int degre) throws TondeuseException  {
		// si degreeCalcule �gale � 360 le remettre � z�ro
		int degreeCalcule = (degre + orientation.getDegre()) % 360;
		if (degreeCalcule < 0) {
			degreeCalcule = 360 + degreeCalcule;
		}
		for (Orientation orientation : Orientation.values()) {
			if (degreeCalcule == orientation.getDegre()) {
				return orientation;
			}
		}
		// si l'orientation ne fait pas parties des Orientation.values(), alors throw une TondeuseException 
		throw new TondeuseException("Erreur dans le calcule de l'orientation." +
				" tondeuse : "+ idTondeuse+ " , orientation : "+degreeCalcule);
	}

	/**
	 * Permet � la tondeuse d'avancer en fct de l'orientation.
	 * 
	 * @return la position calcul�e
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
	 * Permet de r�cup�rer les positions des autres tondeuses. 
	 * Ajouter les positions comme des obstacles
	 * @param positionObstacle
	 *            position
	 */
	public void recevoirPosition(Position positionObstacle) {
		this.listeObstacle.add(positionObstacle);
	}

	/**
	 * informationTondeuse.
	 * 
	 * @return informationTondeuse
	 */
	public String informationTondeuse() {
		StringBuilder sb = new StringBuilder();
		sb.append("idTondeuse : " + idTondeuse);
		sb.append("\n");
		sb.append("Position x : " + position.getX());
		sb.append("\n");
		sb.append("Position y : " + position.getY());
		sb.append("\n");
		sb.append("Position orientation : " + orientation.getLabel());
		return sb.toString();
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
