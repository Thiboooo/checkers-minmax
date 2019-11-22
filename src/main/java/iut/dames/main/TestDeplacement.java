package iut.dames.main;

/*
 * TestDamier.java
 *
 * Created on 8 mai 2004, 20:52
 */

//import damier.Case;
import iut.dames.damier.*;


/**
 *
 * @author  Samuel Delepoulle
 */
public class TestDeplacement {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	Damier d = new Damier(10,10,4);
	Position depart = new Position(4,4);
	Position arrive = new Position(5,5);
	Deplacement deplace = new Deplacement(depart,arrive);
	System.out.println(deplace);
	DamierDeplacement dd = new DamierDeplacement(d,deplace);	
    }
}