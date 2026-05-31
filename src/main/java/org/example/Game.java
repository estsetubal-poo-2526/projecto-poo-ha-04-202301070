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
        dungeon.getCurrentRoom().addItem(new HealthPotion(20), 2, 1);
        dungeon.getCurrentRoom().addItem(new AttackPotion(5), 6, 5);
        message = "Apanha itens ao passar por cima deles.";
    }


    public Player getPlayer() {
        return player;
    }

    public Room getCurrentRoom() {
        return dungeon.getCurrentRoom();
    }

    public String getMessage() {
        return message;
    }

    public void movePlayer(int dx, int dy) {
        int nextX = player.getX() + dx;
        int nextY = player.getY() + dy;

        if (nextX < 0 || nextX >= ROOM_WIDTH || nextY < 0 || nextY >= ROOM_HEIGHT) {
            message = "Nao podes sair da sala nesta fase.";
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
}
