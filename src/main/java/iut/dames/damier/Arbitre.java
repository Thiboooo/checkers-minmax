package iut.dames.damier;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


//import ia.Ia;
//import ia.MiniMax;


public class Arbitre extends Thread{

    Joueur joueurActif;

    Position depart, arrive;

    List listeCoups;

    ResultatAction resultatAction;

    private boolean select = false;

    private boolean unePrise = false;

    // donne des messages sur la partie
    private boolean verbeux = false;

    // affiche le damier à chaque tour
    private boolean afficheDamier = false;

    private int vainqueur = 0;

    private boolean fini = false;

    private int tour = 0;

    private boolean nul = false;

    private Damier damier;

    boolean jeuActif = true;

    Joueur[] lesJoueurs = new Joueur[2];

    private boolean IAWait;

    // temps maximal de réponse pour une IA (en millisecondes)
    // 0 = pas de timeOut
    private int timeOut = 5000;
    private final int MAX_TOURS = 400;
    private boolean restart = false;
    private final int x;
    private final int y;

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isIAWait() {
        return IAWait;
    }

    /** Retourne le résultat du match.
     * Retourne +1 si le match est gagné par blanc, -1 si le match est gagné par noir,
     * 0 si c'est un match nul.
     *
     * Si le match n'est pas fini, une exception est lancée.
     *
     * @return
     */

     public int getVainqueur() {
         if (!fini){
             throw new UnsupportedOperationException("Le match n'est pas fini.");
         }
        return vainqueur;
    }
    /**
     * Afficher le damier à chaque tour
     * @param afficheDamier
     */
    public void setAfficheDamier(boolean afficheDamier) {
        this.afficheDamier = afficheDamier;
    }


    /**
     * Constructeur avec dimention du damier
     * @param x largeur du damier
     * @param y hauteur du damier
     */
    public Arbitre(int x, int y){
        this.x = x;
        this.y = y;
        // les blancs commencent
        damier = new Damier(x,y,4);

    }

    public void setJoueur(int number, Joueur j){
        lesJoueurs[number] = j;
    }

    /**
     * Permet de récupérer le damier utilisé par l'arbitre
     * @return Le damier de l'arbitre
     */
    public Damier getDamier(){
        return damier;
    }

    /**
     * Permet de savoir si la partie est active ou non (terminée).
     * @return l'état de la partie
     */
    public boolean getJeuActif(){
        return jeuActif;
    }

    // force l'arbitre a expliquer son choix (pour le déboggage)
    /**
     *
     * @param verbeux
     */
    public void setVerbeux(boolean verbeux){
        this.verbeux = verbeux;
    }


    /**
     * Méthode match donne la main à tour de rôle aux joueurs.
     *
     * La méthode match est appelée
     * - par la méthode run (en version thread)
     * - dirtectement (en version séquentielle)
     *
     */
    public void match(){
        damier.init(4);
        this.jeuActif = true;
        this.fini = false;
        if (verbeux)
            {
                System.out.println("**********************");
                System.out.println(lesJoueurs[0] + " contre " + lesJoueurs[1]);
                System.out.println("**********************");
            }

        int i=0;
        int tour = 1;
	boolean joue = true;

        while(jeuActif){

            if (verbeux) {


                if (i == 0) {
                    System.out.println("Au tour des blancs");
                } else {
                    System.out.println("Au tour des noirs");
                }
                System.out.println("tour = " + tour);
            }

            if (afficheDamier){
                System.out.println("damier = " + damier);
            }

            joueurActif = lesJoueurs[i];

            if (verbeux) {
                System.out.println(joueurActif);
            }

            if (joueurActif.getType() == Joueur.HUMAIN){
                Humain humain = (Humain)joueurActif;
                humain.setFiniDeJouer(false);

                do{
                    try{
                        sleep(50);
                    } catch(InterruptedException e)
			{System.out.println("Problème de thread...");}
                } while(!humain.finiDeJouer());
            }
            else{

                IAWait = true;

                try {
                    long debut = System.nanoTime();
		    damier = ((Machine)joueurActif).joue(damier);
                    long duree = (System.nanoTime() - debut)/1000000;


                    if (duree > timeOut && timeOut !=0 ){
                        System.out.println("IA du joueur "+i+" trop lente");
                        jeuActif = false;
                    }
                    if (verbeux) {
                        System.out.println("temps de réponse = " + duree);
                    }

		} catch(NullPointerException e) {
                    if (verbeux) {
                        System.out.println("Partie terminée");
                        System.out.println("c'était le tour des "+i);

                        System.err.println(e);
                        e.printStackTrace();
                        System.out.println("victoire par KO !");
                    }
                    jeuActif = false;

                    if (i==0) vainqueur = -1;
                    if (i==1) vainqueur = 1;

                }
                IAWait = false;
            }

            if (damier.perdu(1)) {
                System.out.println("!!! Les noirs remportent la partie !!! Vainqueur = "+lesJoueurs[1]);
                vainqueur = -1;
                jeuActif = false;

            }
            if (damier.perdu(-1)){
                System.out.println("!!! Les blancs remportent la partie !!! Vainqueur = "+lesJoueurs[0]);
                vainqueur = 1;
                jeuActif = false;
            }


            i++;
            if (i>1) i=0;

            tour++;

            if (tour>=MAX_TOURS){
                vainqueur = 0;
                jeuActif = false;
                System.out.println("Fin de partie car on dépasse "+MAX_TOURS+" coups");
            }

            /*
            try{
                sleep(1000);
            } catch(InterruptedException e){System.out.println("Problème de thread...");}
            */
        }
        fini = true;

        System.out.println("C'est fini...");

        if (afficheDamier){
                System.out.println("damier = " + damier);
            }
    }


    /**
     * Thread principal de l'arbitre.
     *
     * N'appelle que la méthode match, ce qui permet d'utiliser la méthode match
     * en version séquentielle (non threadée).
     */
    public void run(){

        while(true){
           System.out.println("c'est reparti");
           this.restart = false;

            match();
            do {
                try {
                    //System.out.println("partie fini mais thread pas mourru");
                    Arbitre.sleep(100);

                } catch (InterruptedException ex) {
                   Logger.getLogger(Arbitre.class.getName()).log(Level.SEVERE, null, ex);
                }
           }while(!this.restart);

        }
    }

    public void setRestart(boolean restart) {
        this.restart = restart;
    }


    /**
     * Méthode invoquée par l'interface graphique pour indiquer quelle case a été sélectionnée
     * @param position la position sélectionnée par le biais de l'interface graphique
     */
    public void selectPosition(Position position){

        // selection de la case de départ


        if (joueurActif.getType() == Joueur.HUMAIN){

            System.out.println("Un humain doit jouer");
            damier = ((Humain)joueurActif).selectPosition(damier,position);

        }
    }

    public void vainqueur() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
