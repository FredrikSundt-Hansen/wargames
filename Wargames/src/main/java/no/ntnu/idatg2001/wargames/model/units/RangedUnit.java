package no.ntnu.idatg2001.wargames.model.units;

/**
 * Ranged unit, a unit with benefit of attacking from a distance.
 *
 * @version 1.0.0
 */
public class RangedUnit extends Unit {

  /**
   * Constructs a new ranged unit, with all parameters.
   *
   * @param name The name of the unit.
   * @param health The health value of the hunit.
   * @param attack The attack value of the unit.
   * @param armor The armor value of the unit.
   */
  public RangedUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
    type = "rangedunit";
  }

  /**
   * Constructs new ranged unit, with name and health.
   *
   * @param name The name of the unit.
   * @param health The health value of the unit.
   */
  public RangedUnit(String name, int health) {
    super(name, health);
    attack = 15;
    armor = 8;
    type = "rangedunit";
  }

  /**
   * Constructor using unit copy constructor.
   *
   * @param unit The ranged unit to copy.
   */
  public RangedUnit(Unit unit) {
    super(unit);
    type = "rangedunit";
  }

  @Override
  public void setTerrain(String terrain) {
    if (terrain.equalsIgnoreCase(Terrain.HILL.name())
        || terrain.equalsIgnoreCase(Terrain.HILL.name() + "s")) {
      terrainAttackBonus = 1;
    } else if (terrain.equalsIgnoreCase(Terrain.FOREST.name())) {
      terrainAttackBonus = -1;
    }
  }

  @Override
  public int getAttackBonus() {
    return 3 + terrainAttackBonus;
  }

  @Override
  public int getResistBonus() {
    if (numberOfDefends == 0) {
      return 6 + terrainDefendBonus;
    } else if (numberOfDefends == 1) {
      return 4 + terrainDefendBonus;
    } else {
      return 2 + terrainDefendBonus;
    }
  }
}
