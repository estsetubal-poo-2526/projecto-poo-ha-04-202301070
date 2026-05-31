package org.example;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final List<Character> characters;
    private final List<Item> items;

    public Room() {
        characters = new ArrayList<>();
        items = new ArrayList<>();
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
