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
public class TestDamier {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

	// Liste de positions
        Position[] blanc = {new Position(1,0),new Position(2,3)};
        Position[] noir = {new Position(6,1)};

	// Test les constructeurs de Damier

	System.out.println("Damier standard (10x10) positions de départ");
	Damier d = new Damier(10, 10, 4);
	System.out.println(d);
	System.out.println("\n\n\n");


	System.out.println("Damier standard (10x10) rempli aléatoirement");	
	d = new Damier(10,10);
	System.out.println(d);
	System.out.println("\n\n\n");
		
	System.out.println("Damier (15x15) rempli aléatoirement");			
	d = new Damier(15,15);
	System.out.println(d);
	System.out.println("\n\n\n");
		

	System.out.println("Damier standard (10x10) rempli à partir d'un tableau de position"); 
	d = new Damier(10, 10, blanc, noir);
	System.out.println(d);	
	System.out.println("\n\n\n");


	System.out.println("Damier obtenu par recopie"); 
	Damier d2 = new Damier(d);
	System.out.println(d2);
	System.out.println("\n\n\n");

    }
    
}
