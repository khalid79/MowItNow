package fr.xebia.mowitnow.domaine;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class TondeuseTest {
	
	@Before
    public void setUp() {
    }
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Test
	public void simulationTest0() throws Exception{
		TondeuseMediator tondeuseMediator = new TondeuseMediator();
		tondeuseMediator.construireInstructions("5 5\n" + "1 2 N\n" + "GAGAGAGAA\n" + "3 3 E\n" + "AADAADADDAGAAAGAAGADAA");
	}
	
	
	/**
	 * 
	 */
	@Test
	public void simulationTest1(){
		Tondeuse tondeuse = new Tondeuse(5,5,new Position(1, 2),Orientation.N, new TondeuseMediator(), "test1");
		List<Direction> list = new ArrayList<Direction>();
		list.add(Direction.G);
		list.add(Direction.A);
		list.add(Direction.G);
		list.add(Direction.A);
		list.add(Direction.G);
		list.add(Direction.A);
		list.add(Direction.G);
		list.add(Direction.A);
		list.add(Direction.A);
		
		tondeuse.setListeInstructions(list);
		try {
			tondeuse.demarrer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(tondeuse.information());
		Assert.assertEquals(tondeuse.getPosition().getX(), 1);
		Assert.assertEquals(tondeuse.getPosition().getY(), 3);
		Assert.assertEquals(tondeuse.getOrientation(), Orientation.N);
	}
	
	/**
	 * 
	 */
	@Test
	public void simulationTest2(){
		Tondeuse tondeuse = new Tondeuse(5,5,new Position(3, 3),Orientation.E,new TondeuseMediator(),"test2");
		List<Direction> list = new ArrayList<Direction>();
		list.add(Direction.A);
		list.add(Direction.A);
		list.add(Direction.D);
		list.add(Direction.A);
		list.add(Direction.A);
		list.add(Direction.D);
		list.add(Direction.A);
		list.add(Direction.D);
		list.add(Direction.D);
		list.add(Direction.A);
		tondeuse.setListeInstructions(list);
		try {
			tondeuse.demarrer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(tondeuse.informationTondeuse());
		Assert.assertEquals(tondeuse.getPosition().getX(), 5);
		Assert.assertEquals(tondeuse.getPosition().getY(), 1);
		Assert.assertEquals(tondeuse.getOrientation(), Orientation.E);
	}

}
