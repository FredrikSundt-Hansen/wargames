package no.ntnu.idatg2001.units;

public abstract class Unit {
    String name;
    int health;
    int attack;
    int armor;
    int numberOfDefends;
    int numberOfAttacks;

    public Unit(String name, int health, int attack, int armor) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
        numberOfDefends = 0;
    }

    public Unit(String name, int health) {
        this.name = name;
        this.health = health;
        numberOfDefends = 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setNumberOfAttacks() {
          this.numberOfAttacks++;
    }

    public void setNumberOfDefends() {
        this.numberOfDefends++;
    }

    public abstract int getAttackBonus();

    public abstract int getResistBonus();

    public void attack(Unit opponent){
        opponent.setHealth(opponent.getHealth() - (this.getAttack() + this.getAttackBonus())
                + (opponent.getArmor() + opponent.getResistBonus()));
        opponent.setNumberOfDefends();
        this.setNumberOfAttacks();
    }

    @Override
    public String toString() {
        return  "Name " + name + "\n" +
                "Health " + health  + "\n" +
                "Attack " + attack  + "\n"  +
                "Armor " + armor;

    }
}
