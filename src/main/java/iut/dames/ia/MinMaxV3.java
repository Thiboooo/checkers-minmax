package iut.dames.ia;

import iut.dames.arbre.MinMaxNode;
import iut.dames.damier.*;

import java.util.Random;

public class MinMaxV3 extends Ia {
    private int depth;

    public MinMaxV3(int player, int depth) {
        super(player);
        this.depth = depth;
    }

    public int minmax(MinMaxNode node, int depth, int player, int alpha, int beta, Position starting) {
        if (depth == 0) {
            node.setValue(Valeur.valeurDeJeu(node.getGameBoard(), this.joueur));
            return node.getValue();
        }
        if (player == this.joueur) {
            node.setValue(Integer.MIN_VALUE);
            System.out.println("Oui max");
            node.createChildren(starting);
            for (MinMaxNode child : node.getChildren()) {
                node.setValue(Integer.max(node.getValue(), this.minmax(child, depth - 1, child.getPlayer(), alpha, beta, starting)));
                alpha = Integer.max(node.getValue(), alpha);
                if (alpha >= beta) {
                    break;
                }
            }
            return node.getValue();
        } else {
            node.setValue(Integer.MAX_VALUE);
            System.out.println("Oui min");
            node.createChildren(starting);
            for (MinMaxNode child : node.getChildren()) {
                node.setValue(Integer.min(node.getValue(), this.minmax(child, depth - 1, child.getPlayer(), alpha, beta, starting)));
                beta = Integer.min(node.getValue(), beta);
                if (alpha >= beta) {
                    break;
                }
            }
            return node.getValue();
        }
    }

    @Override
    public Deplacement choix(Damier damier, int joueur, Position position) {
        MinMaxNode root = new MinMaxNode(damier, joueur);
        this.minmax(root, this.depth, this.joueur, Integer.MIN_VALUE, Integer.MAX_VALUE, position);
        return getDeplacement(damier, joueur, root);
    }

    @Override
    public Deplacement choix(Damier damier, int joueur) {
        MinMaxNode root = new MinMaxNode(damier, joueur);
        this.minmax(root, this.depth, this.joueur, Integer.MIN_VALUE, Integer.MAX_VALUE, null);
        return getDeplacement(damier, joueur, root);
    }

    private Deplacement getDeplacement(Damier damier, int joueur, MinMaxNode root) {
        Damier tempBoard;
        if (!root.isLeaf()) {
            Random r = new Random();
            int choice = r.nextInt(root.getMaxChildren().size());
            tempBoard = root.getMaxChildren().get(choice).getGameBoard();
        } else {
            tempBoard = root.getGameBoard();
        }
        return Coup.retrouveDeplacement(damier, tempBoard, joueur);
    }
}
