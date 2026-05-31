package org.example;

public class Boss extends Enemy{
    public Boss(int hitPoints,int attackDamage,int x, int y){
        super(hitPoints,attackDamage,x,y);
    }

    @Override
    public String getSymbol() {
        return "B";
    }
}
