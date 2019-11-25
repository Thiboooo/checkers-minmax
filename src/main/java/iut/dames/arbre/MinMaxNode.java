package iut.dames.arbre;

import iut.dames.damier.Damier;

import java.util.ArrayList;

public class MinMaxNode {
    private Damier gameBoard;
    private int value;
    private ArrayList<MinMaxNode> children = new ArrayList<>();
    private int player;

    public MinMaxNode(Damier gameBoard, int player) {
        this.gameBoard = gameBoard;
        this.player = player;
    }

    public MinMaxNode(Damier gameBoard, int value, int player) {
        this.gameBoard = gameBoard;
        this.value = value;
        this.player = player;
    }

    public static MinMaxNode maxChild(MinMaxNode node) {
        MinMaxNode maxNode = node.getChildren().get(0);
        for (MinMaxNode childNode : node.getChildren()) {
            if (maxNode.getValue() < childNode.getValue()) {
                maxNode = childNode;
            }
        }
        return maxNode;
    }

    public static int evaluate(MinMaxNode node, int joueur, int alpha, int beta) {

        if (node.getPlayer() == joueur && !node.isLeaf()) {
            for (MinMaxNode child : node.getChildren()) {
                node.setValue(Integer.max(node.getValue(), MinMaxNode.evaluate(child, -joueur, alpha, beta)));
                alpha = Integer.max(node.getValue(), alpha);
                if (alpha >= beta) {
                    break;
                }
            }
            return node.getValue();
        } else if (node.getPlayer() == -joueur && !node.isLeaf()) {
            for (MinMaxNode child : node.getChildren()) {
                node.setValue(Integer.min(node.getValue(), MinMaxNode.evaluate(child, -joueur, alpha, beta)));
                beta = Integer.min(node.getValue(), beta);
                if (alpha >= beta) {
                    break;
                }
            }
            return node.getValue();
        }
        return node.getValue();
    }

    public Damier getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Damier gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ArrayList<MinMaxNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<MinMaxNode> children) {
        this.children = children;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void addChild(MinMaxNode child) {
        this.children.add(child);
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    @Override
    public String toString() {
        return "MinMaxNode{" +
                "value=" + value +
                ", children=" + children +
                '}';
    }
}


