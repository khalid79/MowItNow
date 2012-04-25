package fr.xebia.mowitnow.domaine;

import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Permet d'instancier les tondeuses et le médiateur des tondeuses.
 * @author kbouaabd
 *
 */
public class TondeuseFactory {
	
	/**
	 * Interpreteur des instructions. TODO: Déplacer cette méthode dans une
	 * autre classe : par ce que les tondeuse peuvent l'utliser
	 * 
	 * @param tableauInstructions
	 *            tableauInstructions
	 */
	public static void initialisationTondeuses(String[] tableauInstructions, Observer observer) {
		
		String lignesInstructions = formaterInstruction(tableauInstructions);
		
		String[] lines = lignesInstructions.split("\n");
		String[] xy = lines[0].split(" ");
		int width = parseInt(xy[0]);
		int height = parseInt(xy[1]);
		int nbMower = (lines.length - 1) / 2;
		/*
		 * Prévoir une liste de tondeuse
		 */
		List<Tondeuse> listeTondeuses  = new ArrayList<Tondeuse>();
		/*
		 * Instancier le médiateur des tondeuses
		 * Le méditeur doit connaitre toutes les tondeuses
		 */
		TondeuseMediator tondeuseMediator = new TondeuseMediator(listeTondeuses);
		// Ajouter l'observateur
		tondeuseMediator.addObserver(observer);
		
		for (int i = 0; i < nbMower; i++) {
			String[] positionChar = lines[(2 * i) + 1].split(" ");
			Position position = new Position(parseInt(positionChar[0]),
					parseInt(positionChar[1]));
			Tondeuse tondeuse = new Tondeuse(width, height, position,
					Orientation.valueOf(positionChar[2]), tondeuseMediator , String.valueOf(i) );
			List<Direction> listInstruction = new ArrayList<Direction>();
			for (char c : lines[(2 * i) + 2].toCharArray()) {
				listInstruction.add(Direction.valueOf(Character.toString(c)));
			}
			tondeuse.setListeInstructions(listInstruction);
			listeTondeuses.add(tondeuse);

		}
		demarrerTondeuses(listeTondeuses);
	}
	

	/**
	 * Permet de démarrer toutes les tondeuses.
	 * @param listeTondeuses est la liste des tondeuses à démarrer
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
	
	
	/**
	 * Construire instruction avec saut de ligne.
	 * TODO: faire un Test unitaire
	 * @param tableauInstructions instructionTab
	 * @return instruction sous forme de string
	 */
	private static String formaterInstruction(String[] tableauInstructions){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tableauInstructions.length; i++) {
			String information = tableauInstructions[i];
			if(!information.isEmpty()){
				sb.append(information);
				sb.append("\n");
			}
		}
		return sb.toString();
	}

}
