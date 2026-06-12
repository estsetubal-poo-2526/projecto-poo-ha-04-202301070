package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {
    @Test
    void startsInFirstRoomWithExpectedPlayerState() {
        Game game = new Game();
        Player player = game.getPlayer();

        assertEquals(0, game.getCurrentRoomX());
        assertEquals(0, game.getCurrentRoomY());
        assertEquals(100, player.getHitPoints());
        assertEquals(15, player.getAttackDamage());
        assertEquals(Game.ROOM_WIDTH / 2, player.getX());
        assertEquals(Game.ROOM_HEIGHT / 2, player.getY());
        assertFalse(game.isFinished());
        assertFalse(game.isVictory());
        assertEquals("Apanha itens ao passar por cima deles.", game.getMessage());
    }

    @Test
    void movesPlayerInsideCurrentRoom() {
        Game game = new Game();
        clearCurrentRoomEnemies(game);

        game.movePlayer(0, -1);

        assertEquals(4, game.getPlayer().getX());
        assertEquals(2, game.getPlayer().getY());
        assertEquals("Posicao: (4, 2).", game.getMessage());
    }

    @Test
    void refusesToMoveIntoMissingRoom() {
        Game game = new Game();
        clearCurrentRoomEnemies(game);

        moveRepeatedly(game, -1, 0, 5);

        assertEquals(0, game.getCurrentRoomX());
        assertEquals(0, game.getCurrentRoomY());
        assertEquals(0, game.getPlayer().getX());
        assertEquals(3, game.getPlayer().getY());
        assertEquals("Nao existe sala nessa direcao.", game.getMessage());
    }

    @Test
    void collectsAndUsesHealthPotion() {
        Game game = new Game();
        clearCurrentRoomEnemies(game);

        moveRepeatedly(game, 0, -1, 2);
        moveRepeatedly(game, -1, 0, 2);

        assertEquals(1, game.getPlayer().getInventory().size());
        assertInstanceOf(HealthPotion.class, game.getPlayer().getInventory().get(0));
        assertEquals("Apanhaste: Pocao de Vida.", game.getMessage());

        game.useItem(0);

        assertEquals(120, game.getPlayer().getHitPoints());
        assertTrue(game.getPlayer().getInventory().isEmpty());
        assertEquals("Usaste: Pocao de Vida.", game.getMessage());
    }

    @Test
    void defeatedEnemyIsRemovedFromRoom() {
        Game game = new Game();
        Room room = game.getCurrentRoom();
        room.getEnemies().clear();
        room.addEnemy(new Enemy(15, 0, 5, 3));

        game.movePlayer(1, 0);

        assertNull(room.getEnemyAt(5, 3));
        assertEquals(4, game.getPlayer().getX());
        assertEquals(3, game.getPlayer().getY());
        assertEquals("Inimigo derrotado.", game.getMessage());
    }

    @Test
    void enemyCounterAttackCanFinishTheGame() {
        Game game = new Game();
        Room room = game.getCurrentRoom();
        room.getEnemies().clear();
        room.addEnemy(new Enemy(100, 100, 5, 3));

        game.movePlayer(1, 0);

        assertTrue(game.isFinished());
        assertFalse(game.isVictory());
        assertEquals(0, game.getPlayer().getHitPoints());
        assertEquals("Game Over.", game.getMessage());
    }

    @Test
    void lockedExitDoesNotFinishGame() {
        Game game = new Game();
        enterBossRoomWithoutEnemies(game);

        moveRepeatedly(game, 1, 0, 8);
        moveRepeatedly(game, 0, 1, 6);

        assertEquals(Game.EXIT_X, game.getPlayer().getX());
        assertEquals(Game.EXIT_Y, game.getPlayer().getY());
        assertFalse(game.isFinished());
        assertFalse(game.isVictory());
    }

    @Test
    void defeatingBossUsingKeyAndEnteringExitWinsGame() {
        Game game = new Game();
        enterBossRoomWithoutEnemies(game);
        Room bossRoom = game.getCurrentRoom();
        bossRoom.addEnemy(new Boss(1, 0, 1, 0));

        game.movePlayer(1, 0);

        RoomItem droppedKey = bossRoom.getItemAt(1, 0);
        assertNull(bossRoom.getEnemyAt(1, 0));
        assertNotNull(droppedKey);
        assertInstanceOf(Key.class, droppedKey.getItem());
        assertEquals("Boss derrotado. A chave caiu no chao.", game.getMessage());

        game.movePlayer(1, 0);
        game.useItem(0);

        assertTrue(game.isExitUnlocked());
        assertEquals("Usaste a chave. A saida da sala do boss foi ativada.", game.getMessage());

        moveRepeatedly(game, 1, 0, 7);
        moveRepeatedly(game, 0, 1, 6);

        assertTrue(game.isFinished());
        assertTrue(game.isVictory());
        assertEquals(Game.EXIT_X, game.getPlayer().getX());
        assertEquals(Game.EXIT_Y, game.getPlayer().getY());
        assertEquals("Fim do jogo.", game.getMessage());
    }

    private static void enterBossRoomWithoutEnemies(Game game) {
        clearCurrentRoomEnemies(game);
        moveRepeatedly(game, 1, 0, 5);
        clearCurrentRoomEnemies(game);
        moveRepeatedly(game, 0, 1, 4);
        clearCurrentRoomEnemies(game);

        assertEquals(1, game.getCurrentRoomX());
        assertEquals(1, game.getCurrentRoomY());
        assertEquals(0, game.getPlayer().getX());
        assertEquals(0, game.getPlayer().getY());
    }

    private static void moveRepeatedly(Game game, int dx, int dy, int times) {
        for (int i = 0; i < times; i++) {
            game.movePlayer(dx, dy);
        }
    }

    private static void clearCurrentRoomEnemies(Game game) {
        game.getCurrentRoom().getEnemies().clear();
    }
}
