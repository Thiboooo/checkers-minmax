package iut.dames.main;

import iut.dames.damier.Arbitre;
import iut.dames.damier.Humain;
import iut.dames.damier.Machine;
import iut.dames.ia.Ia;
import iut.dames.ia.PapaJohnny;
import iut.dames.visudamier.DessinDamier;

import javax.swing.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RunDamierGraphique{

    public static void main(String[] args){


	JFrame fenetre = new JFrame("Damier");
	fenetre.setSize(600,500);

        ArrayList<Ia> list = new ArrayList<>();
        

        Arbitre arbitre = new Arbitre(10,10);

        arbitre.setJoueur(0, new Machine(1, new PapaJohnny(1, 4)));

        arbitre.setJoueur(1, new Humain(-1));


	fenetre.setVisible(true);

	// sortie si fermeture du cadre
	fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JRootPane contenu = fenetre.getRootPane();

	// Construction de l'IHM avec Grille + Agent
	//ihm = new Ihm(contenu,damier);

	
        arbitre.setAfficheDamier(true);
        arbitre.setVerbeux(true);
        
        arbitre.start();
                       
        //System.out.println(arbitre.getDamier());

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
