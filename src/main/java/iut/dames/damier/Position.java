// Classe qui permet de représenter une position 
// Ici la position est simplement un couple x,y de
// valeurs entières

package iut.dames.damier;

/**
 * Définit une position (x et y) en entiers (des cases)
 */
public class Position{
    private int x,y;
    
    /**
     * Crée une nouvelle instance de Position (x=0 et y=0)
     */    
    public Position(){
	x = 0;
	y = 0;
    }

    /**
     * Crée une nouvelle instance de Position en fonction des coordonnées passées en paramètres
     * @param x coordonnée en x
     * @param y coordonnée en x
     */    
    public Position(int x, int y){
	this.x = x;
	this.y = y;
    }

    /**
     * Crée une nouvelle instance de Position à partir d'une position existante
     * @param p position existante
     */    
    public Position(Position p){
	this.x = p.getX();
	this.y = p.getY();
    }

    /**
     * Permet de connaitre la coordonnée en x de la position
     * @return coordonnée en x
     */    
    public int getX(){
	return x;
    }

    /**
     * Permet de connaitre la coordonnée en y de la position
     * @return coordonnée en y
     */    
    public int getY(){
	return y;
    }

    /**
     * une chaine de caractères qui représente la position (x, y)
     * @return chaine qui représente la position
     */    
    public String toString(){
	return x+" ; "+y;
    }

    /**
     * Permet de tester si l'instance courante de position est égale à un objet passé en paramètre
     * @param obj objet dont on veut tester l'égalité avec l'instance courante
     * @return vrai si les position sont égales.
     */    
    public boolean equals(Object obj){
	if (((Position)obj).getX() != getX()) return false;
	if (((Position)obj).getY() != getY()) return false;
	return true;
    }

}