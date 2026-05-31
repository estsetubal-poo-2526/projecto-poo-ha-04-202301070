package org.example;

public class AttackPotion extends Item{
    private int value;
    public AttackPotion(int value){
        this.value=value;
    }
    @Override
    public void Use(Player player){
        Player.addAttackDamage(value);
    }
}

