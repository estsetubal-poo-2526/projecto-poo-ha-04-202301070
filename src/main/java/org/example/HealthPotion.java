package org.example;

public class HealthPotion extends Item{
    private int value;

    public HealthPotion(int value){
        super("Pocao de Vida");
        this.value=value;
    }

    @Override
    public void use(Player player){
        player.addHealthPoints(value);
    }

    @Override
    public String getSymbol() {
        return "H";
    }

}
