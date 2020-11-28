package ru.vsu.cs.models.characters;

public class MrX extends Character {
    private int doubleMove;
    private int blackTicket;

    public MrX(int detectiveCount) {
        this.doubleMove = 2;
        this.blackTicket = detectiveCount;
        this.characterType = CharacterType.MRX;
    }

    public int getDoubleMove() {
        return doubleMove;
    }

    public int getBlackTicket() {
        return blackTicket;
    }

    public void setDoubleMove(int doubleMove) {
        this.doubleMove = doubleMove;
    }

    public void setBlackTicket(int blackTicket) {
        this.blackTicket = blackTicket;
    }
}