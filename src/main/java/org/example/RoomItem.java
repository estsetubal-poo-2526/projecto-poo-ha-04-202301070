package org.example;

public class RoomItem {
    private final Item item;
    private final Position position;

    public RoomItem(Item item, int x, int y) {
        this.item = item;
        this.position = new Position(x, y);
    }

    public Item getItem() {
        return item;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }
}
