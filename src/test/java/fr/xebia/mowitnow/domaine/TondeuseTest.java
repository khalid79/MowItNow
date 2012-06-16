package fr.xebia.mowitnow.domaine;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import fr.xebia.mowitnow.autres.TondeuseException;


/**
 * Classe de test de la tondeuse.
 * @author KBOUAABD
 *
 */
public class TondeuseTest {
	
	/**
	 * Permet de simuler un observer.
	 * @author KBOUAABD
	 *
	 */
	private class ObserverTest implements Observer{
		/** 
		 * mediator.
		 */
		public TondeuseMediator mediator;
		
		@Override
		public void update(Observable observable, Object message) {
			 mediator = (TondeuseMediator) observable;
		}
		
	}
	
	/**
	 * Simulation de la position des tondeuses.
	 * @throws Exception 
	 */
	@Test
	public void simulationTondeuses() throws Exception{
		ObserverTest observerTest = new ObserverTest();
		TondeuseFactory.initialisationTondeuses("5 5\n1 2 N\nGAGAGAGAA\n3 3 E\nAADAADADDA", "pseudo", observerTest);
		// pour être sur que l'exécution des instructions est finie
		Thread.sleep(3000);
		List<Tondeuse> listeTondeuses = observerTest.mediator.getListeTondeuses();
		for (Tondeuse tondeuse : listeTondeuses) {
			// première tondeuse
			Position dernierePosition = tondeuse.getPosition();
			if(tondeuse.getIdTondeuse().equals("1")){
				Assert.assertEquals(dernierePosition.getX(), 1);
				Assert.assertEquals(dernierePosition.getY(), 3);
			}else if(tondeuse.getIdTondeuse().equals("2")){
				Assert.assertEquals(dernierePosition.getX(), 5);
				Assert.assertEquals(dernierePosition.getY(), 1);
			}
		}
	}
	
	/**
	 * Permet de tester le calcule de l'orientation à partir de la direction.
	 * @throws TondeuseException 
	 */
	@Test
	public void construirePelouseTestSuccess() throws TondeuseException{
		Tondeuse tondeuseTest = new Tondeuse(Orientation.N);
		Orientation orientation =  tondeuseTest.calculeOrientation(-90);
		Assert.assertEquals(orientation, Orientation.W);
	}
}
