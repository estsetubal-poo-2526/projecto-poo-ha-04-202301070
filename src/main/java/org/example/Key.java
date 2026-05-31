package org.example;

public class Key extends Item {
    public Key() {
        super("Chave");
    }

    @Override
    public void use(Player player) {
        // A chave sera usada numa fase futura para abrir a saida.
    }

    @Override
    public String getSymbol() {
        return "K";
    }
}
