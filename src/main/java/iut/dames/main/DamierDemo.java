package iut.dames.main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import iut.dames.visudamier.DessinDamier;
import iut.dames.damier.Damier;
import iut.dames.damier.Arbitre;
import iut.dames.damier.Humain;
import iut.dames.damier.Machine;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DamierDemo{
    
    
    

    public static void main(String[] args){


	JFrame fenetre = new JFrame("Damier");
	fenetre.setSize(600,500);

	fenetre.setVisible(true);

	// sortie si fermeture du cadre
	fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JRootPane contenu = fenetre.getRootPane();

	// Construction de l'IHM avec Grille + Agent
	//ihm = new Ihm(contenu,damier);

	Arbitre arbitre = new Arbitre(10,10);
        
        arbitre.setJoueur(0, new Humain(1));
        arbitre.setJoueur(1, new Humain(-1));


        //arbitre.setJoueur(1, new Machine(-1,new ia.Aleatoire(-1)));

        
        arbitre.start();
        
        arbitre.setAfficheDamier(true);

        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        
        menuBar = new JMenuBar();
        
        menu = new JMenu("Jeu");
        
        JMenuItem itemLancer = new JMenuItem("lancer");
        
        
        menu.add(itemLancer);
        
        itemLancer.addActionListener((ActionEvent e) -> {
            arbitre.setRestart(true);            
        });
        
        menuBar.add(menu);
        
        fenetre.setJMenuBar(menuBar);
                   
	DessinDamier monDessinDamier = new DessinDamier(arbitre);

	Thread monThreadFenetre = new Thread(monDessinDamier);
	monThreadFenetre.start();
	fenetre.getContentPane().add(monDessinDamier);

	// Rendre la fenetre visible
	fenetre.setVisible(true);
        
        // attendre la fin        
        try {
            System.out.println("mise en attente");
            arbitre.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(RunDamierGraphique.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JOptionPane.showMessageDialog(null, "Fin de partie.");
    }
}
