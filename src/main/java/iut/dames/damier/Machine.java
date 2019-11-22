package iut.dames.damier;

import iut.dames.ia.Ia;
//import ia.MiniMax;

/**
 * Représente un joueur de type machine (ordinateur ou IA)
 */
public class Machine extends Joueur{

    /**
     * Permet de créer une nouvelle instance de Machine avec pour paramètre la couleur de joueur et une instance d'une sous classe d'Ia
     * @param joueur Couleur du joueur (<0 pour noir et >0 pour blanc)
     * @param ia une sous classe d'Ia
     */    
    public Machine(int joueur, Ia ia){
	super(joueur, Joueur.ORDINATEUR);	
	setIa(ia);
    }

    /**
     * Permet de connaitre le damier suivant demandé par l'Ia. Ce damier correspond à un damier possible
     * après un coup.
     * @param damier le damier de départ
     * @return damier après avoir joué.
     */    
    public Damier joue(Damier damier){        
	return getIa().joue(damier);
    }

}