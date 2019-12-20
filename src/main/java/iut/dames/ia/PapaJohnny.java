package iut.dames.ia;

import iut.dames.damier.Coup;
import iut.dames.damier.Damier;
import iut.dames.damier.Deplacement;
import iut.dames.damier.Position;
import iut.dames.ia.papaJohnny.MinMaxNode;
import iut.dames.ia.papaJohnny.ValeurMM;

import java.util.Random;


public class PapaJohnny extends Ia {
    private int depth;

    public PapaJohnny(int player, int depth) {
        super(player);
        this.depth = depth;
    }

    public int minmax(MinMaxNode node, int depth, int player, int alpha, int beta) { //Fonction alpha-beta
        if (depth == 0) { //Cas où on atteint le niveau final de l'arbre de recherche
            node.setValue(ValeurMM.valeurDeJeu(node.getGameBoard(), this.joueur));
            return node.getValue();
        }
        if (player == this.joueur) { //Cas où le noeud évalué est un noeud max
            node.setValue(Integer.MIN_VALUE);
            node.createChildren();
            if (node.getChildren().size() == 0) { // Cas où le noeud est terminal
                node.setValue(ValeurMM.valeurDeJeu(node.getGameBoard(), this.joueur));
            } else {
                for (MinMaxNode child : node.getChildren()) {
                    node.setValue(Integer.max(node.getValue(), this.minmax(child, depth - 1, child.getPlayer(), alpha, beta)));
                    alpha = Integer.max(node.getValue(), alpha);
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            System.out.println("Joueur max - Profondeur " + (this.depth - depth) + " " + node.getValue());
            return node.getValue();
        } else { //Cas où le noeud évalué est un noeud min
            node.setValue(Integer.MAX_VALUE);
            node.createChildren();
            if (node.getChildren().size() == 0) { // Cas où le noeud est terminal
                node.setValue(ValeurMM.valeurDeJeu(node.getGameBoard(), this.joueur));
            } else {
                for (MinMaxNode child : node.getChildren()) {
                    node.setValue(Integer.min(node.getValue(), this.minmax(child, depth - 1, child.getPlayer(), alpha, beta)));
                    beta = Integer.min(node.getValue(), beta);
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            System.out.println("Joueur min - Profondeur " + (this.depth - depth) + " " + node.getValue());
            return node.getValue();
        }
    }

    @Override
    public Deplacement choix(Damier damier, int joueur, Position position) {
        MinMaxNode root = new MinMaxNode(damier, joueur, position);
        this.minmax(root, this.depth, this.joueur, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return getDeplacement(damier, joueur, root);
    }

    @Override
    public Deplacement choix(Damier damier, int joueur) {
        MinMaxNode root = new MinMaxNode(damier, joueur);
        this.minmax(root, this.depth, this.joueur, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return getDeplacement(damier, joueur, root);
    }

    private Deplacement getDeplacement(Damier damier, int joueur, MinMaxNode root) { // Méthode extraite pour éviter les doublons de code
        Damier tempBoard;
        if (!root.isLeaf()) {
            /*
            TODO : Adopter une façon plus intelligente de choisir entre plusieurs coups d'une même valeur maximale, pas une méthode aléatoire
             */
            Random r = new Random();
            int choice = r.nextInt(root.getMaxChildren().size());
            tempBoard = root.getMaxChildren().get(choice).getGameBoard();
        } else {
            tempBoard = root.getGameBoard();
        }
        return Coup.retrouveDeplacement(damier, tempBoard, joueur);
    }
}
