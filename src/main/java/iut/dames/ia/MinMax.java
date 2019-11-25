package iut.dames.ia;

import iut.dames.arbre.MinMaxNode;
import iut.dames.damier.*;

import java.util.ArrayList;
import java.util.List;

public class MinMax extends Ia {
    int maxDepth;

    public MinMax(int joueur, int maxDepth) {
        super(joueur);
        this.maxDepth = maxDepth;
    }

    public void createTree(MinMaxNode node, int depth, Position starting) {
        if (node.isLeaf()) {
            this.buildNode(node, starting);
        }
        if (depth < this.maxDepth) {
            for (MinMaxNode child : node.getChildren()) {
                this.createTree(child, depth + 1, starting);
            }
        }
    }

    public void buildNode(MinMaxNode node, Position starting) {
        boolean taking = false;
        List<Position> startPos = new ArrayList<Position>();
        if (starting != null) {
            taking = true;
            startPos.add(starting);
        } else {
            startPos = node.getGameBoard().listePositionPion(node.getPlayer());
        }
        for (Position start : startPos) {
            List<Position> endPos = Coup.listeArriveValide(node.getGameBoard(), start, node.getPlayer(), taking);
            for (Position end : endPos) {
                Deplacement move = new Deplacement(start, end);
                ResultatAction valid = Coup.valide(node.getGameBoard(), move, node.getPlayer(), taking);
                if (valid.getValide()) {
                    Damier result = Coup.execute(node.getGameBoard(), move, valid);
                    MinMaxNode child = new MinMaxNode(result, -node.getPlayer(), Valeur.valeurDeJeu(result, this.joueur));
                    node.addChild(child);
                }
            }
        }
    }

    @Override
    public Deplacement choix(Damier damier, int joueur) {
        System.out.println("Nouveau choix pas pris");
        MinMaxNode root = new MinMaxNode(damier, joueur);
        this.createTree(root, 0, null);
        System.out.println("Père : " + root);
        System.out.println("Fils 0 : " + root.getChildren().get(0));
        MinMaxNode.evaluate(root, joueur, Integer.MIN_VALUE, Integer.MAX_VALUE);
        Damier tempBoard = MinMaxNode.maxChild(root).getGameBoard();
        return Coup.retrouveDeplacement(damier, tempBoard, joueur);
    }

    @Override
    public Deplacement choix(Damier damier, int joueur, Position position) {
        Damier tempBoard = null;
        System.out.println("Nouveau choix pris");
        MinMaxNode root = new MinMaxNode(damier, joueur);
        this.createTree(root, 0, position);
        System.out.println(root);
        MinMaxNode.evaluate(root, joueur, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (!root.isLeaf()) {
            tempBoard = MinMaxNode.maxChild(root).getGameBoard();
        } else {
            tempBoard = root.getGameBoard();
        }
        return Coup.retrouveDeplacement(damier, tempBoard, joueur);
    }
}

