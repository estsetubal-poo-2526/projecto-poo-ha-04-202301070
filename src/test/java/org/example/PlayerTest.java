package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {
    @Test
    void healthPotionIncreasesHitPointsAndLeavesInventory() {
        Player player = new Player(40, 10, 0, 0);
        player.addItem(new HealthPotion(20));

        player.useItem(0);

        assertEquals(60, player.getHitPoints());
        assertTrue(player.getInventory().isEmpty());
    }

    @Test
    void attackPotionIncreasesAttackDamageAndLeavesInventory() {
        Player player = new Player(40, 10, 0, 0);
        player.addItem(new AttackPotion(5));

        player.useItem(0);

        assertEquals(15, player.getAttackDamage());
        assertTrue(player.getInventory().isEmpty());
    }

    @Test
    void keyMarksPlayerKeyAsUsedAndLeavesInventory() {
        Player player = new Player(40, 10, 0, 0);
        player.addItem(new Key());

        player.useItem(0);

        assertTrue(player.isKeyUsed());
        assertTrue(player.getInventory().isEmpty());
    }

    @Test
    void invalidInventoryIndexDoesNothing() {
        Player player = new Player(40, 10, 0, 0);
        player.addItem(new Key());

        player.useItem(1);

        assertEquals(1, player.getInventory().size());
        assertEquals(40, player.getHitPoints());
        assertEquals(10, player.getAttackDamage());
    }

    @Test
    void inventoryCannotBeModifiedDirectly() {
        Player player = new Player(40, 10, 0, 0);

        assertThrows(UnsupportedOperationException.class, () -> player.getInventory().add(new Key()));
    }
}
