package iut.dames.ia;

import iut.dames.arbre.NoeudMiniMax;
import iut.dames.damier.*;

import java.util.ArrayList;
import java.util.List;

public class MinMaxV2 extends Ia {
    int maxDepth;

    public MinMaxV2(int joueur, int maxDepth) {
        super(joueur);
        this.maxDepth = maxDepth;
    }

    public void createTree(NoeudMiniMax node, int depth, Position starting) {
        if (node.estFeuille()) {
            this.buildNode(node, starting);
        }
        if (depth < this.maxDepth) {
            for (int i = 0; i < node.getNbFils(); i++) {
                this.createTree((NoeudMiniMax) node.getFils(i), depth + 1, starting);
            }
        }
    }

    public void buildNode(NoeudMiniMax node, Position starting) {
        boolean taking = false;
        int nbfils = 0;
        List<Position> startPos = new ArrayList<Position>();
        if (starting != null) {
            taking = true;
            startPos.add(starting);
        } else {
            startPos = node.getGameBoard().listePositionPion(node.getJoueur());
            //System.out.println("startPos size :"+startPos.size());
        }
        for (Position start : startPos) {
            List<Position> endPos = Coup.listeArriveValide(node.getGameBoard(), start, node.getJoueur(), taking);
            //System.out.println("endPos size : "+endPos.size());
            for (Position end : endPos) {
                Deplacement move = new Deplacement(start, end);
                ResultatAction valid = Coup.valide(node.getGameBoard(), move, node.getJoueur(), taking);
                if (valid.getValide()) {
                    Damier result = Coup.execute(node.getGameBoard(), move, valid);
                    NoeudMiniMax child = new NoeudMiniMax(result, node);
                    child.setJoueur(-joueur);
                    //nbfils++;
                    //System.out.println(nbfils);
                }
            }
        }
    }

    @Override
    public Deplacement choix(Damier damier, int joueur) {
        System.out.println("Nouveau choix pas pris");
        NoeudMiniMax root = new NoeudMiniMax(damier);
        root.setJoueur(joueur);
        this.createTree(root, 0, null);
        System.out.println("Père : " + root);
        System.out.println("Fils 0 : " + root.getFils(0));
        root.calculeValeur();
        Damier tempBoard = ((NoeudMiniMax) root.getLesFils().get(root.maxFils())).getGameBoard();
        return Coup.retrouveDeplacement(damier, tempBoard, joueur);
    }

    @Override
    public Deplacement choix(Damier damier, int joueur, Position position) {
        Damier tempBoard = null;
        System.out.println("Nouveau choix pris");
        NoeudMiniMax root = new NoeudMiniMax(damier);
        root.setJoueur(joueur);
        this.createTree(root, 0, position);
        root.calculeValeur();
        if (!root.estFeuille()) {
            tempBoard = ((NoeudMiniMax) root.getLesFils().get(root.maxFils())).getGameBoard();
        } else {
            tempBoard = root.getGameBoard();
        }
        return Coup.retrouveDeplacement(damier, tempBoard, joueur);
    }
}

