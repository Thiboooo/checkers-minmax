package iut.dames.ia;


import iut.dames.damier.Damier;
import iut.dames.damier.Position;
import iut.dames.damier.Coup;
import iut.dames.damier.Deplacement;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémente une Ia qui choisi le premier coup possible (le premier pion possible est déplacé suivant le premier mouvement possible.)
 */
public class PremierChoix extends Ia{


    /**
     * Crée une nouvelle instance de la classe Aleatoire
     * @param joueur couleur du joueur
     */    
    public PremierChoix(int joueur){
	super(joueur);
    }

    /**
     * Choix du premier coup possible 
     * @param damier damier sur lequel on se déplace
     * @param joueur couleur du joueur
     * @param position position
     * @return un déplacement valide
     */    
    public Deplacement choix(Damier damier ,int joueur, Position position){
	List coupPossible = new ArrayList<Position>();

	Position depart, arrive;
	
	depart = position;
	System.out.println("Départ : "+depart);
	
	coupPossible = Coup.listeArriveValide(damier, depart, joueur, false);

	if (coupPossible.isEmpty()) return null;

	arrive = (Position)(coupPossible.get(0));
		
	return new Deplacement(depart,arrive);
	
    }

    /**
     * On choisi le premier pion disponible est on effectue le premier mouvement possible
     * @param damier le damier sur lequel on se déplace
     * @param joueur le joueur (sa couleur)
     * @return un déplacement valide
     */    
    @Override
    public Deplacement choix(Damier damier, int joueur){
	List<Position> listePos = damier.listePositionPion(joueur);
	int taille = listePos.size();
	
	Position depart, arrive;
	int nbcoup = 0;
	List<Position> coupPossible = new ArrayList<>();
        
        int num = 0;

	// trouver un pion qui peut se déplacer
	do{
	    depart = (Position)(listePos.get(num));
            num++;
	    System.out.println("Départ : "+depart);
	
	    coupPossible = Coup.listeArriveValide(damier, depart, joueur, false);

	    nbcoup = coupPossible.size();
	} while (nbcoup == 0);
	      		
	// premier coup possible
	arrive = (Position)(coupPossible.get(0));
		
	return new Deplacement(depart,arrive);

    } // choix


}