package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player extends Character {
    private final List<Item> inventory;
    private boolean keyUsed;

    public Player(int hitPoints, int attackDamage, int x, int y) {
        super(hitPoints, attackDamage, x, y);
        inventory = new ArrayList<>();
    }

    public void addHealthPoints(int value) {
        setHitPoints(getHitPoints() + value);
    }

    public void addAttackDamage(int value) {
        setAttackDamage(getAttackDamage() + value);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    public void useItem(int index) {
        if (index >= 0 && index < inventory.size()) {
            Item item = inventory.remove(index);
            item.use(this);
        }
    }

    public void useKey() {
        keyUsed = true;
    }

    public boolean isKeyUsed() {
        return keyUsed;
    }

    public String getSymbol() {
        return "P";
    }
}
