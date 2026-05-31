package org.example;

public class Player extends Character{
    public Player(int hitPoints,int attackDamage,int x, int y){
        super(hitPoints,attackDamage,x,y);
    }
    public static void  addHealthPoints(int value){
        setHitPoints(getHitPoints()+value);
    }
    public static void addAttackDamage(int value){
        setAttackDamage(getAttackDamage()+value);
    }
}
