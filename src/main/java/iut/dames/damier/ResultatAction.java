package iut.dames.damier;

/* Classe qui permet d'encapsuler le résulat d'une action 
   Deux attributs : 
   * pris = une position (la position du pion pris
   si pris = null : pas de pion pris
   * valide = boolean : indique si l'action est validée ou non
   */
   

/**
 * Permet d'encapsuler dans une même classe le résultat d'une action
 *
 * <UL>
 * <LI> sa validité (valide ou non valide selon qu'elle correspond à un déplacement autorisé
 * ou non)
 * <LI> la position du pion qui a été éventuellement pris par le déplacement
 * </UL>
 */
public class ResultatAction{
    
    // le pion pris éventuellement
    private Position pris;
    
    // l'action est validée ou non
    private boolean valide = false;


    /**
     * Récupère la validation du coup
     * @return vrai si le coup a été validé, faux sinon
     */    
    public boolean getValide(){
	return valide;
    }

   

    /**
     * Permet d'indiquer la position du pion qui a été pris
     * @param pris Position du pion pris, s'il existe (null s'il n'y a pas eu de prise)
     */    
    public void setPris(Position pris){
	this.pris = pris;
    }

    /**
     * Permet d'accéder au pion qui a été pris
     * @return la position du pion pris
     */    
    public Position getPris(){
	return pris;
    }

    /**
     * Permet de valider le coup
     */    
    public void estValide(){
	valide = true;
    }

    /**
     * Permet d'invalider le coup
     */    
    public void estNonValide(){
	valide = false;
    }


    /**
     * retourne une chaine de caractères qui représente le résulatat d'une action.
     * @return chaine de caractère qui indique si le déplacement est valide et s'il a provoqué la prise d'un pion (et sa position)
     */    
    public String toString(){
	String chaine = "Le coup est ";
	if (!valide) chaine+="non ";
	chaine+="valide. ";
	if (pris != null) {
	    chaine+= "Pion pris : ";
	    chaine+= pris.toString();
	}
	return chaine;
    }
    

}