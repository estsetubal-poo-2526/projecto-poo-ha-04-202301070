package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dungeon {
    private final List<Room> rooms;

    public Dungeon() {
        rooms = new ArrayList<>();
        rooms.add(new Room());
    }

    public Room getCurrentRoom() {
        return rooms.getFirst();
    }
}
