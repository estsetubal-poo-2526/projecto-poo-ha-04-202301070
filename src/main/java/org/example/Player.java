package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player extends Character {
    private final List<Item> inventory;

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
}
