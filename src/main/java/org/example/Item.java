package org.example;

public abstract class Item {
    private final String name;

    protected Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void use(Player player);


    public abstract String getSymbol();
}
