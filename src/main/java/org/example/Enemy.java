package org.example;

public class Enemy extends Character{
    public Enemy(int hitPoints,int attackDamage,int x, int y){
        super(hitPoints,attackDamage,x,y);
    }

    public String getSymbol() {
        return "E";
    }

    public int getMovementSteps() {
        return 1;
    }
}
