package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character{
    List inventory;
    public Player(int hitPoints,int attackDamage,int x, int y){
        super(hitPoints,attackDamage,x,y);
        inventory=new ArrayList<>();
    }
    public void  addHealthPoints(int value){
        setHitPoints(getHitPoints()+value);
    }
    public void addAttackDamage(int value){
        setAttackDamage(getAttackDamage()+value);
    }
    public void UseItem(Item item){

    }
}
