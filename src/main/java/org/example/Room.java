package org.example;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final List<Character> characters;
    private final List<RoomItem> items;
    private final List<Enemy> enemies;

    public Room() {
        characters = new ArrayList<>();
        items = new ArrayList<>();
        enemies = new ArrayList<>();
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }


    public Enemy getEnemyAt(int x, int y) {
        for (Enemy enemy : enemies) {
            if (enemy.getX() == x && enemy.getY() == y) {
                return enemy;
            }
        }
        return null;
    }

    public void addItem(Item item, int x, int y) {
        items.add(new RoomItem(item, x, y));
    }

    public RoomItem getItemAt(int x, int y) {
        for (RoomItem roomItem : items) {
            if (roomItem.getX() == x && roomItem.getY() == y) {
                return roomItem;
            }
        }
        return null;
    }

    public void removeItem(RoomItem roomItem) {
        items.remove(roomItem);
    }
}
