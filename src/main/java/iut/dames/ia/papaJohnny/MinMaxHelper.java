package iut.dames.ia.papaJohnny;

import iut.dames.damier.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Regroupe les méthodes de classes utilisées par la classe Coup, redéfinies en méthodes publiques afin d'être utilisées pour certaines opérations de l'IA.
 */

public class MinMaxHelper {

    //private static ResultatAction resultatAction;

    private static boolean verbeux = false;

    private static boolean estSurDiagonale(Position depart, Position arrive) {

        int decalX = arrive.getX() - depart.getX();
        int decalY = arrive.getY() - depart.getY();

        if (decalX == decalY) return true;
        if (decalX == -decalY) return true;
        if (-decalX == decalY) return true;
        return -decalX == -decalY;

    }


    public static int nbPriseMax(Damier damier, Position depart, int joueurActif) {

        Position arrive;
        Damier damierArrive;
        ResultatAction rAction;
        Deplacement deplacement;
        boolean faireTest = true;

        int nbPrise, decalX, max = 0;

        int contenuDepart = damier.getContenu(depart);

        int maxX = damier.getMaxX(), maxY = damier.getMaxY();

        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {

                // cases noires
                if (((i + j) % 2) == 1) {
                    arrive = new Position(i, j);

                    if (contenuDepart == 1 || contenuDepart == -1) {
                        decalX = depart.getX() - arrive.getX();

                        faireTest = (decalX == 2) || (decalX == -2);
                    }

                    if (faireTest) {

                        if (estSurDiagonale(depart, arrive)) {

                            // on est sur la diagonale

                            rAction = priseValide(damier, depart, arrive, joueurActif);
                            if (rAction.getValide()) {

                                deplacement = new Deplacement(depart, arrive);
                                damierArrive = execute(damier, deplacement, rAction);
                                nbPrise = 1 + nbPriseMax(damierArrive, arrive, joueurActif);
                                if (nbPrise > max) max = nbPrise;
                            }
                        }
                    }
                }
            }
        }

        return max;
    }

    public static int maxPriseJeu(Damier damier, int joueurActif) {
        int nb, max = 0;
        int maxX = damier.getMaxX(), maxY = damier.getMaxY();

        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {

                // cases noires
                if ((i + j) % 2 == 1) {
                    Position depart = new Position(i, j);

                    if (damier.getCase(depart).getContenu() * joueurActif > 0) {
                        nb = nbPriseMax(damier, depart, joueurActif);
                        if (nb > max) max = nb;
                    }
                }
            }
        }
        return max;
    }

    private static Position positionPionPris(Damier damier, Position depart, Position arrive, int couleur) {
        boolean trouve = false;
        Position pionPris = null;
        if (depart.equals(arrive)) return null;
        int pasX, pasY;
        if (arrive.getX() > depart.getX()) pasX = 1;
        else pasX = -1;
        if (arrive.getY() > depart.getY()) pasY = 1;
        else pasY = -1;
        Position suivante;
        do {
            suivante = new Position(depart.getX() + pasX, depart.getY() + pasY);
            if ((damier.getContenu(suivante) * couleur) > 0)
                return null;
            if ((damier.getContenu(suivante) * couleur) < 0) {
                if (!trouve) {
                    pionPris = new Position(suivante);
                    trouve = true;
                } else return null;
            }
            depart = suivante;
        } while (!suivante.equals(arrive));
        return pionPris;
    }

    private static boolean cheminLibreEnDiagonal(Damier damier, Position depart, Position arrive) {

        if (!estSurDiagonale(depart, arrive)) return false;

        if (depart.equals(arrive)) return true;

        int pasX, pasY;

        if (arrive.getX() > depart.getX()) pasX = 1;
        else pasX = -1;

        if (arrive.getY() > depart.getY()) pasY = 1;
        else pasY = -1;

        Position suivante = new Position(depart.getX() + pasX, depart.getY() + pasY);

        if (damier.getContenu(suivante) != 0) return false;

        return cheminLibreEnDiagonal(damier, suivante, arrive);
    }


    private static ResultatAction priseValide(Damier damier, Position depart, Position arrive, int joueurActif) {

        ResultatAction resultatAction = new ResultatAction();

        int contenuDepart = damier.getContenu(depart),
                contenuArrive = damier.getContenu(arrive);

        Case caseDepart = damier.getCase(depart),
                caseArrive = damier.getCase(arrive);

        int departX = depart.getX(),
                departY = depart.getY(),
                arriveX = arrive.getX(),
                arriveY = arrive.getY();

        if (caseDepart == null) return resultatAction;
        if (caseArrive == null) return resultatAction;

        if (((departX + departY) % 2) == 0) return resultatAction;
        if (((arriveX + arriveY) % 2) == 0) return resultatAction;

        if (contenuDepart * joueurActif <= 0) return resultatAction;

        if (contenuArrive != 0) return resultatAction;

        if (contenuDepart == 1 || contenuDepart == -1) {
            if (((departX + 2 == arriveX)
                    || (departX - 2 == arriveX))
                    && ((departY + 2 == arriveY)
                    || (departY - 2 == arriveY))) {

                if (verbeux) System.out.println("Le pion arrive bien deux cases plus loin en diagonale");

                int xMilieu = (departX + arriveX) / 2,
                        yMilieu = (departY + arriveY) / 2;

                Position milieu = new Position(xMilieu, yMilieu);
                int contenuMilieu = damier.getContenu(milieu);

                if (contenuMilieu * joueurActif < 0) {
                    resultatAction.setPris(milieu);
                    resultatAction.estValide();
                    if (verbeux) System.out.println("Pion pris par un pion = " + milieu);
                    return resultatAction;
                }
            }
        }

        // si c'est une dame
        if (contenuDepart == 2 || contenuDepart == -2) {

            if (!estSurDiagonale(depart, arrive)) {
                if (verbeux) System.out.println("Prise non valide : on est pas sur la diagonale");
                return resultatAction;
            }

            Position pPris = positionPionPris(damier, depart, arrive, joueurActif);
            if (pPris == null) return resultatAction;

            resultatAction.setPris(pPris);
            resultatAction.estValide();
            return resultatAction;

        }

        resultatAction.setPris(null);

        return resultatAction;
    }

    public static ResultatAction valide(Damier damier, Deplacement deplacement,
                                        int joueurActif, boolean unePrise) {

        ResultatAction resultatAction = new ResultatAction();

        if (verbeux) System.out.println("Debut : " + resultatAction);

        Position depart = deplacement.getDepart(),
                arrive = deplacement.getArrive();

        Case caseDepart = damier.getCase(depart),
                caseArrive = damier.getCase(arrive);

        int contenuDepart = caseDepart.getContenu(),
                contenuArrive = caseArrive.getContenu();

        if (caseDepart == null) return resultatAction;
        if (caseArrive == null) return resultatAction;

        if (contenuDepart * joueurActif <= 0) return resultatAction;

        if (contenuArrive != 0) return resultatAction;

        int maxPionPrisDamier = maxPriseJeu(damier, joueurActif);

        if (nbPriseMax(damier, depart, joueurActif) < maxPionPrisDamier)
            return resultatAction;

        if (maxPionPrisDamier == 0 && !unePrise) {

            if (contenuDepart == 1 || contenuDepart == -1) {
                if ((depart.getX() + 1 == arrive.getX())
                        && (depart.getY() - joueurActif == arrive.getY())) {
                    resultatAction.estValide();
                    return resultatAction;
                }

                if ((depart.getX() - 1 == arrive.getX())
                        && (depart.getY() - joueurActif == arrive.getY())) {
                    resultatAction.estValide();
                    return resultatAction;
                }
            }

            if (contenuDepart == 2 || contenuDepart == -2) {
                if (cheminLibreEnDiagonal(damier, depart, arrive)) {
                    resultatAction.estValide();
                    return resultatAction;
                }
            }
        }
        resultatAction = priseValide(damier, depart, arrive, joueurActif);
        if (!resultatAction.getValide()) return resultatAction;
        Damier damierTemp = execute(damier, new Deplacement(depart, arrive), resultatAction);

        if (nbPriseMax(damierTemp, arrive, joueurActif) < maxPionPrisDamier - 1) {
            resultatAction.estNonValide();
            resultatAction.setPris(null);
        }

        return resultatAction;


    }

    public static List<Position> listeArriveValide(Damier damier, Position depart, int joueurActif, boolean unePrise) {

        List<Position> listeCoups = new ArrayList<Position>();

        for (int i = 0; i < damier.getMaxX(); i++) {
            for (int j = 0; j < damier.getMaxY(); j++) {
                Position arrive = new Position(i, j);
                Deplacement deplacement = new Deplacement(depart, arrive);
                ResultatAction ra = valide(damier, deplacement, joueurActif, unePrise);
                if (ra.getValide())
                    listeCoups.add(arrive);
            }
        }

        return listeCoups;
    }

    public static Damier execute(Damier damier,
                                 Deplacement deplacement,
                                 ResultatAction resultatAction) {

        // on récupère l'information de validation
        if (resultatAction.getValide() == false) return damier;

        Damier nouveauDamier = new Damier(damier);

        Position depart = deplacement.getDepart(), arrive = deplacement.getArrive();


        nouveauDamier.setContenu(arrive, damier.getContenu(depart));
        nouveauDamier.setContenu(depart, 0);


        Position posPris = resultatAction.getPris();
        if (posPris != null) nouveauDamier.setContenu(posPris, 0);

        if ((nouveauDamier.getContenu(arrive) == 1)
                && (arrive.getY() == 0))
            nouveauDamier.setContenu(arrive, 2);

        if ((nouveauDamier.getContenu(arrive) == -1)
                && (arrive.getY() == nouveauDamier.getMaxY() - 1))
            nouveauDamier.setContenu(arrive, -2);

        return nouveauDamier;
    }

    public static Deplacement retrouveDeplacement(Damier DamierDepart,
                                                  Damier DamierArrive,
                                                  int joueur) {

        Deplacement deplacement;
        Position positionDepart = new Position(0, 0),
                positionArrive = new Position(0, 0);

        int maxX = DamierDepart.getMaxX();
        int maxY = DamierDepart.getMaxY();

        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {

                int contenuDepart = DamierDepart.getCase(i, j).getContenu();
                int contenuArrive = DamierArrive.getCase(i, j).getContenu();
                if ((contenuDepart * joueur > 0) && (contenuArrive == 0))
                    positionDepart = new Position(i, j);

                if ((contenuDepart == 0) && (contenuArrive * joueur > 0))
                    positionArrive = new Position(i, j);


            }
        }
        return new Deplacement(positionDepart, positionArrive);

    }

}
