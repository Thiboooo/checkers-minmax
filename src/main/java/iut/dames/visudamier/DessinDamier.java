package iut.dames.visudamier;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import iut.dames.damier.Case;
import iut.dames.damier.Damier;
import iut.dames.damier.Position;
import iut.dames.damier.Arbitre;
import java.awt.Cursor;

/**
 * Représente dans un JFrame le contenu du Damier possédé par un arbitre.
 */
public class DessinDamier extends JPanel implements MouseListener, Runnable{

    Arbitre arbitre;
    Damier damier;
    int contenu;
    int largCase, hautCase;

    /**
     * Cré une nouvelle instance de DessinDamier en référence à une instance d'Arbitre (un Arbitre possède un
     * attribut de la classe Damier que l'on peut afficher)
     * @param arbitre L'arbitre dont on veut représenter le damier
     */    
    public DessinDamier(Arbitre arbitre){
	this.arbitre = arbitre;
	damier = arbitre.getDamier();
	addMouseListener(this);
    } // DessinDamier

    /**
     *
     * @param g
     */    
    public void paint(Graphics g){

	// Pourquoi faut-il faire ça ???
	damier = arbitre.getDamier();

	//System.out.println("DAMIER DESSINE \n"+damier);

       	double haut = getSize().getHeight();
	double larg = getSize().getWidth();
	g.clearRect(0,0,(int)larg,(int)haut);
	//g.drawString("haut = "+haut+" ; "+larg,10,10);

	int nbCaseX = damier.getMaxX(), 
	    nbCaseY = damier.getMaxY();

	largCase = (int)(larg/nbCaseX);
	hautCase = (int)(haut/nbCaseY);

	for (int i=0; i<nbCaseX; i++){
	    for (int j=0; j<nbCaseY; j++){

		contenu = damier.getContenu(i,j);

		// dessin du fond de la case
		g.setColor(damier.getColor(i,j));
		g.fillRect(i*largCase,j*hautCase,largCase,hautCase);
		
		// dessin du tour des cases
		if (damier.isSelected(i,j)) {
		    g.setColor(Case.getColorSelected());
                    
		    g.drawRect(i*largCase+1,j*hautCase+1,largCase-2,hautCase-2);
		    g.drawRect(i*largCase+2,j*hautCase+2,largCase-4,hautCase-4);
		}
                else{
		    g.setColor(Color.BLACK);
                }
		
                g.drawRect(i*largCase,j*hautCase,largCase,hautCase);
		

		// dessin du pion
		if (contenu>0) 
		    g.setColor(Color.WHITE);

		if (contenu<0)
		    g.setColor(Color.BLACK);

		if (contenu != 0){
		    int decalX = (int)(largCase/7);
		    int decalY = (int)(hautCase/7);
		    
		    g.fillOval(i*largCase+decalX, j*hautCase+decalY,
			       largCase-2*decalX, hautCase-2*decalY);
                    
                    // contour du pion 
                    // (pour voir les pions blancs sur les cases blanches)
                    g.setColor(Color.BLACK);
                    g.drawOval(i*largCase+decalX, j*hautCase+decalY,
			       largCase-2*decalX, hautCase-2*decalY);

		    // dame
		    if (contenu == 2 || contenu == -2){		    
                        if (contenu == 2)
                            g.setColor(Color.BLACK);
                        else g.setColor(Color.WHITE);
			g.drawOval(i*largCase+2*decalX, j*hautCase+2*decalY,
				   largCase-4*decalX, hautCase-4*decalY);
		    }
		}
	    }
	}
    } // paint

    
    public void mouseClicked(MouseEvent e){
	//System.out.println("clic : "+largCase+" , "+hautCase);
	int x = e.getX()/largCase;
	int y = e.getY()/hautCase;

	Position p = new Position(x,y);

	arbitre.selectPosition(p);

	repaint();

       	//System.out.println(arbitre.getDamier());

	//System.out.println("case cliquée : "+p);
	
    }

    public void mouseEntered(MouseEvent e) {
	//System.out.println(e);
    }

    public void mouseExited(MouseEvent e) {
	//System.out.println(e);
    }

    public void mousePressed(MouseEvent e) {
	//System.out.println(e);
    }

    public void mouseReleased(MouseEvent e) {
	//System.out.println(e);
    }

    public void run(){

	while(arbitre.getJeuActif()){
	    repaint();
            
            
            if (arbitre.isIAWait()){
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) ) ; 
            }
            else{
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) ) ; 
            }
            
	    try{
		Thread.sleep(20);
	    } catch(InterruptedException e){System.out.println("Problème de thread...");}
            
	}
        
        // repaint final
        repaint();
    }

}