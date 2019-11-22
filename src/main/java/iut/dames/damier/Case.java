/*
 * Case.java
 *
 * Created on 7 mai 2004, 23:10
 */

package iut.dames.damier;

import java.awt.Color;

/**
 * <PRE>Définit une case d'un damier.
 *
 * Le contenu d'une case est codée sur un entier.
 *
 * dame blanche = 2
 * pion blanc = 1
 * vide = 0
 * pion noir = -1
 * dame noire = -2
 *
 *
 * </PRE>
 * @author Samuel Delepoulle
 */
public class Case {
    
    

    /**
     * Valeur pour une case vide
     */    
    public static final int VIDE = 0;
    /**
     * Valeur pour une case qui contient un pion blanc
     */    
    public static final int PION_BLANC = 1;
    /**
     * Valeur pour une case qui contient une dame blanc
     */    
    public static final int DAME_BLANCHE = 2;
    /**
     * Valeur pour une case qui contient un pion noir
     */    
    public static final int PION_NOIR = -1;
    /**
     * Valeur pour une case qui contient une dame noire
     */    
    public static final int DAME_NOIRE = -2;    
    
    private Color c;
    private int contenu = VIDE;
    private boolean selected = false;
    
    // couleur de la case lorsqu'elle est sélectionnée
    private static Color colorSelected = Color.RED;

    public static Color getColorSelected() {
        return colorSelected;
    }

    public static void setColorSelected(Color colorSelected) {
        Case.colorSelected = colorSelected;
    }

    /**
     * Constructeur pour une case vide
     */    
    public Case(){
	// c'est utile !
    }

    
    /**
     * Crée une nouvelle instance de case en précisant la couleur, son contenu et si elle sélectionnée
     * @param c La couleur de la case
     * @param contenu le contenu de la case (codée sous la forme d'un int)
     * @param selected Indique si la case est sélectionnée ou non (essentiellement utilisé par l'affichage)
     */
    public Case(Color c, int contenu, boolean selected) {
	this.c = c;
	this.contenu = contenu;
	this.selected = selected;
    }

    /**
     * Permet d'attribuer une couleur à la case. La couleur est une constante telle que définie dans la classe Color
     * @param c La couleur de la case
     */    
    public void setColor(Color c) {
        this.c = c;
    }
    
    /**
     * Permet de connaitre la couleur d'une case.
     * @return La couleur affectée à la case (null si aucune valeur n'a été affectée)
     */    
    public Color getColor(){
        return c;
    }
    
    /**
     * <PRE>Retourne une chaine de caractère (qui contient un seul caractère) pour décrire le contenu de la case
     *
     * B = dame blanche
     * b = pion blanc
     * n = pion noir
     * N = dame noire
     * </PRE>
     * @return La case sous forme de chaine de caractère
     */    
    public String toString(){
        if (contenu == -1) return "n";
	if (contenu == 1) return "b";
	if (contenu == -2) return "N";
	if (contenu == 2) return "B";
        return " ";
    }

    /**
     * permet d'affecter un contenu à la case
     * @param contenu le contenu souhaité pour la case
     * @throws RuntimeException Exception retournée si on tente d'affecter une valeur qui ne représente pas un état vailide
     * pour le damier.
     */    
    public void setContenu(int contenu) throws RuntimeException{
	if ((contenu>=-2) || (contenu<=2))
	    this.contenu = contenu;
	else throw new RuntimeException("Contenu non valide");
    }

    /**
     * Permet de connaitre le contenu de la case
     * @return le contenu de la case
     */    
    public int getContenu(){
	return contenu;
    }

    /**
     * vrai si la case est sélectionnée faux sinon
     * @return vrai si la case est sélectionnée
     */    
    public boolean isSelected(){
	return selected;
    }

    /**
     * Permet de sélectionner ou de désélectionner la case
     * @param selected état souhaité
     */    
    public void setSelected(boolean selected){
	this.selected = selected;
    }
    
}


