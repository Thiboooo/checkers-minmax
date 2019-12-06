package iut.dames.arbre;

import iut.dames.damier.*;

import java.util.ArrayList;
import java.util.List;

public class MinMaxNode {
    private Damier gameBoard;
    private int value;
    private ArrayList<MinMaxNode> children = new ArrayList<>();
    private int player;

    public MinMaxNode(Damier gameBoard, int player) {
        this.gameBoard = gameBoard;
        this.player = player;
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

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public void createChildren(Position starting) {
        boolean taking = false;
        List<Position> startPos = new ArrayList<Position>();
        if (starting != null) {
            taking = true;
            startPos.add(starting);

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
                    this.children.add(new MinMaxNode(result, -this.getPlayer()));
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


