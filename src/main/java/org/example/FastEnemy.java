package org.example;

public class FastEnemy extends Enemy{
    public FastEnemy(int hitPoints,int attackDamage,int x, int y){
        super(hitPoints,attackDamage,x,y);
    }

    @Override
    public String getSymbol() {
        return "F";
    }

    @Override
    public int getMovementSteps() {
        return 2;
    }
}
