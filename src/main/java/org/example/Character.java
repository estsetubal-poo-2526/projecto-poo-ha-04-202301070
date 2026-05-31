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

    public  int getHitPoints() {
        return hitPoints;
    }
    public void setHitPoints(int value){
        this.hitPoints=value;
    }
    public  int getAttackDamage() {
        return attackDamage;
    }
    public  void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
