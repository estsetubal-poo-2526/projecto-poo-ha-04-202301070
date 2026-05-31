package org.example;

public class Game {
    public static final int ROOM_WIDTH = 9;
    public static final int ROOM_HEIGHT = 7;

    private final Dungeon dungeon;
    private final Player player;
    private int currentRoomX;
    private int currentRoomY;
    private String message;

    public Game() {
        dungeon = new Dungeon();
        player = new Player(100, 15, ROOM_WIDTH / 2, ROOM_HEIGHT / 2);
        currentRoomX = 0;
        currentRoomY = 0;
        getCurrentRoom().addCharacter(player);
        message = "Apanha itens ao passar por cima deles.";
    }

    public Player getPlayer() {
        return player;
    }

    public Room getCurrentRoom() {
        return dungeon.getRoom(currentRoomX, currentRoomY);
    }

    public int getCurrentRoomX() {
        return currentRoomX;
    }

    public int getCurrentRoomY() {
        return currentRoomY;
    }

    public String getMessage() {
        return message;
    }

    public void movePlayer(int dx, int dy) {
        int nextX = player.getX() + dx;
        int nextY = player.getY() + dy;

        if (nextX < 0 || nextX >= ROOM_WIDTH || nextY < 0 || nextY >= ROOM_HEIGHT) {
            moveToNextRoom(nextX, nextY);
            return;
        }

        if (getCurrentRoom().getEnemyAt(nextX, nextY) != null) {
            return;
        }

        player.setPosition(nextX, nextY);
        collectItemIfPresent();
    }

    public void useItem(int index) {
        if (index < 0 || index >= player.getInventory().size()) {
            return;
        }

        String itemName = player.getInventory().get(index).getName();
        player.useItem(index);
        message = "Usaste: " + itemName + ".";
    }

    private void collectItemIfPresent() {
        RoomItem roomItem = getCurrentRoom().getItemAt(player.getX(), player.getY());

        if (roomItem == null) {
            message = "Posicao: (" + player.getX() + ", " + player.getY() + ").";
            return;
        }

        getCurrentRoom().removeItem(roomItem);
        player.addItem(roomItem.getItem());
        message = "Apanhaste: " + roomItem.getItem().getName() + ".";
    }

    private void moveToNextRoom(int nextX, int nextY) {
        int roomX = currentRoomX;
        int roomY = currentRoomY;
        int playerX = player.getX();
        int playerY = player.getY();

        if (nextX < 0) {
            roomX--;
            playerX = ROOM_WIDTH - 1;
        } else if (nextX >= ROOM_WIDTH) {
            roomX++;
            playerX = 0;
        }

        if (nextY < 0) {
            roomY--;
            playerY = ROOM_HEIGHT - 1;
        } else if (nextY >= ROOM_HEIGHT) {
            roomY++;
            playerY = 0;
        }

        if (dungeon.getRoom(roomX, roomY) == null) {
            message = "Nao existe sala nessa direcao.";
            return;
        }

        currentRoomX = roomX;
        currentRoomY = roomY;
        player.setPosition(playerX, playerY);
        message = "Entraste na sala (" + currentRoomX + ", " + currentRoomY + ").";
        collectItemIfPresent();
    }
}
