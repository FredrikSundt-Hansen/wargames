package no.ntnu.idatg2001.units;

public abstract class Unit {
    String name;
    int health;
    int attack;
    int armor;
    int numberOfDefends;
    int numberOfAttacks;

    /**
     * Constructs a unit with   
     * @param name Name of the unit.
     * @param health The health value of the unit.
     * @param attack The attack value of the unit.
     * @param armor The armor value of the unit.
     */
    public Unit(String name, int health, int attack, int armor) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
        numberOfDefends = 0;
    }

    /**
     * Constructor for Unit objects.
     * @param name The name of the object.
     * @param health The health value of the unit.
     */
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

    /**
     * Increments the number of attacks to the unit.
     */
    public void setNumberOfAttacks() {
          this.numberOfAttacks++;
    }

    /**
     * Increments the number of defends ot the unit.
     */
    public void setNumberOfDefends() {
        this.numberOfDefends++;
    }

    /**
     * Abstract method for attack-bonus to units.
     * @return The value of the attack-bonus
     */
    public abstract int getAttackBonus();

    /**
     * Abstract method for resist-bonus to units.
     * @return The value of the resist-bonus.
     */
    public abstract int getResistBonus();

    /**
     * Attack method, attacks another unit, takes attack, attack-bonus, armor, resist-bonus into consideration.
     * @param opponent The opponent to attack.
     */
    public void attack(Unit opponent){
        opponent.setHealth(opponent.getHealth() - (this.getAttack() + this.getAttackBonus())
                + (opponent.getArmor() + opponent.getResistBonus()));
        opponent.setNumberOfDefends();
        this.setNumberOfAttacks();
    }

    /**
     * Returns the information about the unit.
     * @return String, consists of name, health, attack and armor.
     */
    @Override
    public String toString() {
    return "\nName: " + name + "\n" + "Health: " + health + "\n" + "Attack: " + attack + "\n"
        + "Armor: " + armor;
    }
}
