/*
 * damier.java
 *
 * Created on 7 mai 2004, 23:08
 */

package iut.dames.damier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;




/**
 * Représente un damier
 * @author Samuel Delepoulle
 */
public class Damier {
    
    private final int maxX, maxY;
    private Case[][] damier;

    /**
     * Crée une nouvelle instance de damier en connaissant la largeur et la hauteur, 
     * les cases sont remplies aléatoirement 
     * @param x nombre de cases en largeur
     * @param y nombre de cases en hauteur
     */
    // x = nombre de colonnes
    // y = nombre de lignes
    // le contenu des cases est rempli aléatoirement par des entiers (de -2 à +2 ).
    // (voir codage des cases).
    public Damier(int x, int y) {
        maxX = x;
        maxY = y;
        damier = new Case[x][y];
        for (int i=0; i<x; i++){
            for (int j=0; j<y; j++){
                damier[i][j] = new Case();
                if ((i+j)%2 == 0) {		    
		    damier[i][j].setColor(Color.GRAY);		
		    int alea = (int)(Math.random()*5.0)-2;
		    //System.out.println(alea);
		    damier[i][j].setContenu(alea);
		}
                else {
		    damier[i][j].setColor(Color.WHITE);
		}
            }
        }
    }

    
    /**
     * Crée une nouvelle instance de damier en connaissant la largeur, la hauteur
     * et le nombre de lignes remplies (le jeu de dame standard est défini par les
     * new Damier(10, 10, 4)
     * @param x largeur du damier
     * @param y hauteur du damier
     * @param lignesRemplies nombre de lignes de jeu remplies (4 pour le jeu standard)
     */
    // x = nombre de colonnes
    // y = nombre de lignes
    // rang = nombre de lignes remplies
    public Damier(int x, int y, int lignesRemplies) {
        maxX = x;
        maxY = y;
        damier = new Case[x][y];
        for (int i=0; i<x; i++){
            for (int j=0; j<y; j++){
                damier[i][j] = new Case();
                if ((i+j)%2 == 1) {
		    damier[i][j].setColor(Color.GRAY);
		    if (j<lignesRemplies) damier[i][j].setContenu(-1);
		    if (j>=(y-lignesRemplies)) damier[i][j].setContenu(1);
		}
                else {
		    damier[i][j].setColor(Color.WHITE);
		}
            }
        }
    }


    // permet d'initialiser un damier avec deux tableaux de position pour les 
    // noirs et blancs.
    /**
     * Crée une nouvelle instance de damier (de largeur et hauteur donnée)
     * à partir de deux tableaux de positions qui représentent les positions
     * des pions des deux joueurs
     * @param x largeur du damier
     * @param y hauteur du damier
     * @param blanc tableau de position des pions blancs
     * @param noir tableau de position des pions noirs
     */    
    public Damier(int x, int y, Position[] blanc, Position[] noir){

        maxX = x;
        maxY = y;
        damier = new Case[x][y];
        for (int i=0; i<x; i++){
            for (int j=0; j<y; j++){
                damier[i][j] = new Case();
                if ((i+j)%2 == 1) {
		    damier[i][j].setColor(Color.GRAY);
		}
                else {
		    damier[i][j].setColor(Color.WHITE);
		}
            }
        }
	
	for (int i=0; i< blanc.length; i++){
	    setContenu(blanc[i],1);
	}

	for (int i=0; i< noir.length; i++){
	    setContenu(noir[i],-1);
	}
	
    }

    /**
     * Crée une nouvelle instance de Damier à partir d'une instance existante
     * @param damierOrigine Un damier existant
     */    
    public Damier(Damier damierOrigine){
	maxX = damierOrigine.getMaxX();
	maxY = damierOrigine.getMaxY();
	damier = new Case[maxX][maxY];
	for (int i=0; i<maxX; i++){
	    for (int j=0; j<maxY; j++){
		damier[i][j] = new Case(damierOrigine.getColor(i,j),
					damierOrigine.getContenu(i,j),
					damierOrigine.isSelected(i,j));
	    }
	}    	
    }
    
    /**
     * Permet de vider completement le damier (plus aucun pion ou dame dessus)
     * 
     */
    public void videDamier(){
        for (int i=0; i<maxX; i++){
	    for (int j=0; j<maxY; j++){
                damier[i][j].setContenu(0);
	    }
	}    	
    }
    
    /**
     * permet de connaitre la largeur du damier
     * @return largeur du damier
     */    
    public final int getMaxX(){
	return maxX;
    }

    /**
     * permet de connaitre la hauteur du damier
     * @return hauteur du damier
     */    
    public final int getMaxY(){
	return maxY;
    }


    /**
     * permet d'accéder à une case à partir de ses coordonnées
     * @param x coordonnée en x
     * @param y coordonnée en y
     * @return la case du damier
     */    
    public Case getCase(int x, int y){
        if ((x<maxX) && (x>=0) && (y<maxY) && (y>=0))
            return damier[x][y];
        else return null;
    }

    /**
     * permet d'accéder à une case du damier en fonction de sa position
     * @param p position de la case
     * @return case de position p
     */    
    public Case getCase(Position p){
	int x = p.getX();
	int y = p.getY();
        if ((x<maxX) && (x>=0) && (y<maxY) && (y>=0))
            return damier[x][y];
        else return null;
    }
    
    /**
     * permet d'accéder à la couleur d'une case en fonction de ses
     * coordonnées
     * @param x coordonnée en x
     * @param y coordonnée en y
     * @return la couleur de la case de coordonnées x, y
     */    
    public Color getColor(int x, int y){

	return (damier[x][y]).getColor();
        //if ((x+y)%2 == 0)
        //    return Color.BLACK;
        //else return Color.WHITE;
    }

    /**
     * permet d'accéder au contenu (pion, dame, vide) d'une case en fonction de
     * ses coordonnées. Le contenu d'une case est codée sur un int (voir la classe
     * Case).
     * @param x coordonnée en x
     * @param y coordonnée en y
     * @return le contenu d'une case
     */    
    public int getContenu(int x,int y){
	return (damier[x][y]).getContenu();
    }

    /**
     * Permet de fixer le contenu d'une case dont les coordonnées sont connues
     * @param x coordonnée en x
     * @param y coordonnée en y
     * @param contenu contenu codé sur un int (voir classe Case)
     */    
    public void setContenu(int x,int y, int contenu){
	(damier[x][y]).setContenu(contenu);
    }

    /**
     * permet d'accéder au contenu d'une case en fonction de sa position
     * @param p position de la case
     * @return contenu (codé sur un int : voir classe Case)
     */    
    public int getContenu(Position p){
	return (damier[p.getX()][p.getY()]).getContenu();
    }

    /**
     * permet de fixer le contenu d'une case dont on connait la position
     * @param p position de la case dont on veut fixer le contenu
     * @param contenu contenu codé sur un int (voir la classe Case)
     */    
    public void setContenu(Position p, int contenu){
	(damier[p.getX()][p.getY()]).setContenu(contenu);
    }

    /**
     * permet de savoir si une case définie par ses coordonnées est sélectionnée
     * @param x coordonnée en x de la case
     * @param y coordonnée en y de la case
     * @return un booléen qui code si la case est sélectionnée (true = sélectionnée,
     * false = non sélectionnée)
     */    
    public boolean isSelected(int x, int y){
	return (damier[x][y]).isSelected();
    }

    /**
     * permet de sélectionner (ou de retirer la sélection) d'une case
     * définie par ses coordonnées
     * @param x coordonnée en x
     * @param y coordonnée en y
     * @param selected booléen qui indique si la case doit être sélectionnée
     */    
    public void setSelected(int x, int y, boolean selected){
	(damier[x][y]).setSelected(selected);
    }

    /**
     * permet de connaitre si un case définie par sa position est sélectionnée
     * ou non
     * @param p position de la case
     * @return un booléen qui indique si la case est sélectionnée
     */    
    public boolean isSelected(Position p){
	return (damier[p.getX()][p.getY()]).isSelected();
    }

    /**
     * Permet de sélectionner (ou de retirer la sélection) d'une case
     * définie par sa position
     * @param p défini la position de la case
     * @param selected booléen qui code si la case doit être sélectionnée
     */    
    public void setSelected(Position p, boolean selected){
	(damier[p.getX()][p.getY()]).setSelected(selected);
    }


    /**
     * renvoie un chaine de caractère qui représente un damier (un tableau de cases).
     * @return Chaine de caractères qui représente l'état du damier (toutes les cases)
     */    
    public String toString(){
        String affiche = new String("\n");
        for (int j=0; j<maxY; j++){
            for (int i=0; i<maxX; i++){
                affiche += damier[i][j].toString()+"|";                
            }
            affiche += "\n";
        }
       return affiche; 
    }

    /**
     * Retourne sous la forme d'une List la liste des positions des pions d'un joueur (utilisé
     * par les joueurs IA). Cette liste contient normalement uniquement des
     * instances de la classe position
     * @param joueur joueur dont on veut récupérer la liste des pions
     * @return La liste des position des pions
     */    
    public List<Position> listePositionPion(int joueur){
	List<Position> listePos = new ArrayList<>();
	for (int i=0; i<maxX; i++){
	    for (int j=0; j<maxY; j++){
		// si le contenu et le joueur sont de même signe
		if (getContenu(i,j)*joueur >0 )
		    listePos.add(new Position(i,j));
	    }
	}
	return listePos;
    }
    
    /**
     * Position perdante.
     * Est définie comme position perdante toute configuration du damier soit lorsque :
     * - tous les pions d'un joueur sont perdus
     * - aucun pion du joueur ne peut jouer un coup valide
     * @param joueur
     * @return 
     */
    public boolean perdu(int joueur){
        
        List<Position> v = listePositionPion(joueur); 
        //System.out.println(v.size());
        
        if (v.isEmpty()) return true;
        
        for (Position p : v){
            //System.out.println(Coup.listeArriveValide(this, p, joueur, false).size());
            if (!Coup.listeArriveValide(this, p, joueur, false).isEmpty()){
                return false;
            }
        }
        
        return true;
    }

    /**
     * Permet de désélectionner toutes les cases.
     */
    void unSelectAll() {
       	for (int i=0; i<maxX; i++){
	    for (int j=0; j<maxY; j++){
		
		setSelected(i,j,false);

	    }
	} 
    }

    
    /**
     * Méthode de réinitialisation du damier.
     * 
     * @param lignesRemplies 
     */
    public void init(int lignesRemplies) {
        for (int i=0; i<maxX; i++){
            for (int j=0; j<maxY; j++){
                damier[i][j] = new Case();
                if ((i+j)%2 == 1) {
		    damier[i][j].setColor(Color.GRAY);
		    if (j<lignesRemplies) damier[i][j].setContenu(-1);
		    if (j>=(maxY-lignesRemplies)) damier[i][j].setContenu(1);
		}
                else {
		    damier[i][j].setColor(Color.WHITE);
		}
            }
        }
    }
}