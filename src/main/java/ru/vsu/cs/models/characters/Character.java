package ru.vsu.cs.models.characters;

import ru.vsu.cs.models.Station;
import ru.vsu.cs.models.WayType;

public class Character {
    protected int busTickets = 10;
    protected int metroTickets = 4;
    protected int taxiTickets = 8;

    protected Station currentPosition;

    protected CharacterType characterType;

    public void setCurrentPosition(Station currentPosition){
        this.currentPosition = currentPosition;
    }

    public Station getCurrentPosition(){
        return currentPosition;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public int getBusTickets() {
        return busTickets;
    }

    public int getMetroTickets() {
        return metroTickets;
    }

    public int getTaxiTickets() {
        return taxiTickets;
    }

    public void setBusTickets(int busTickets) {
        this.busTickets = busTickets;
    }

    public void setMetroTickets(int metroTickets) {
        this.metroTickets = metroTickets;
    }

    public void setTaxiTickets(int taxiTickets) {
        this.taxiTickets = taxiTickets;
    }

    public int getTicketsByType(WayType wayType){
        switch (wayType){
            case BUS: return busTickets;
            case TAXI: return taxiTickets;
            case METRO: return metroTickets;
        }
        return -1;
    }
}