package iut.dames.arbre;

/**
 * Une extention du Noeud pour être utilisé par l'algorithme MiniMax. En plus de ses attributs habituels,
 * un noeud possède une valeur (valeur de jeu) et appartient à un joueur (un int positif ou négatif
 * indique le joueur qui possède le noeud.
 * Il existe de plus certaines méthodes qui permettent de faire des opérations sur les arbres MiniMax
 * (trouver le meilleur fils ou le plus mauvais, calculer les valeurs de jeu ...)
 */
public class NoeudMiniMax extends Noeud {

    //public static int compte = 0;

    // la valeur de jeu pour le noeud
    private int valeur;

    private int joueur;

    private boolean estCalcule = false;

    // surcharge des constructeurs

    /**
     * Permet de créer une nouvelle instance de NoeudMiniMax avec un objet
     *
     * @param object l'objet attaché au noeud
     */
    public NoeudMiniMax(Object object) {
        super(object);
        //System.out.println(compte++);

    }

    /**
     * Permet de créer une nouvelle instance de NoeudMiniMax avec pour paramètre, l'objet à attacher et le père du noeud.
     *
     * @param object l'objet attaché
     * @param pere   le père du noeud (on suppose que c'est aussi un noeud minimax
     */
    public NoeudMiniMax(Object object, NoeudMiniMax pere) {

        super(object, pere);
        //System.out.println(compte++);


// 	// on ne peut ajouter que des fils Minimax !
// 	if (pere.getClass() == NoeudMiniMax.class)
// 	    super(object,pere);

// 	else throw new RuntimeException
// 	    ("le père d'un NoeudNiniMax doit être un Noeud MiniMax");	

    }

    //surcharge des méthodes de Noeud

    /**
     * Ajoute un fils. La méthode vérifie si le fils est bien une instance de la classe
     * NoeudMiniMax. Dans le cas contraire, une exception est levée.
     *
     * @param fils Le fils
     * @throws RuntimeException Exception levée dans le cas ou l'objet n'est pas une instance de la classe NoeudMiniMax
     */
    public void ajouteFils(Noeud fils) throws RuntimeException {

        // on ne peut ajouter que des fils Minimax !
        if (fils.getClass() == NoeudMiniMax.class)
            super.ajouteFils(fils);

        else throw new RuntimeException
                ("le fils d'un NoeudNiniMax doit être un Noeud MiniMax");
    }

    /**
     * Permet de récupérer la valeur de jeu de l'instance courante
     *
     * @return valeur de jeu stockée dans le noeud
     */
    public int getValeur() {
        return valeur;
    }

    /**
     * Permet d'affecter à l'instance courrante une valeur de jeu passée en paramètre
     *
     * @param valeur valeur de jeu
     */
    public void setValeur(int valeur) {
        this.valeur = valeur;
        estCalcule = true;
    }

    /**
     * Récupère la couleur du joueur
     *
     * @return couleur du joueur
     */
    public int getJoueur() {
        return joueur;
    }

    /**
     * Affecte le noeud à un joueur.
     * Nombre <0 pour noir et >0 pour blanc
     *
     * @param joueur valeur qui caractérise un joueur
     */
    public void setJoueur(int joueur) {
        this.joueur = joueur;
    }

    /**
     * Permet de savoir si la valeur de jeu a été calculée ou non pour le noeud courrant. Typiquement,
     * pour MiniMax, on commence par affecter les valeurs de jeu pour les feuille et ensuite,
     * on remonte jusqu'au racine pour calculer les valeurs de jeu en fonction des branches.
     *
     * @return booléen qui indique si le noeud a déjà été traité ou non
     */
    public boolean getEstCalcule() {
        return estCalcule;
    }


    /**
     * permet de retrouver la valeur du meilleur des fils
     *
     * @return valeur du meilleur fils
     */
    public int maxFils() {

        //System.out.println("appel de maxFils");

        if (estFeuille()) return getValeur();

        // ce n'est pas une feuille
        int valeur = ((NoeudMiniMax) getFils(0)).getValeur();

        if (getNbFils() == 1) return valeur;

        // il y a plus d'un fils
        int max = valeur;

        for (int i = 1; i < getNbFils(); i++) {
            valeur = ((NoeudMiniMax) getFils(i)).getValeur();
            if (valeur > max) max = valeur;
        }

        //System.out.println("max a trouvé : "+max);

        return max;

    }

    // calcule l'indice du meilleur fils (si il est unique)
    // l'indice de l'un des meilleurs fils en cas d'égalité

    /**
     * Permet de retrouver l'indice du meilleur fils
     *
     * @return indice du meilleur fils.
     */
    public int indiceMaxFils() {


        if (estFeuille()) return -1;

        // ce n'est pas une feuille
        int valeur = ((NoeudMiniMax) getFils(0)).getValeur();

        if (getNbFils() == 1) return 0;

        // il y a plus d'un fils
        int max = valeur, indice = 0;

        for (int i = 1; i < getNbFils(); i++) {
            valeur = ((NoeudMiniMax) getFils(i)).getValeur();
            if (valeur > max) {
                max = valeur;
                indice = i;
            }
        }

        //System.out.println("max a trouvé : "+max);

        return indice;

    }

    /**
     * Permet de trouver la valeur du moins bon fils
     *
     * @return valeur du moins bon fils
     */
    public int minFils() {

        //System.out.println("appel de minFils");

        if (estFeuille()) return getValeur();

        // ce n'est pas une feuille
        int valeur = ((NoeudMiniMax) getFils(0)).getValeur();

        if (getNbFils() == 1) return valeur;

        // il y a plus d'un fils
        int min = valeur;

        for (int i = 1; i < getNbFils(); i++) {
            valeur = ((NoeudMiniMax) getFils(i)).getValeur();
            if (valeur < min) min = valeur;
        }

        return min;

    }


    // Calcul les valeurs des noeuds (on suppose ques les
    // valeurs des feuilles sont valides.

    /**
     * Algorithme de calcule des valeurs MiniMax (les valeurs des feuilles sont "remontées" jusqu'aux racines
     */
    public void calculeValeur() {

        //System.out.println(this);

        if (!estFeuille()) {
            for (int i = 0; i < getNbFils(); i++) {
                ((NoeudMiniMax) getFils(i)).calculeValeur();
            }
            if (getJoueur() <= 0) {
                valeur = maxFils();
                //System.out.println("On a executé maxFils()");
            } else
                valeur = minFils();
            //System.out.println(valeur);
        }
    }

    @Override
    public String toString() {
        return "NoeudMiniMax{" +
                "valeur=" + valeur +
                ", joueur=" + joueur +
                ", nbFils=" + nbFils +
                '}';
    }
}