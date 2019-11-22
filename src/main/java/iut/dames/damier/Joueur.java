package iut.dames.damier;

import iut.dames.ia.Ia;
//import ia.Aleatoire;

/**
 * Classe qui représente un joueur (humain ou ia)
 */
public class Joueur{

    /**
     * constante qui correspond à un joueur humain (pour le type) vaut 0
     */
    public static final int HUMAIN = 0;
    /**
     * constante qui correspond à un joueur machine - ou ordinateur - (pour le type) vaut 1
     */
    public static final int ORDINATEUR = 1;

    private final int type;

    private final int joueur;

    private Ia monIa;

    /**
     * Crée une nouvelle instance de Joueur avec deux paramètres.
     * @param joueur La couleur du joueur en théorie :
     * <UL>
     * <LI>n'importe quel entier positif pour un joueur de couleur blanche, la valeur 1 étant conseillée
     * <LI>n'importe quel entier négatif pour un joueur de couleur noire, la valeur -1 étant conseillée
     * </UL>
     * @param type permet de définir si le joueur est humain (0) ou machine (1)
     */
    public Joueur(int joueur, int type){
	if (type == 0) this.type = 0;
	else this.type = 1;
	this.joueur = joueur;
    }

    /**
     * Permet de connaitre la couleur du joueur
     * @return La couleur (<0 pour noir et >0 pour blanc)
     */
    public int getJoueur(){
	return joueur;
    }

    /**
     * Permet de connaitre le type du joueur
     * @return Le type du joueur (0) pour humain et (1) pour machine
     */
    public int getType(){
	return type;
    }

    /**
     * Permet d'affecter une instance d'une sous classe d'Ia au joueur. La classe Ia étant abstraite,
     * le paramètre sera une classe qui hérite de Ia.
     * @param monIa une sous classe d'Ia qui définit le comportement d'un joueur
     */
    public void setIa(Ia monIa){
	this.monIa = monIa;
    }

    /**
     * Permet de retourner l'Ia qui est utilisée par le joueur (si c'est une machine)
     * @return Un sous classe d'Ia
     */
    public Ia getIa(){
	return monIa;
    }

    
    /** Retourne une chaine qui représente le joueur.
     * 
     * @return le joueur (son type et l'IA utilisée)
     */
    @Override
    public String toString(){
      return joueur+" "+type+" "+monIa;
    }

}
