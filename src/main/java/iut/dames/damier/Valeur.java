/*
 * Valeur.java
 *
 * Permet de calculer la valeur de jeu d'un damier
 *
 * Created on 7 mai 2004, 23:08
 */

package iut.dames.damier;


/**
 * Représente un damier
 * @author Samuel Delepoulle
 */
public class Valeur {
    
    /** valeur d'un pion */
    public static final int PION = 1;
    /** valeur d'une dame */
    public static final int REINE = 10;
    /** valeur d'un plateau gagnant */
    public static final int GAGNE = 1000;
    

    /**
     * Calcule la valeur de jeu pour l'état courrant du damier. La valeur de jeu est une quantité qui caractérise un damier.
     * <BR>
     * Elle peut être utilisée par des algorithmes d'intelligence artificielle.
     * <BR><BR>
     * Ici elle est calculée de la façon suivante :
     * <UL>
     * <LI> 1000 si le damier est dans un état gagnant (aucun pion de l'adversaire sur
     * le damier)
     * <LI> -1000 si le damier est dans un état perdant (aucun pion du joueur
     * sur le damier)
     * <LI> sinon la formule suivante est appliquée :<BR>
     * <PRE>NbPionJoueur + 10*NbDameJoueur - NbPionAdversaire - 10*NbDameAdversaire</PRE>
     * @param joueur joueur considéré
     * @return valeur de jeu codée sur un int
     */    
    public static int valeurDeJeu(Damier d, int joueur){	
                	
	int total = 0, positif = 0 , negatif = 0;

	for (int i=0; i<d.getMaxX(); i++){
            for (int j=0; j<d.getMaxY(); j++){
		int contenu = d.getContenu(i,j);

		switch (contenu){
			case 2:
				negatif += REINE;
				break;
			case 1:
				negatif += PION;
				break;
			case -1:
				positif += PION;
				break;
			case -2:
				positif += REINE;
				break;
		default : break;
		}
            }		    
        }

	if (joueur>0){
	    if (positif == 0) return -GAGNE;
	    if (negatif == 0) return GAGNE;
	    return positif-negatif;
	}
	
	if (positif == 0) return GAGNE;
	if (negatif == 0) return -GAGNE;
	return -(positif-negatif);
	
    }

}
