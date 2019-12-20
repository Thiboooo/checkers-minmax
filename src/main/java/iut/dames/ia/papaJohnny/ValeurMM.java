package iut.dames.ia.papaJohnny;

import iut.dames.damier.Damier;

public class ValeurMM {

    /* TODO :
    Redéfinir l'heuristique utilisée :
    - Valuer le contrôle du centre du damier
    - Valuer la préservation de l "arrière" ligne
    - Pénaliser la prise de position offrant un nb de prises trop élevées par l'adversaire
    - Valuer la combinaisons de pièces rendant impossible la prise d'une pièce
     */

    public static final int PION = 1;
    public static final int REINE = 10;
    public static final int GAGNE = 1000;

    public static int valeurDeJeu(Damier d, int joueur) {

        int total = 0, positif = 0, negatif = 0;

        for (int i = 0; i < d.getMaxX(); i++) {
            for (int j = 0; j < d.getMaxY(); j++) {
                int contenu = d.getContenu(i, j);

                switch (contenu) {
                    case -2:
                        negatif += REINE;
                        break;
                    case -1:
                        negatif += PION;
                        break;
                    case 1:
                        positif += PION;
                        break;
                    case 2:
                        positif += REINE;
                        break;
                    default:
                        break;
                }
            }
        }

        if (joueur > 0) {
            if (positif == 0) return -GAGNE;
            if (negatif == 0) return GAGNE;
            return positif - negatif;
        }

        if (positif == 0) return GAGNE;
        if (negatif == 0) return -GAGNE;
        return -(positif - negatif);

    }

}
