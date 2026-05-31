package org.example;

public abstract class Character {
    private int hitPoints;
    private int attackDamage;
    private Position position;

    public Character(int hitPoints,int attackDamage,int x, int y){
        this.hitPoints=hitPoints;
        this.attackDamage=attackDamage;
        position=new Position(x,y);
    }

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

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public void setPosition(int x, int y) {
        position.setX(x);
        position.setY(y);
    }

    public void moveBy(int dx, int dy) {
        position.moveBy(dx, dy);
    }

    public void attack(Character target) {
        target.takeDamage(attackDamage);
    }

    public void takeDamage(int damage) {
        hitPoints = Math.max(0, hitPoints - damage);
    }

    public boolean isAlive() {
        return hitPoints > 0;
    }
}
