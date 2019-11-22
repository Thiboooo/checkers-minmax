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
 * @author  samuel
 */
public class TestDamierPrises {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

	// Declaration des positions
	//Position[] blanc = {new Position(2,3), new Position(5,5)};
	//Position[] noir = {new Position(1,2), new Position(3,2),new Position(5,2), new Position(4,6)};
        
        // Declaration des positions
	Position[] blanc = {new Position(2,3), new Position(5,6)};
	Position[] noir = {new Position(1,2), new Position(3,2), new Position(4,7)};
        

	// Le damier
        Damier damier = new Damier(10, 10,blanc, noir);
        System.out.println(damier);

	// Position de départ
	Position depart= new Position(5,5);

        System.out.println(Coup.listeArriveValide(damier, depart, 1, false).size()+" coups valide");
        for (Position p : Coup.listeArriveValide(damier, depart, 1, false)){
            System.out.println(p);
        }
        
        
	// On trouve la première position possible
	Position arrive=(Position)(Coup.listeArriveValide(damier, depart, 1, false).get(0));
                

	System.out.println("\n"+arrive);
	
	// Le déplacement
	Deplacement deplace = new Deplacement(depart,arrive);

	// On valide le déplacement
	ResultatAction ra = Coup.valide(damier, deplace, 1, false);
	System.out.println(ra);


	
	// On l'exécute
	System.out.println(Coup.execute(damier, deplace, ra));

	
			   

    }
    
}
