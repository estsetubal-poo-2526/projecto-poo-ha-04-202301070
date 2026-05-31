package org.example;

public class Key extends Item {
    public Key() {
        super("Chave");
    }

    @Override
    public void use(Player player) {
        player.useKey();
    }

    @Override
    public String getSymbol() {
        return "K";
    }
}
