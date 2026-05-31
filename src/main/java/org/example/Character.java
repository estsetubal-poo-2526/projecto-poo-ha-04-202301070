package org.example;

public abstract class Character {
    private int hitPoints;
    private int attackDamage;
    //Posicao
    private Position position;
    public Character(int hitPoints,int attackDamage,int x, int y){
        this.hitPoints=hitPoints;
        this.attackDamage=attackDamage;
        position=new Position(x,y);
    }
    public void Move(){}
    public void Attack(){}

    public static int getHitPoints() {
        return hitPoints;
    }
    public static void setHitPoints(int value){
        this.hitPoints=value;
    }
    public static int getAttackDamage() {
        return attackDamage;
    }
    public static void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
