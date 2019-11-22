package iut.dames.damier;

import java.awt.Color;
import java.util.List;



/**
 * Permet de représenter un joueur humain
 */
public class Humain extends Joueur{
   
    int joueur;

    boolean select, unePrise, tourTermine = false;

    Position depart, arrive;

    Damier damier;

    List listeCoups;

    ResultatAction resultatAction;

    /**
     * Crée une nouvelle instance de Joueur
     * @param joueur spécifie la couleur du joueur (<0 pour noir et >0 pour blanc)
     */    
    public Humain(int joueur){
	super(joueur, Joueur.HUMAIN);
	this.joueur = joueur;
    }

    /**
     * Méthode appelée automatiquement par l'interface graphique. Cette méthode envoie certaines informations
     * @param damier Le damier sur lequel on travaille
     * @param position La position qui a été "cliquée"
     * @return Le damier après sélection
     */    
    public Damier selectPosition(Damier damier, Position position){

	System.out.println("On passe à la selection des pions. select = "+select);
        
        //damier.unSelectAll();
        
        Case.setColorSelected(Color.RED);
        
	if (!select){
            damier.unSelectAll();
	    //System.out.println("Le joueur "+joueur+" prend un pion");
	    
	    // si la case contient un pion du joueur actif
	    if (damier.getCase(position).getContenu()*joueur > 0){
		
		tourTermine = false;
		depart = position;
		select = true;
		damier.setSelected(depart,true);
		unePrise = false;

		//récupère la liste des coups possibles
		listeCoups = Coup.listeArriveValide(damier, depart,
						    joueur, unePrise);
                
                System.out.println("et voici la liste des coups possibles "+listeCoups);
                System.out.println("=> Liste des arrivées possibles "+Coup.listeArriveValide(damier, depart, joueur, unePrise));

		// active les cases possibles
		for (int i=0; i<listeCoups.size(); i++){
		    damier.setSelected((Position)listeCoups.get(i), true);
		}
	    }
	}

	// selection de la case d'arrivée

	else{

	    //System.out.println("Le joueur "+joueur+" déponse un pion");
            System.out.println("Liste des coups possible " +listeCoups);
            
            
	    // on retire les sélections des cases possibles
	    for (int i=0; i<listeCoups.size(); i++){
		damier.setSelected((Position)listeCoups.get(i), false);
	    }

	    // si départ = arrivée on repose le pion (sauf si prise)
	    if (position.equals(depart) ){
		if (unePrise){
		    tourTermine = true;		 
		}
		select = false;
		damier.setSelected(depart,false);
		depart = null;
	    }
	    else{
		arrive = position;
		Deplacement deplacement = new Deplacement(depart,arrive);
                
		resultatAction = Coup.valide(damier, deplacement, joueur, unePrise);
		if (resultatAction.getValide()){

		    damier = Coup.execute(damier, deplacement, resultatAction);

		    System.out.println("* Un humain joue ");
		    System.out.println("son déplacement : "+deplacement+" ==> "+resultatAction);

		    System.out.println("Pion pris : "+resultatAction.getPris());


                    unePrise = resultatAction.getPris() != null;

		    damier.setSelected(depart,false);

		    if (unePrise){

                        List encore = Coup.listeArriveValide(damier, position, joueur, unePrise);
                        if (encore.isEmpty()){
                            select = false;		      
                            tourTermine = true;
                        }
                        else{
                            depart = position;
                            damier.setSelected(depart,true);
                        }
                        
		    }
		    else{                        
			select = false;		      
			tourTermine = true;
		    }
		}
	    }
	}
	return damier;
    }

    /**
     * Méthode qui permet de savoir si le joueur a fini de joueur (son tour est terminé)
     * @return booléen qui indique si le coup est terminé ou non
     */    
    public boolean finiDeJouer(){
	if (tourTermine) return true;
	return false;
    }

    /**
     * Permet d'indiquer que le joueur a terminé son tour
     * @param tourTermine booléen qui indique que le tour est terminé
     */    
    public void setFiniDeJouer(boolean tourTermine){
	this.tourTermine = tourTermine;
    }

    /**
     * Retourne une chaine qui décrit le joueur
     * @return chaine caractéristique du joueur
     */    
    public String toString(){
	return "Humain joueur:"+joueur+" fini de jouer : "+tourTermine;
    }

}