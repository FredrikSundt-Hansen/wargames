package no.ntnu.idatg2001.wargames.model.units;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class, used to make units without exposing creation logic to user.
 */
public class UnitFactory {
  private static volatile UnitFactory instance;

  /**
   * Private constructor restricting object creation,
   * making constructor only available when used with getInstance.
   */
  private UnitFactory() {}

  /**
   * Static method to get an instance of the class. Singleton design.
   *
   * @return An instance of UnitFactory.
   */
  public static UnitFactory getInstance() {
    if (instance == null) {
      synchronized (UnitFactory.class) {
        instance = new UnitFactory();
      }
    }
    return instance;
  }

  /**
   * Method to create a single Unit, based on unitType input.
   * Passes the name and health to the new object.
   *
   * @param unitType String, the unit type to create as a string.
   *                 "Infantry", "Ranged", "Cavalry" or "Commander" for the different units.
   * @param name The name of the new unit.
   * @param health The health of the new unit.
   * @return An instance of the new unit.
   */
  public Unit createUnit(String unitType, String name, int health) {
    switch (unitType.toLowerCase()) {
      case "infantry":
        return new InfantryUnit(name, health);
      case "cavalry":
        return new CavalryUnit(name, health);
      case "ranged":
        return new RangedUnit(name, health);
      case "commander":
        return new CommanderUnit(name, health);
      default:
        return null;
    }
  }

  /**
   * Method to create n many units. Creates units based on unitType input.
   *
   * @param unitType String, the unit type to create as a string. "Infantry", "Ranged", "Cavalry" or
   *     "Commander" for the different units.
   * @param name The name of the new unit.
   * @param health The health of the new unit.
   * @param numberUnits How many units to create of that type.
   * @return List of units, with the given health, name, and number.
   */
  public List<Unit> createMultipleUnits(String unitType, String name, int health, int numberUnits) {
    List<Unit> unitList = new ArrayList<>();
    for (int i = 0; i < numberUnits; i++) {
      unitList.add(createUnit(unitType, name, health));
    }
    return unitList;
  }
}
