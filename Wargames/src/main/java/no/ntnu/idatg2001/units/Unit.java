package no.ntnu.idatg2001.units;

/**
 * Abstract class unit, represents the basic functions of a unit. Other types of unit are inheriting
 * from this class.
 *
 * @version 1.0.0
 */
public abstract class Unit {
  String name;
  int health;
  int attack;
  int armor;
  int numberOfDefends;
  int numberOfAttacks;

  /**
   * Constructs a new unit object with name, health, attack and armor.
   *
   * @param name Name of the unit.
   * @param health The health value of the unit.
   * @param attack The attack value of the unit.
   * @param armor The armor value of the unit.
   */
  protected Unit(String name, int health, int attack, int armor) {
    this.setName(name);
    this.setHealth(health);
    this.setAttack(attack);
    this.setArmor(armor);
    numberOfDefends = 0;
    numberOfAttacks = 0;
  }

  /**
   * Constructs a new unit object with name and health.
   *
   * @param name The name of the object.
   * @param health The health value of the unit.
   */
  protected Unit(String name, int health) {
    this.setName(name);
    this.setHealth(health);
    numberOfDefends = 0;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  /**
   * Mutator method to change the name of the unit, if the name is not blank.
   *
   * @param name The name of the unit.
   */
  public void setName(String name) {
    if (!name.isBlank()) {
      this.name = name;
    } else {
      throw new IllegalArgumentException("Invalid name.");
    }
  }

  /**
   * Mutator method to change the attack value of the unit, if it is between 0 and 1000.
   *
   * @param attack The attack value of the unit.
   */
  public void setAttack(int attack) {
    if (attack > 0 && attack < 1000) {
      this.attack = attack;
    } else {
      throw new IllegalArgumentException("Invalid attack value.");
    }
  }

  /**
   * Mutator method to change the armor value of the unit, if it is between 0 and 1000.
   *
   * @param armor The armor value of the unit.
   */
  public void setArmor(int armor) {
    if (armor > 0 && armor < 1000) {
      this.armor = armor;
    } else {
      throw new IllegalArgumentException("Invalid armor value.");
    }
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

  /** Increments the number of attacks to the unit. */
  public void setNumberOfAttacks() {
    this.numberOfAttacks++;
  }

  /** Increments the number of defends ot the unit. */
  public void setNumberOfDefends() {
    this.numberOfDefends++;
  }

  /**
   * Abstract method for attack-bonus to units.
   *
   * @return The value of the attack-bonus
   */
  public abstract int getAttackBonus();

  /**
   * Abstract method for resist-bonus to units.
   *
   * @return The value of the resist-bonus.
   */
  public abstract int getResistBonus();

  /**
   * Attack method, attacks another unit, takes attack, attack-bonus, armor, resist-bonus into
   * consideration.
   *
   * @param opponent The opponent to attack.
   */
  public void attack(Unit opponent) {
    opponent.setHealth(opponent.getHealth() - (this.getAttack() + this.getAttackBonus())
            + (opponent.getArmor() + opponent.getResistBonus()));
    opponent.setNumberOfDefends();
    this.setNumberOfAttacks();
  }

  /**
   * Returns the information about the unit.
   *
   * @return String, consists of name, health, attack and armor.
   */
  @Override
  public String toString() {
    return "\nName: " + name + "\n" + "Health: " + health + "\n" + "Attack: " + attack + "\n"
        + "Armor: " + armor;
  }
}
