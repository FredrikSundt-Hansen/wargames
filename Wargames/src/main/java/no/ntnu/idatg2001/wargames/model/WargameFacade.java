package no.ntnu.idatg2001.wargames.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.armies.Army;
import no.ntnu.idatg2001.wargames.model.armies.ArmyFileHandler;
import no.ntnu.idatg2001.wargames.model.battles.Battle;
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
  private String currentTerrain;

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

  public void setUnits(List<Unit> units, String armyName) {
    if (armyName.equalsIgnoreCase(armyOne.getName())) {
      armyOne.setUnits(units);
    } else if (armyName.equalsIgnoreCase(armyTwo.getName())) {
      armyTwo.setUnits(units);
    }
  }

  /**
   * Method to register an observer to the battle instance.
   * @param unitObserver
   */
  public void registerObserver(UnitObserver unitObserver) {
    battle = new Battle(armyOne, armyTwo);
    battle.register(unitObserver);
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
  public List<Unit> makeUnits(List<String> unitValuesAsString) throws IllegalArgumentException {
    return UnitFactory.getInstance()
          .createMultipleUnits(
              unitValuesAsString.get(0),
              unitValuesAsString.get(1),
              Integer.parseInt(unitValuesAsString.get(2)),
              Integer.parseInt(unitValuesAsString.get(3)),
              Integer.parseInt(unitValuesAsString.get(4)),
              Integer.parseInt(unitValuesAsString.get(5)));

  }

  public void saveArmyOneToResources() throws IOException, IllegalArgumentException {
    ArmyFileHandler.writeArmyCsv(armyOne,"src/main/resources/savefiles/armyOneSaveFile.csv");
  }

  public void saveArmyTwoToResources() throws IOException, IllegalArgumentException {
    ArmyFileHandler.writeArmyCsv(armyTwo, "src/main/resources/savefiles/armyTwoSaveFile.csv");

  }

  public List<Unit> getArmyOneFromResources() throws IOException, IllegalArgumentException {
    Army army =  ArmyFileHandler.readCsv("src/main/resources/savefiles/armyOneSaveFile.csv");
    armyOne.setName(army.getName());
    return army.getUnits();
  }

  public List<Unit> getArmyTwoFromResources() throws IOException, IllegalArgumentException {
    Army army =  ArmyFileHandler.readCsv("src/main/resources/savefiles/armyTwoSaveFile.csv");
    armyTwo.setName(army.getName());
    return army.getUnits();
  }

  public List<Unit> getArmyOneFromDemoFile() throws IOException, IllegalArgumentException {
    Army army =  ArmyFileHandler.readCsv("src/main/resources/savefiles/armyOneDemoFile.csv");
    armyOne.setName(army.getName());
    return army.getUnits();
  }


  public List<Unit> getArmyTwoFromDemoFile() throws IOException, IllegalArgumentException {
    Army army =  ArmyFileHandler.readCsv("src/main/resources/savefiles/armyTwoDemoFile.csv");
    armyTwo.setName(army.getName());
    return army.getUnits();
  }

  public int geAmountOfUnits(String armyName) {
    if (armyName.equalsIgnoreCase(armyOne.getName())) {
      return armyOne.getAllUnits().size();
    } else if (armyName.equalsIgnoreCase(armyTwo.getName())) {
      return armyTwo.getAllUnits().size();
    } else {
      return 0;
    }
  }

  public int getArmyOneAmountInfantry(String armyName) {
    if (armyName.equalsIgnoreCase(armyOne.getName())) {
      return armyOne.getAllInfantryUnits().size();
    } else if (armyName.equalsIgnoreCase(armyTwo.getName())) {
      return armyTwo.getAllInfantryUnits().size();
    } else {
      return 0;
    }
  }

  public int getArmyOneAmountRanged(String armyName) {
    if (armyName.equalsIgnoreCase(armyOne.getName())) {
      return armyOne.getAllRangedUnits().size();
    } else if (armyName.equalsIgnoreCase(armyTwo.getName())) {
      return armyTwo.getAllRangedUnits().size();
    } else {
      return 0;
    }
  }

  public int getArmyOneAmountCavalry(String armyName) {
    if (armyName.equalsIgnoreCase(armyOne.getName())) {
      return armyOne.getAllCavalryUnits().size();
    } else if (armyName.equalsIgnoreCase(armyTwo.getName())) {
      return armyTwo.getAllCavalryUnits().size();
    } else {
      return 0;
    }
  }

  public int getArmyOneAmountCommander(String armyName) {
    if (armyName.equalsIgnoreCase(armyOne.getName())) {
      return armyOne.getAllCommanderUnits().size();
    } else if (armyName.equalsIgnoreCase(armyTwo.getName())) {
      return armyTwo.getAllCommanderUnits().size();
    } else {
      return 0;
    }
  }

  public void setCurrentTerrain(String terrain) {
    currentTerrain = terrain;
    battle.setTerrain(currentTerrain);
  }

  public String getCurrentTerrain() {
    return currentTerrain;
  }

  public boolean simulateStep() {
    return battle.simulateStep();
  }

  public void simulate() {
    battle.simulate(currentTerrain);
  }

  public void setBattle() {
    this.battle = new Battle(armyOne, armyTwo);
  }
}
