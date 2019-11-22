package iut.dames.damier;

/**
 * Représente un déplacement entre deux positions.
 */
public class Deplacement{

    Position depart, arrive;

    /**
     * Crée une nouvelle instance de Deplacement à partir de deux positions
     * @param depart position de départ
     * @param arrive position d'arrivée
     */    
    public Deplacement(Position depart, Position arrive){
	this.depart = depart;
	this.arrive = arrive;
    }

    /**
     * retourne la position de départ
     * @return La position de départ
     */    
    public Position getDepart(){
	return depart;
    }

    /**
     * retourne la position d'arrivée
     * @return la position d'arrivée
     */    
    public Position getArrive(){
	return arrive;
    }

    /**
     * retourne une chaine qui décrit le déplacement
     * @return un chaine de caractère qui décrit le déplacement
     */    
    public String toString(){
	return "deplacement de "+depart+" vers "+arrive;
    }

}