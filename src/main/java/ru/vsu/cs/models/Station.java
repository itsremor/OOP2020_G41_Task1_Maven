package ru.vsu.cs.models;

import ru.vsu.cs.models.characters.Character;
import ru.vsu.cs.models.characters.CharacterType;

import java.util.HashMap;

// TODO: 17.10.2020 Class edge, that can see only ways between stations. Map: Type -> Edge[], try to make 1 collection for every edge

public class Station{
    private int id;
    private HashMap<WayType, Station[]> ways = new HashMap<>();
    private CharacterType characterType = CharacterType.NOBODY;

    public HashMap<WayType, Station[]> getWays() {
        return ways;
    }

    public CharacterType getCharacter() {
        return characterType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }
}
