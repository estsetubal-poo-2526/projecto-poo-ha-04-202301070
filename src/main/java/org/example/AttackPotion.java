package org.example;

public class AttackPotion extends Item{
    private int value;

    public AttackPotion(int value) {
        super("Pocao de Ataque");
        this.value = value;
    }

    @Override
    public void use(Player player){
        player.addAttackDamage(value);
    }

    @Override
    public String getSymbol() {
        return "A";
    }

}

