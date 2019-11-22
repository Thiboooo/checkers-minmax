package iut.dames.ia;

import iut.dames.damier.Case;
import iut.dames.damier.Deplacement;
import iut.dames.damier.Damier;
import iut.dames.damier.ResultatAction;
import iut.dames.damier.Coup;
import iut.dames.damier.Position;
import java.awt.Color;

/**
 * Classe abstraite qui décrit une IA (Intelligence Artificielle)
 */
public abstract class Ia{

    /**
     * représente la couleur du joueur (<0 pour les noirs >0 pour les blancs)
     */    
    protected int joueur;

    /**
     * Constructeur pour l'Ia
     * @param joueur couleur du joueur (<0 pour les noirs >0 pour les blancs)
     */    
    public Ia(int joueur){
	this.joueur = joueur;
    }
    
    
    /**
     * Méthode qui permet de choisir le joueur que joue une IA.
     *
     * Attention : ceci ne change pas les coups joué sur l'interface mais simple
     * pour quel joueur l'IA choisit les meilleurs coups.
     * 
     * @param joueur 
     */
    public void setJoueur(int joueur){
        this.joueur = joueur;
    }

    /**
     * Déplacement choisi par l'Ia sur un damier, connaissant la couleur du joueur
     * @param damier le damier sur lequel ont lieu les déplacements
     * @param joueur joueur qui doit effectuer un coup (ce n'est pas forcément le joueur de l'ia puisque
     * l'algorithme peut avoir besoin de prédire les déplacements de l'adversaire).
     *
     * En pratique Deplacement(damier, joueur) permet de connaitre le coup du joueur,
     * Deplacement(damier, -joueur) permet de connaitre le coup de l'adversaire en suivant
     * le même algorithme.
     * @return un déplacement qui correspond à un coup valide sur le damier
     */    
    abstract public Deplacement choix(Damier damier, int joueur);

    /**
     * Permet de connaitre le coup de l'Ia pour un damier, pour un joueur et pour un pion. Ceci permet
     * de forcer l'Ia à jouer avec un pion précis (utilisé lors des prises multiples : un joueur peut
     * prendre plusieurs fois mais avec le même pion).
     * @param damier le damier
     * @param joueur la couleur du joueur
     * @param position la position du pion qui doit être joué
     * @return Un déplacement valide
     */    
    abstract public Deplacement choix(Damier damier, int joueur, Position position);

    /**
     * Permet de connaitre le damier après un coup de l'Ia
     * @param damier damier avant le coup
     * @return damier après le coup
     */    
    public Damier joue(Damier damier){
	
	Deplacement deplacement = choix(damier,joueur);
        
        Case.setColorSelected(Color.GREEN);
        damier.setSelected(deplacement.getDepart(), true);
        damier.setSelected(deplacement.getArrive(), true);
        
        
	ResultatAction resultatAction = Coup.valide(damier,deplacement,joueur, false);       
	Damier temp = Coup.execute(damier, deplacement, resultatAction);
	boolean stop = false;

 	// si on peut reprendre, on le fait
 	while (resultatAction.getPris() != null && !stop){

 	    deplacement = choix(temp,joueur,deplacement.getArrive());

	    if (deplacement != null){
		resultatAction = Coup.valide(temp,deplacement,joueur, true);
		
		if (resultatAction.getValide()){
		    temp = Coup.execute(temp, deplacement, resultatAction);
		}
 	    }	 
	    else {
		stop = true;
	    }
 	}
	return temp;
    }


}