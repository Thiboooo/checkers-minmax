package iut.dames.ia.papaJohnny;

import iut.dames.damier.*;

import java.util.ArrayList;
import java.util.List;

public class MinMaxNode {
    private Damier gameBoard;
    private int value;
    private Position startPos = null; //Permet de forcer les prises / fixer le pion de départ
    private ArrayList<MinMaxNode> children = new ArrayList<>();
    private int player;

    public MinMaxNode(Damier gameBoard, int player) {
        this.gameBoard = gameBoard;
        this.player = player;
    }

    public MinMaxNode(Damier gameBoard, int player, Position startPos) {
        this.gameBoard = gameBoard;
        this.startPos = startPos;
        this.player = player;
    }

    //Getters
    public Position getStartPos() {
        return startPos;
    }

    public Damier getGameBoard() {
        return gameBoard;
    }

    public int getPlayer() {
        return player;
    }

    public int getValue() {
        return value;
    }

    public ArrayList<MinMaxNode> getChildren() {
        return children;
    }

    //Setters
    public void setValue(int value) {
        this.value = value;
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }


    public void createChildren() {
        boolean taking = false;
        List<Position> startPos = new ArrayList<Position>();

        //On vérifie si on est dans le cas d'une prise multiple/forcée
        if (this.getStartPos() != null) {
            taking = true;
            startPos.add(this.getStartPos());
        } else {
            startPos = this.getGameBoard().listePositionPion(this.getPlayer());
        }
        for (Position start : startPos) {
            List<Position> endPos = Coup.listeArriveValide(this.getGameBoard(), start, this.getPlayer(), taking);
            for (Position end : endPos) {
                Deplacement move = new Deplacement(start, end);
                ResultatAction valid = Coup.valide(this.getGameBoard(), move, this.getPlayer(), taking);
                if (valid.getValide()) {
                    Damier result = Coup.execute(this.getGameBoard(), move, valid);

                    if (valid.getPris() != null && MinMaxHelper.maxPriseJeu(this.getGameBoard(), this.getPlayer()) > 1) { //Cas où un état fils donne lieu à une prise multiple
                        this.children.add(new MinMaxNode(result, this.getPlayer(), end)); //On suppose qu'un état donnant lieu à une prise multiple donne naissance à un fils avec la même valeur de joueur, dans la mesure où le joueur joue effectivement plusieurs fois à la suite
                    }
                    else{
                        this.children.add(new MinMaxNode(result, -this.getPlayer())); 
                    }
                }
            }
        }
    }

    public ArrayList<MinMaxNode> getMaxChildren() {
        ArrayList<MinMaxNode> maxChildren = new ArrayList<>();
        int max = Integer.MIN_VALUE;
        for (MinMaxNode child : this.getChildren()) {
            max = Integer.max(max, child.getValue());
        }
        for (MinMaxNode child : this.getChildren()) {
            if (child.getValue() == max) {
                maxChildren.add(child);
            }
        }
        return maxChildren;

    }
}


