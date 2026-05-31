package org.example;

public class Game {
    public static final int ROOM_WIDTH = 9;
    public static final int ROOM_HEIGHT = 7;

    private final Dungeon dungeon;
    private final Player player;
    private String message;

    public Game() {
        dungeon = new Dungeon();
        player = new Player(100, 15, ROOM_WIDTH / 2, ROOM_HEIGHT / 2);
        dungeon.getCurrentRoom().addCharacter(player);
    }


    public Player getPlayer() {
        return player;
    }

}
