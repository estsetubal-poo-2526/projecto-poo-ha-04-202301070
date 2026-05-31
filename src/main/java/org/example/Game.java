package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int ROOM_WIDTH = 9;
    public static final int ROOM_HEIGHT = 7;
    public static final int EXIT_X = ROOM_WIDTH - 1;
    public static final int EXIT_Y = ROOM_HEIGHT - 1;

    private static final int BOSS_ROOM_X = 1;
    private static final int BOSS_ROOM_Y = 1;

    private final Dungeon dungeon;
    private final Player player;
    private int currentRoomX;
    private int currentRoomY;
    private boolean finished;
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

    public boolean isFinished() {
        return finished;
    }

    public boolean isBossRoom() {
        return currentRoomX == BOSS_ROOM_X && currentRoomY == BOSS_ROOM_Y;
    }

    public boolean isExitTile(int x, int y) {
        return isBossRoom() && x == EXIT_X && y == EXIT_Y;
    }

    public boolean isExitUnlocked() {
        return player.isKeyUsed();
    }

    public void movePlayer(int dx, int dy) {
        if (finished) {
            return;
        }

        int nextX = player.getX() + dx;
        int nextY = player.getY() + dy;

        if (nextX < 0 || nextX >= ROOM_WIDTH || nextY < 0 || nextY >= ROOM_HEIGHT) {
            moveToNextRoom(nextX, nextY);
            return;
        }

        if (isExitTile(nextX, nextY) && player.isKeyUsed()) {
            player.setPosition(nextX, nextY);
            finished = true;
            message = "Fim do jogo.";
            return;
        }

        Enemy enemy = getCurrentRoom().getEnemyAt(nextX, nextY);
        if (enemy != null) {
            attackEnemy(enemy);
            enemiesTurn();
            checkPlayerAlive();
            return;
        }

        player.setPosition(nextX, nextY);
        collectItemIfPresent();
        enemiesTurn();
        checkPlayerAlive();
    }

    public void useItem(int index) {
        if (finished || index < 0 || index >= player.getInventory().size()) {
            return;
        }

        Item item = player.getInventory().get(index);
        String itemName = item.getName();
        player.useItem(index);

        if (item instanceof Key) {
            message = "Usaste a chave. A saida da sala do boss foi ativada.";
        } else {
            message = "Usaste: " + itemName + ".";
        }
    }

    private void attackEnemy(Enemy enemy) {
        player.attack(enemy);

        if (enemy.isAlive()) {
            message = "Atacaste " + enemy.getSymbol() + ". Vida restante: " + enemy.getHitPoints() + ".";
            return;
        }

        int enemyX = enemy.getX();
        int enemyY = enemy.getY();
        getCurrentRoom().removeEnemy(enemy);

        if (enemy instanceof Boss) {
            getCurrentRoom().addItem(new Key(), enemyX, enemyY);
            message = "Boss derrotado. A chave caiu no chao.";
        } else {
            message = "Inimigo derrotado.";
        }
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

    private void enemiesTurn() {
        List<Enemy> enemies = new ArrayList<>(getCurrentRoom().getEnemies());

        for (Enemy enemy : enemies) {
            if (!enemy.isAlive() || !player.isAlive()) {
                continue;
            }

            for (int step = 0; step < enemy.getMovementSteps(); step++) {
                if (isAdjacentToPlayer(enemy)) {
                    enemy.attack(player);
                    message = enemy.getSymbol() + " atacou-te. Vida: " + player.getHitPoints() + ".";
                    break;
                }

                moveEnemyTowardsPlayer(enemy);
            }
        }
    }

    private void moveEnemyTowardsPlayer(Enemy enemy) {
        int nextX = enemy.getX();
        int nextY = enemy.getY();
        int dx = Integer.compare(player.getX(), enemy.getX());
        int dy = Integer.compare(player.getY(), enemy.getY());

        if (Math.abs(player.getX() - enemy.getX()) >= Math.abs(player.getY() - enemy.getY())) {
            nextX += dx;
        } else {
            nextY += dy;
        }

        if (nextX == player.getX() && nextY == player.getY()) {
            enemy.attack(player);
            message = enemy.getSymbol() + " atacou-te. Vida: " + player.getHitPoints() + ".";
            return;
        }

        if (nextX < 0 || nextX >= ROOM_WIDTH || nextY < 0 || nextY >= ROOM_HEIGHT) {
            return;
        }

        if (getCurrentRoom().getEnemyAt(nextX, nextY) != null) {
            return;
        }

        if (getCurrentRoom().getItemAt(nextX, nextY) != null || isExitTile(nextX, nextY)) {
            return;
        }

        enemy.setPosition(nextX, nextY);
    }

    private boolean isAdjacentToPlayer(Enemy enemy) {
        int distance = Math.abs(enemy.getX() - player.getX()) + Math.abs(enemy.getY() - player.getY());
        return distance == 1;
    }

    private void checkPlayerAlive() {
        if (!player.isAlive()) {
            finished = true;
            message = "Game Over.";
        }
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
