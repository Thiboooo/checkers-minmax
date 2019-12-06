package iut.dames.ia;

import iut.dames.damier.Coup;
import iut.dames.damier.Damier;
import iut.dames.damier.Deplacement;
import iut.dames.damier.Position;

import java.util.List;
import java.util.Random;

public class Aleatoire extends Ia {

    public Aleatoire(int joueur) {
        super(joueur);
    }

    public Deplacement choix(Damier damier, int joueur) {

        List<Position> listePion = damier.listePositionPion(joueur);
        List coupPossible;
        int taille = listePion.size();
        Random rand = new Random();
        Position depart, arrive;
        int num;
        int nbcoup;


        do {
            num = rand.nextInt(taille);
            depart = listePion.get(num);
            System.out.println("Départ : " + depart);

            coupPossible = Coup.listeArriveValide(damier, depart, joueur, false);

            nbcoup = coupPossible.size();
        } while (nbcoup == 0);

        arrive = (Position) (coupPossible.get(rand.nextInt(coupPossible.size())));

        return new Deplacement(depart, arrive);
    }

    public Deplacement choix(Damier damier, int joueur, Position position) {
        List coupPossible;
        Random rand = new Random();

        Position depart, arrive;

        depart = position;
        System.out.println("Départ : " + depart);

        coupPossible = Coup.listeArriveValide(damier, depart, joueur, false);

        if (coupPossible.size() == 0) return null;

        arrive = (Position) (coupPossible.get(rand.nextInt(coupPossible.size())));

        return new Deplacement(depart, arrive);
    }
}