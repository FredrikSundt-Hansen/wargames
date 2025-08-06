package no.ntnu.idatg2001.wargames.model.units;

import java.util.Objects;

/**
 * Abstract class unit, represents the basic functions of a unit. Other types of unit are inheriting
 * from this class.
 *
 * @version 1.0.0
 */
public abstract class Unit {
  String type;
  String name;
  int health;
  int attack;
  int armor;
  int numberOfDefends;
  int numberOfAttacks;
  int terrainDefendBonus;
  int terrainAttackBonus;

  /**
   * Three different types of terrain, used to calculate attack and resist bonus.
   */
  enum Terrain {
    PLAINS, FOREST, HILL
  }

  /**
   * Method to set the terrain of the unit. Accepts three different types, plains, forest and hill.
   *
   * @param terrain String, the terrain to change to.
   */
  public abstract void setTerrain(String terrain);

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
  }

  /**
   * Copy constructor for units.
   *
   * @param unit The unit to copy.
   */
  protected Unit(Unit unit) {
    this.type = unit.type;
    this.name = unit.name;
    this.health = unit.health;
    this.attack = unit.attack;
    this.armor = unit.armor;
    this.numberOfDefends = unit.numberOfDefends;
    this.numberOfAttacks = unit.numberOfAttacks;
    this.terrainDefendBonus = unit.terrainDefendBonus;
    this.terrainAttackBonus = unit.terrainAttackBonus;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  /**
   * Mutator method to change the name of the unit.
   *
   * @param name The name of the unit.
   * @exception IllegalArgumentException - If name is empty (null).
   */
  public void setName(String name) throws IllegalArgumentException {
    if (!name.isEmpty()) {
      this.name = name;
    } else {
      throw new IllegalArgumentException("Invalid name.");
    }
  }

  /**
   * Mutator method to change the attack value of the unit.
   *
   * @param attack The attack value of the unit.
   * @exception IllegalArgumentException - If the value is less than zero, or more than 1000.
   */
  public void setAttack(int attack) throws IllegalArgumentException {
    if (attack >= 0 && attack < 1000) {
      this.attack = attack;
    } else {
      throw new IllegalArgumentException("Invalid attack value.");
    }
  }

  /**
   * Mutator method to change the armor value of the unit.
   *
   * @param armor The armor value of the unit.
   * @exception IllegalArgumentException - If the value is less than zero, or more than 1000.
   */
  public void setArmor(int armor) throws IllegalArgumentException {
    if (armor >= 0 && armor < 1000) {
      this.armor = armor;
    } else {
      throw new IllegalArgumentException("Invalid armor value.");
    }
  }

  public String getType() {
    return getClass().getSimpleName();
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



  public int getNumberOfDefends() {
    return numberOfDefends;
  }

  public int getNumberOfAttacks() {
    return numberOfAttacks;
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
  public String getUnitInformation() {
    return "\nName: " + name + "\n" + "Health: " + health + "\n" + "Attack: " + attack + "\n"
        + "Armor: " + armor;
  }

  /**
   * Compares to units. Return true if they are equal, takes the name, health, attack and armor of
   * the unit into consideration.
   *
   * @param o Army to compare.
   * @return True if the two armies are equal, and false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Unit unit)) {
      return false;
    }
    return health == unit.health
        && attack == unit.attack
        && armor == unit.armor
        && Objects.equals(name, unit.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, health, attack, armor);
  }
}
