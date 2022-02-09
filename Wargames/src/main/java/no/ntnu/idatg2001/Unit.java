package no.ntnu.idatg2001;

public abstract class Unit {
    private String name;
    private int health;
    private int attack;
    private int armor;

    public Unit(String name, int health, int attack, int armor) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
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

    public abstract int getAttackBonus();

    public abstract int getResistBonus();

    public void attack(Unit opponent){
        setHealth(opponent.getHealth() - (opponent.getAttack() + getAttackBonus())
                + (opponent.getArmor() + getResistBonus()));
    }

    @Override
    public String toString() {
        return  name + " :: " +
                health +  " :: " +
                attack +  " :: " +
                armor;

    }
}
