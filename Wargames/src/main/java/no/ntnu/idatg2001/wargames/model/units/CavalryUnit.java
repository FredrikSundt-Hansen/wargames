package no.ntnu.idatg2001.wargames.model.units;

/**
 * Cavalry unit, unit with a strong first attack and a benefit in close quarter combat.
 *
 * @version 1.0.0
 */
public class CavalryUnit extends Unit {
  /**
   * Constructs new cavalry unit, with all parameters.
   *
   * @param name Name of the unit.
   * @param health Health value of the unit.
   * @param attack Attack value of the unit.
   * @param armor Armor value of the unit.
   */
  public CavalryUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
    type = "cavalryunit";
  }

  /**
   * Constructs new cavalry unit, with name and health.
   *
   * @param name Name of the unit.
   * @param health Health value of the unit.
   */
  public CavalryUnit(String name, int health) {
    super(name, health);
    attack = 20;
    armor = 12;
    type = "cavalryunit";
  }

  /**
   * Constructor using unit copy constructor.
   *
   * @param unit The cavalry unit to copy.
   */
  public CavalryUnit(Unit unit) {
    super(unit);
    type = "cavalryunit";
  }

  @Override
  public void setTerrain(String terrain) {
    if (terrain.equalsIgnoreCase(Terrain.PLAINS.name())) {
      terrainAttackBonus = 1;
    } else if (terrain.equalsIgnoreCase(Terrain.FOREST.name())) {
      terrainDefendBonus = -1;
    }
  }

  @Override
  public int getAttackBonus() {
    if (this.numberOfAttacks == 0) {
      return 4 + terrainAttackBonus;
    } else {
      return 2 + terrainAttackBonus;
    }
  }

  @Override
  public int getResistBonus() {
    return 1 + terrainDefendBonus;
  }
}
