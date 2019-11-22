package iut.dames.damier;

import java.util.ArrayList;
import java.util.List;


/**
 * Regroupe des méthodes de classe pour manipuler des coups (déplacements de pions suivis d'actions).
 * Toutes les méthodes sont des méthodes de classe.
 */
public class Coup{

    //private static ResultatAction resultatAction;    

    private static boolean verbeux = false;

    private static boolean estSurDiagonale(Position depart, Position arrive){

	int decalX = arrive.getX() - depart.getX();
	int decalY = arrive.getY() - depart.getY();
        
        if (decalX == decalY) return true;
        if (decalX == - decalY) return true;
        if (-decalX == decalY) return true;
        if (-decalX == - decalY) return true;                   
	
	return false;

    }


    private static int nbPriseMax(Damier damier, Position depart, int joueurActif){

	Position arrive;
	Damier damierArrive;
	ResultatAction rAction;
	Deplacement deplacement;
	boolean faireTest = true;

	int nbPrise, decalX, max = 0;

	int contenuDepart = damier.getContenu(depart);
	
        int maxX = damier.getMaxX(), maxY = damier.getMaxY();
        
	for (int i=0; i<maxX; i++){
	    for (int j=0; j<maxY; j++){

		// cases noires
		if (((i+j) % 2) == 1){
		    arrive = new Position(i,j);
                    
                    if (contenuDepart == 1 || contenuDepart == -1){                        
                        decalX = depart.getX()-arrive.getX();
                        
                        if ((decalX == 2) || (decalX == -2)) 
				faireTest = true;
			    else faireTest = false;
                    }
                                        
                    if (faireTest){
                                        
                        if (estSurDiagonale(depart,arrive)){

			// on est sur la diagonale
						
			    rAction = priseValide(damier,depart, arrive, joueurActif);
			    if (rAction.getValide()){
				
				deplacement = new Deplacement(depart,arrive);				
				damierArrive = execute(damier,deplacement, rAction);				
				nbPrise = 1+ nbPriseMax(damierArrive, arrive, joueurActif);
				if (nbPrise > max) max = nbPrise;
			    }
			}
		    }
		}
	    }
	}
	
	return max;
    }


    private static int maxPriseJeu(Damier damier, int joueurActif){
	int nb,max = 0;
        int maxX = damier.getMaxX(), maxY = damier.getMaxY();
        
	for (int i=0; i<maxX; i++){
	    for (int j=0; j<maxY; j++){

		// cases noires
		if ((i+j)%2 == 1){
		    Position depart = new Position(i,j);
		    
		    if (damier.getCase(depart).getContenu() * joueurActif > 0){
			nb = nbPriseMax(damier, depart, joueurActif);
			if (nb > max) max = nb;
		    }
		}
	    }
	}
	return max;
    }


    private static Position positionPionPris(Damier damier, Position depart,
					     Position arrive, int couleur){

	boolean trouve = false;
	Position pionPris = null;

	// on suppose que c'est une diagonale
	
	if (depart.equals(arrive)) return null;

	// sinon on calcule le pas en x et y
	int pasX, pasY;

	if (arrive.getX() > depart.getX()) pasX = 1;
	else pasX = -1;

	if (arrive.getY() > depart.getY()) pasY = 1;
	else pasY = -1;

	// la position suivante
	
	Position suivante;

	do{
	    suivante = new Position(depart.getX()+pasX,depart.getY()+pasY);

	    // s'il y a un pion du joueur sur le passage, la prise n'est pas valide
	    if ((damier.getContenu(suivante)*couleur) > 0)
		return null;

	    // s'il y a un pion de l'autre couleur
	    if ((damier.getContenu(suivante)*couleur) < 0){
		if (!trouve){
		    pionPris = new Position(suivante);
		    trouve = true;
		}
		else return null;
	    }
	    depart = suivante;

	} while (!suivante.equals(arrive));

	

	return pionPris;

    }



    private static boolean cheminLibreEnDiagonal(Damier damier, Position depart, Position arrive){
	//suppose que l'arrivée est sur la diagonale de départ...
	// les cases arrivée et départ ne sont pas considérées
	
	// ce n'est pas une diagonale...
	if (!estSurDiagonale(depart,arrive)) return false;

	// si la case de départ et d'arrivée sont confondue 
	if (depart.equals(arrive)) return true;
	
	// sinon on calcule le pas en x et y
	int pasX, pasY;

	if (arrive.getX() > depart.getX()) pasX = 1;
	else pasX = -1;

	if (arrive.getY() > depart.getY()) pasY = 1;
	else pasY = -1;	

	// la position suivante
	Position suivante = new Position(depart.getX()+pasX,depart.getY()+pasY);

	// si la case suivante contient un pion le chemin n'est pas libre
	if (damier.getContenu(suivante) != 0) return false;

	// sinon on recommence
	return cheminLibreEnDiagonal(damier, suivante, arrive);
    }


    private static ResultatAction priseValide(Damier damier, Position depart,
				       Position arrive, int joueurActif){

	ResultatAction resultatAction = new ResultatAction();

	// récupère le contenu de la case de départ 
	int contenuDepart = damier.getContenu(depart),
	    contenuArrive = damier.getContenu(arrive);

	Case caseDepart = damier.getCase(depart),
	    caseArrive = damier.getCase(arrive);

	// position des cases de départ et d'arrivée
	int departX = depart.getX(),
	    departY = depart.getY(),
	    arriveX = arrive.getX(),
	    arriveY = arrive.getY();

	// ** cas ou le coup n'est pas valide ** //

	// la case de départ ou d'arrivée n'existent pas
	if (caseDepart == null) return resultatAction;
	if (caseArrive == null) return resultatAction;

	// si la case d'arrivée ou de départ sont "blanches"
	if (((departX+departY) % 2) == 0) return resultatAction;
	if (((arriveX+arriveY) % 2) == 0) return resultatAction;

	// si la case de départ ne contient pas un pion du joueur
	if (contenuDepart*joueurActif <= 0) return resultatAction;

	// si la case d'arrivée contient un pion
	if (contenuArrive != 0) return resultatAction;

	// si c'est un pion
	if (contenuDepart == 1 || contenuDepart == -1){
	    // si deux cases digagonales séparent arrivée et départ
	    if (((departX + 2 == arriveX)
		 || (departX - 2 == arriveX))
		&& ((departY + 2 == arriveY)
		    || (departY - 2 == arriveY))){

		if (verbeux) System.out.println("Le pion arrive bien deux cases plus loin en diagonale");

		//on regarde si un pion est au milieu
		int xMilieu = (departX+arriveX)/2,
		    yMilieu = (departY+arriveY)/2; 
	    
		Position milieu = new Position(xMilieu,yMilieu);
		int contenuMilieu = damier.getContenu(milieu);

		// le pion pris n'est pas de même couleur que le joueur
		if (contenuMilieu*joueurActif < 0) {
		    resultatAction.setPris(milieu);
		    resultatAction.estValide();
		    if (verbeux) System.out.println("Pion pris par un pion = "+milieu);
		    return resultatAction;
		}
	    }
	}

	// si c'est une dame
	if (contenuDepart == 2 || contenuDepart == -2){

	    // il faut que le déplacement soit sur la diagonale
	    if (!estSurDiagonale(depart,arrive)) {
		if (verbeux) System.out.println("Prise non valide : on est pas sur la diagonale");
		return resultatAction;
	    }
		
	    // il faut qu'il y ait un et un seul pion de l'autre couleur sur le chemin

	    Position pPris = positionPionPris(damier, depart, arrive, joueurActif);
	    if (pPris == null) return resultatAction;
	    
	    resultatAction.setPris(pPris);
	    resultatAction.estValide();
	    return resultatAction;
	   
	}

	// la prise n'est pas validée
	resultatAction.setPris(null);
	
	return resultatAction;
    }





    /**
     * Retourne le résultat d'une action (sous la forme d'un ResltatAction sur un damier pour un déplacement,
     * pour un joueur.
     * @param damier le damier sur lequel s'effectue le déplacement
     * @param deplacement Le déplacement effectué
     * @param joueurActif Le joueur qui effectue le déplacement (>0 pour le joueur blanc, <0 pour le joueur noir)
     * @param unePrise <PRE>Inidique si une prise a déjà été effectuée par le joueur
     *
     * Si aucune prise a été effectuée, l'ensemble des coups est possible
     * Si une prise a déjà été effectuée, seule une autre prise peut être validée.
     * </PRE>
     * @return un booléen qui indique si le mouvement est valide ou non
     */    
    public static ResultatAction valide(Damier damier, Deplacement deplacement,
				 int joueurActif, boolean unePrise){

	ResultatAction resultatAction = new ResultatAction(); 

	if (verbeux) System.out.println("Debut : "+resultatAction);

	Position depart = deplacement.getDepart(),
	    arrive = deplacement.getArrive();

	Case caseDepart = damier.getCase(depart),
	    caseArrive = damier.getCase(arrive);

	int contenuDepart = caseDepart.getContenu(),
	    contenuArrive = caseArrive.getContenu();       

	// ** cas ou le coup n'est pas valide ** //

	// la case de départ ou d'arrivée n'existent pas
	if (caseDepart == null) return resultatAction;
	if (caseArrive == null) return resultatAction;

	// si la case de départ ne contient pas un pion du joueur
	if (contenuDepart*joueurActif <= 0) return resultatAction;

	// si la case d'arrivée contient un pion
	if (contenuArrive != 0) return resultatAction;

	// le pion sélectionné doit permettre de prendre le maximum de pion

	int maxPionPrisDamier = maxPriseJeu(damier, joueurActif);
	//System.out.println(maxPionPrisDamier);
	if (nbPriseMax(damier,depart, joueurActif) < maxPionPrisDamier)
	    return resultatAction;       	


	// ** cas ou le coup est valide ** //

	// * coup "normal" *

	// si il n'y a pas de prise possible et qu'il n'y a pas eu de prise,
	// on autorise les "coup" normaux	

	if (maxPionPrisDamier == 0 && !unePrise){
	    
	    // si c'est un pion
	    if (contenuDepart == 1 || contenuDepart == -1){
		// vers la droite
		if ((depart.getX() +1 == arrive.getX())
		    && (depart.getY() - joueurActif == arrive.getY())) {
		    resultatAction.estValide();
		    return resultatAction;
		}

		// vers la gauche
		if ((depart.getX() -1 == arrive.getX())
		    && (depart.getY() - joueurActif == arrive.getY())) {
		    resultatAction.estValide();
		    return resultatAction;
		}
	    }

	    // si c'est une dame
	    if (contenuDepart == 2 || contenuDepart ==-2){
		if (cheminLibreEnDiagonal(damier, depart,arrive)) {
		    resultatAction.estValide();
		    return resultatAction;
		}
	    }
	}

	resultatAction = priseValide(damier,depart, arrive, joueurActif);

	if (!resultatAction.getValide()) return resultatAction;
	
       
	Damier damierTemp = execute(damier, new Deplacement(depart, arrive), resultatAction);

	
	    
	if (nbPriseMax(damierTemp, arrive, joueurActif) < maxPionPrisDamier-1){
	    resultatAction.estNonValide();
	    resultatAction.setPris(null);
	}

	return resultatAction;



    }



    /**
     * Retourne sous la forme d'un vecteur la liste des positions d'arrivée possibles
     * pour une case donnée (sur un damier, pour un joueur)
     * @param damier Le damier sur lequel s'effectuent les déplacements
     * @param depart La case de départ
     * @param joueurActif Le joueur qui joueur (>0 pour les blancs, <0 pour les noirs)
     * @param unePrise Inidique si une prise a déjà été réalisée
     * @return un vecteur qui contient la liste des positions d'arrivée possibles
     * (normalement, le vecteur ne doit comporter que des instances
     * de la classe Position).
     */    
    public static List<Position> listeArriveValide(Damier damier, Position depart, 
				 int joueurActif, boolean unePrise){


	List<Position> listeCoups = new ArrayList<Position>();

	for (int i=0; i<damier.getMaxX(); i++){
	    for (int j=0; j<damier.getMaxY(); j++){
		Position arrive = new Position(i,j);
		Deplacement deplacement = new Deplacement(depart,arrive);
		ResultatAction ra = valide(damier, deplacement, joueurActif, unePrise);
		if (ra.getValide())
		    listeCoups.add(arrive);
	    }
	}

	return listeCoups;
    }


    /**
     * réalise le dépalcement de pion. Si le mouvement n'est pas valide, le damier d'origine est retourné
     * @param damier le damier d'origine
     * @param deplacement Un déplacement
     * @param resultatAction encapsule l'information de validation et le pion éventuellement pris
     * @return Le damier après exécution du coup
     */    
    public static Damier execute(Damier damier, 
				 Deplacement deplacement, 
				 ResultatAction resultatAction){

        // on récupère l'information de validation
	if (resultatAction.getValide() == false) return damier;

	Damier nouveauDamier = new Damier(damier);

	Position depart = deplacement.getDepart(),
	    arrive = deplacement.getArrive();

	// on fait le déplacement
	nouveauDamier.setContenu(arrive,damier.getContenu(depart));
	nouveauDamier.setContenu(depart,0);

	// on supprime le pion pris éventuellement

	Position posPris = resultatAction.getPris();
	if (posPris != null) nouveauDamier.setContenu(posPris,0);

	// un pion arrivé au bout de sa rangée devient une dame
	// un pion qui arrive sur le bout du damier devient dame
	if ((nouveauDamier.getContenu(arrive) == 1 ) 
	    && (arrive.getY() == 0))
	    nouveauDamier.setContenu(arrive,2);

	if ((nouveauDamier.getContenu(arrive) == -1) 
	    && (arrive.getY() == nouveauDamier.getMaxY() -1))
	    nouveauDamier.setContenu(arrive,-2);

	return nouveauDamier;
    }


    // Permet de retrouver le déplacement effectué entre deux damier
    // On suppose que les deux damiers ont la même dimension et que
    // le déplacement est valide.
    /**
     * <PRE>
     * Permet de retrouver le coup en fonction de l'état de deux damier (damier de départ et damier d'arrivée).
     *
     * Sachant qu'un seul pion du damier a changé pour le joueur considéré</PRE>
     * @param DamierDepart Etat du damier au départ
     * @param DamierArrive Etat du damier à l'arrivée
     * @param joueur Joueur qui a joué
     * @return Le coup joué
     */    
    public static Deplacement retrouveDeplacement(Damier DamierDepart,
						  Damier DamierArrive,
						  int joueur){

	Deplacement deplacement;
	Position positionDepart = new Position(0,0),
	    positionArrive = new Position(0,0);
        
        int maxX = DamierDepart.getMaxX();
        int maxY = DamierDepart.getMaxY();

	for (int i=0; i<maxX; i++){
	    for (int j=0; j<maxY; j++){

		int contenuDepart = DamierDepart.getCase(i,j).getContenu();
		int contenuArrive = DamierArrive.getCase(i,j).getContenu();
		if ((contenuDepart*joueur>0) && (contenuArrive==0))
		    positionDepart = new Position(i,j);

		if ((contenuDepart==0) && (contenuArrive*joueur>0))
		    positionArrive = new Position(i,j);

		
	    }
	}
	return new Deplacement(positionDepart, positionArrive);

    }    

}
