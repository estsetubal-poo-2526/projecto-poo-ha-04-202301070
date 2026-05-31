package org.example;

public class HealthPotion extends Item{
    private int value;
    public HealthPotion(int value){
        this.value=value;
    }
    @Override
    public void Use(Player player){
        player.addHealthPoints(value);
    }

}
