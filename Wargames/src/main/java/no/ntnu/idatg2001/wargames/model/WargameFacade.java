package no.ntnu.idatg2001.wargames.model;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.armies.Army;
import no.ntnu.idatg2001.wargames.model.battles.Battle;
import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.units.RangedUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.model.units.UnitFactory;

/**
 * Facade for the application, as a singleton. Holds all the model classes
 * that are being used and binds the controller classes with the model classes to reduce
 * coupling between classes and preventing communication between controller classes.
 */
public class WargameFacade {

  private static volatile WargameFacade instance;
  private final Army armyOne;
  private final Army armyTwo;
  private Battle battle;

  /**
   * Constructor creates an instance.
   * Notice this is a singleton, so this will only be initiated once.
   */
  private WargameFacade() {
    armyOne = new Army();
    armyTwo = new Army();
  }

  /**
   * Accessor method for the WargameFacade instance. Will only create on instance.
   * @return The current instance.
   */
  public static WargameFacade getInstance() {
    if (instance == null) {
      synchronized (WargameFacade.class) {
        instance = new WargameFacade();
      }
    }
    return instance;
  }

  public void setBattle(Battle battle) {
    this.battle = battle;
  }

  public List<Unit> getArmyOneUnits() {
    return armyOne.getAllUnits();
  }

  public List<Unit> getArmyTwoUnits() {
    return armyTwo.getAllUnits();
  }

  public void setArmyOneName(String name) {
    armyOne.setName(name);
  }

  public void setArmyTwoName(String name) {
    armyTwo.setName(name);
  }

  public String getArmyOneName() {
    return armyOne.getName();
  }

  public String getArmyTwoName() {
    return armyTwo.getName();
  }

  public void addUnits(List<Unit> units, String armyName) {
    if (armyName.equalsIgnoreCase(armyOne.getName())) {
      armyOne.addAllUnits(units);
    } else if (armyName.equalsIgnoreCase(armyTwo.getName())) {
      armyTwo.addAllUnits(units);
    }
  }

  public void simulate(String terrain) {
    battle.simulate(terrain);
  }

  /**
   * Method to register an observer to the battle instance.
   * @param unitObserver
   */
  public void registerObserver(UnitObserver unitObserver) {
    battle.register(unitObserver);
  }

  /**
   * Method to get a list of all current types of units.
   * @return List of all unit types.
   */
  public List<String> getAllDifferentUnits() {
    List<String> unitTypes = new ArrayList<>();
    unitTypes.add("Infantry");
    unitTypes.add("Ranged");
    unitTypes.add("Cavalry");
    unitTypes.add("Commander");

    return unitTypes;
  }

  /**
   * Method to get a list of the army names.
   * @return List of the army names.
   */
  public List<String> getArmyNames() {
    List<String> armiesNames = new ArrayList<>();
    String armyOneName = armyOne.getName();
    String armyTwoName = armyTwo.getName();
    if (armyOneName != null) {
      armiesNames.add(armyOneName);
    }
    if (armyTwoName != null) {
      armiesNames.add(armyTwoName);
    }
    return armiesNames;
  }

  /**
   * Method to make several units using UnitFactory.
   * @param type The type of the unit (Infantry, Commander etc. )
   * @param name The name of the unit.
   * @param health The health value of the unit.
   * @param attack The attack value of the unit.
   * @param armor The armor value of the unit.
   * @param amount The amount of units ot make.
   * @return List of all units with these characteristics.
   */
  public List<Unit> makeUnits(
      String type, String name, int health, int attack, int armor, int amount) {
    return UnitFactory.getInstance()
          .createMultipleUnits(type, name, health, attack, armor, amount);

  }
}
