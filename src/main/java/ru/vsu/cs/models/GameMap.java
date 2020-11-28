package ru.vsu.cs.models;

import java.util.HashMap;

public class GameMap {

    private int[] startPoints;
    private Station[] stations;

    public int[] getStartPoints() {
        return startPoints;
    }

    public Station[] getStations() {
        return stations;
    }

    public void setStartPoints(int[] startPoints) {
        this.startPoints = startPoints;
    }

    public void setStations(Station[] stations) {
        this.stations = stations;
    }
}