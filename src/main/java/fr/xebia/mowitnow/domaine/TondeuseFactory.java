package fr.xebia.mowitnow.domaine;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import fr.xebia.mowitnow.autres.TondeuseException;

/**
 * Permet d'instancier les tondeuses et le m�diateur des tondeuses.
 * @author kbouaabd
 *
 */
public class TondeuseFactory {
	
	/**
	 * Le s�parateur est un espace.
	 */
	private static final String SEPARATEUR = "\\s";


	/**
	 * Interpreteur des instructions.
	 * TODO: D�placer cette m�thode dans une
	 * autre classe : par ce que les tondeuse peuvent l'utliser
	 * @param pseudo pseudo
	 * @param observer observer
	 * @param instructions
	 *            instructions
	 * @throws TondeuseException  TondeuseException
	 */
	public static void initialisationTondeuses(String instructions,String pseudo, Observer observer) throws TondeuseException {
		
		// cr�er le tableau des instructions
		String[] tableauInstructions = instructions.split("\n");
		Pelouse pelouse = new Pelouse();
		try{
			// cr�er la pelouse � partir des informations de la 1er ligne
			String[] informationPelouse = tableauInstructions[0].split(SEPARATEUR);
			pelouse.setLargeur(new Integer(informationPelouse[0]));
			pelouse.setHauteur(new Integer(informationPelouse[1]));
		}catch(NumberFormatException numberFormatException){
			throw new TondeuseException("Erreur lors du parsing des commandes de la pelouse.");
		}
		// Pr�voir une liste de tondeuse
		List<Tondeuse> listeTondeuses  = new ArrayList<Tondeuse>();
		 // Instancier le m�diateur des tondeuses
		 // Le m�diteur doit connaitre toutes les tondeuses
		TondeuseMediator tondeuseMediator = new TondeuseMediator(listeTondeuses,pseudo);
		// Ajouter l'observateur
		tondeuseMediator.addObserver(observer);
		// d�terminer le nombre de tondeuses
		int nombreTondeuse = (tableauInstructions.length - 1) / 2;
		for (int i = 0; i < nombreTondeuse; i++) {
			int numeroTondeuse = i+1;
			Tondeuse tondeuse =null;
			try{
				// r�cup�rer la poisition : exemple 1 4 N
				String[] positionTondeuse = tableauInstructions[(2 * i) + 1].split(SEPARATEUR);
				Position position = new Position(new Integer(positionTondeuse[0]), new Integer(positionTondeuse[1]));
				// v�rifier que la tondeuse est bien dans la pelouse
				if(position.getX() > pelouse.getLargeur() || position.getY() > pelouse.getHauteur()){
					throw new TondeuseException("Erreur la tondeuse num�ro "+ numeroTondeuse+" est hors la pelouse");
				}
				// r�cup�rer l'orientation en 3�me position
				Orientation orientation = Orientation.valueOf(positionTondeuse[2]);
				// cr�er la tondeuse
				tondeuse = new Tondeuse(pelouse.getLargeur(), pelouse.getHauteur(), position,
											orientation, tondeuseMediator , String.valueOf(numeroTondeuse) );
			}catch(NumberFormatException numberFormatException){
				throw new TondeuseException("Erreur lors du parsing des commandes pour positionner la tondeuse num�ro "+ numeroTondeuse);
			}catch (IllegalArgumentException argumentException) {
				throw new TondeuseException("Erreur lors du parsing des commandes pour orienter la tondeuse num�ro "+ numeroTondeuse +
						". Les orientation autoris�es sont : N,E,W,S ." );
			}
			// r�cup�rer les instructions de direction
			String directionTondeuse = tableauInstructions[(2 * i) + 2];
			// cr�er la liste des directions
			List<Direction> listInstruction = new ArrayList<Direction>();
			for (int j = 0; j < directionTondeuse.length(); j++) {
				char direction = directionTondeuse.charAt(j);
				try{
					listInstruction.add(Direction.valueOf(String.valueOf(direction)));
				}catch (IllegalArgumentException argumentException) {
					throw new TondeuseException("Erreur lors du parsing des commandes pour diriger la tondeuse num�ro "+ numeroTondeuse+
							". Les directions autoris�es sont : A,D,G .");
				}
			}
			tondeuse.setListeInstructions(listInstruction);
			listeTondeuses.add(tondeuse);

		}
		demarrerTondeuses(listeTondeuses);
	}
	

	/**
	 * Permet de d�marrer toutes les tondeuses.
	 * @param listeTondeuses est la liste des tondeuses � d�marrer
	 * 
	 */
	private static void demarrerTondeuses(List<Tondeuse> listeTondeuses)  {
		for (Tondeuse tondeuse : listeTondeuses) {
			try {
				tondeuse.demarrer();
			} catch (Exception e) {
				// TODO : Exception pendant l'avancement de la tondeuse
				e.printStackTrace();
			}
		}
	}
}
