package org.example;

import java.util.ArrayList;
import java.util.List;

public class Room {
    List<Character> characters;
    List<Item> items;
    public Room(){
        characters = new ArrayList<>();
        items = new ArrayList<>();
    }
}
