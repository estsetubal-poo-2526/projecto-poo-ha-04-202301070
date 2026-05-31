package org.example;

public class Dungeon {
    private static final int WIDTH = 2;
    private static final int HEIGHT = 2;

    private final Room[][] rooms;

    public Dungeon() {
        rooms = new Room[HEIGHT][WIDTH];
        generateRooms();
    }

    public Room getRoom(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return null;
        }

        return rooms[y][x];
    }

    private void generateRooms() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                rooms[y][x] = new Room();
            }
        }

        getRoom(0, 0).addItem(new HealthPotion(20), 2, 1);
        getRoom(0, 0).addItem(new AttackPotion(5), 6, 5);
        getRoom(0, 0).addEnemy(new Enemy(30, 8, 1, 5));

        getRoom(1, 0).addItem(new HealthPotion(15), 1, 2);
        getRoom(1, 0).addItem(new AttackPotion(4), 7, 4);
        getRoom(1, 0).addEnemy(new Enemy(35, 9, 4, 3));

        getRoom(0, 1).addItem(new HealthPotion(25), 5, 1);
        getRoom(0, 1).addItem(new AttackPotion(3), 3, 5);
        getRoom(0, 1).addEnemy(new FastEnemy(25, 6, 6, 3));

        getRoom(1, 1).addItem(new HealthPotion(30), 2, 4);
        getRoom(1, 1).addItem(new AttackPotion(6), 6, 2);
        getRoom(1, 1).addEnemy(new Boss(80, 15, 4, 3));
    }
}
