package iut.dames.arbre;

import iut.dames.damier.Damier;

import java.util.ArrayList;
import java.util.List;


//Défini le noeud d'un arbre n-aire 
// L'arbre contient une valeur entière (int) et un objet 

/**
 * Représente le noeud d'un arbre n-aire. A chaque noeud a éventuellement un père et/ou éventuellement un ou plusieurs fils. A chaque noeud est également attaché un objet (une instance de la classe Object).
 * <p>
 * Un noeud qui n'a pas de père est une racine, un noeud qui n'a pas de fils est une feuille.
 * <p>
 * La liste des fils est stockée dans le noeud sous forme d'une liste (un objet ArrayList).
 */
public class Noeud {


    // sa valeur
    int nbFils;
    // son père
    private Noeud pere;
    // ses fils
    private List<Noeud> lesFils;
    // l'état du damier
    private Object object;

    // une racine 

    /**
     * Crée une nouvelle instance de noeud avec pour paramètre un objet. Le noeud n'a pas de père, c'est une racine.
     *
     * @param object L'objet qu'on attache au noeud
     */
    public Noeud(Object object) {
        pere = null;
        this.object = object;
        lesFils = new ArrayList<Noeud>();
        nbFils = 0;
    }

    // avec un père 

    /**
     * Crée une nouvelle instance de noeud avec comme paramètre, un objet et une autre instance de Noeud (son père)
     *
     * @param object Instance de l'objet qu'on attache au noeud
     * @param pere   Un autre instance de noeud (son père)
     */
    public Noeud(Object object, Noeud pere) {
        this.pere = pere;
        this.object = object;
        lesFils = new ArrayList<Noeud>();
        nbFils = 0;
        pere.ajouteFils(this);
    }

    /**
     * Permet de récupérer l'ensemble des fils d'un noeud.
     *
     * @return la liste des noeud sous la forme d'une List.
     */
    public List getLesFils() {
        return lesFils;
    }

    /**
     * permet de récupérer une référence sur l'objet stocké dans le noeud.
     *
     * @return la référenc de l'objet stocké dans le noeud
     */
    public Object getObject() {
        return object;
    }

    /**
     * permet d'affecter un objet au noeud.
     *
     * @param object L'objet attaché au noeud.
     */
    public void setObject(Object object) {
        this.object = object;
    }

    public Damier getGameBoard() {
        return (Damier) object;
    }

    /**
     * Permet d'affecter un fils au noeud courant
     *
     * @param fils une autre instance de Noeud qui va devenir le fils
     */
    public void ajouteFils(Noeud fils) {
        lesFils.add(fils);
        nbFils++;
    }

    /**
     * Permet de retirer un fils dont on donne la référence
     *
     * @param fils fils qu'on veut supprimer
     */
    public void retireFils(Noeud fils) {
        if (lesFils.remove(fils)) nbFils--;
    }

    /**
     * permet de récupérer une référence sur l'objet stocké dans le noeud
     *
     * @return une référence sur l'objet stocké.
     */
    public Object getValue() {
        return object;
    }

    /**
     * Le père du noeud
     *
     * @return Le père du noeud
     */
    public Noeud getPere() {
        return pere;
    }

    /**
     * Pemet de connaitre le nombre de fils que possède un noeud.
     *
     * @return nombre de fils
     */
    public int getNbFils() {
        return nbFils;
    }

    /**
     * retourne le premier fils du noeud
     *
     * @return si le noeud a au moins un fils, la méthode retourne le premier fils.
     */
    public Noeud getFils() {
        if (lesFils.size() > 0)
            return lesFils.get(0);

        // sinon
        return null;
    }

    /**
     * retourne le n-ieme fils s'il existe
     *
     * @param index l'index du fils
     * @return le n-ième fils, s'il existe, null sinon
     */
    public Noeud getFils(int index) {
        if (index >= 0 && lesFils.size() > index)
            return lesFils.get(index);

        // sinon
        return null;
    }

    /**
     * Pemet de connaitre le frère suivant du noeud, s'il existe. Le frère suivant est défini comme un autre fils du père du noeud courrant.
     * <p>
     * L'indice du frère suivant est le successeur de l'indice du noeud courrant au niveau du père.
     *
     * @return une référence sur le frère suivant.
     */
    public Noeud getFrereSuivant() {
        // trouver l'indice du noeud (intéroger son père)
        int rang = pere.getLesFils().indexOf(this);


        if ((rang + 1) < pere.getNbFils())
            return pere.getFils(rang + 1);

        // sinon
        return null;

    }

    // renvoie la hauteur du noeud 
    // 0 pour la racine
    // hauteur du père + 1 pour les autres

    /**
     * Permet de connaitre la hauteur d'un noeud. Elle est définie comme suit :
     * 0 pour une racine
     * sinon, c'est la hauteur du père + 1
     *
     * @return la hauteur
     */
    public int getHauteur() {
        if (estRacine()) {
            return 0;
        } else {
            return getPere().getHauteur() + 1;
        }
    }

    /**
     * retourne vrai si le noeud est une racine (s'il n'a pas de père)
     *
     * @return vrai si c'est une racine
     */
    public boolean estRacine() {
        return getPere() == null;
    }

    /**
     * retourne vrai si le noeud est une feuille (il n'a pas de fils)
     *
     * @return vrai si le noeud est une feuille
     */
    public boolean estFeuille() {
        return getNbFils() == 0;
    }

    /**
     * retourne un description du noeud
     *
     * @return une chaine qui décrit le noeud
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("valeur du noeud = ");
        sb.append(object.toString());
        return sb.toString();
    }

}