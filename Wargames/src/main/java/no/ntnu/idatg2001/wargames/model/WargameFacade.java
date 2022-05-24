package no.ntnu.idatg2001.wargames.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.armies.Army;
import no.ntnu.idatg2001.wargames.model.armies.ArmyFileHandler;
import no.ntnu.idatg2001.wargames.model.battles.Battle;
import no.ntnu.idatg2001.wargames.model.battles.UnitObserver;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.model.units.UnitFactory;

/**
 * Facade for the application, as a singleton. Holds all the model classes that are being used and
 * binds the controller classes with the model classes to reduce coupling between classes and
 * preventing communication between controller classes.
 *
 * <p>Some methods in BattleMaker and this class uses isArmyOne to distinguish * between the
 * different armies. If it is true, then it will set the value to army one, * if it is false it will
 * set the value to armyTow.
 */
public class WargameFacade {

  private static volatile WargameFacade instance;
  private Army armyOne;
  private Army armyTwo;
  private Battle battle;
  private String currentTerrain;

  private Army armyOneDuplicate;
  private Army armyTwoDuplicate;

  private boolean firstSimulationRun;

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
    return armyOne.getUnits();
  }

  public List<Unit> getArmyTwoUnits() {
    return armyTwo.getUnits();
  }

  public String getArmyOneName() {
    return armyOne.getName();
  }

  public String getArmyTwoName() {
    return armyTwo.getName();
  }

  /**
   * Seth the name of one of the army. If isArmy is true, the name will be set to army one,
   * if it is false it will be set to army two. This type of implementation, isArmyOne is used in
   * BattleMakerController, and in this class to support that. Other
   *
   * @param name The new name of the army.
   * @param isArmyOne Change the name of army one if true, false if armyTwo.
   * @throws IllegalArgumentException - If the name is null or empty, or one of the
   * other armies already has that name.
   */
  public void setArmyName(String name, boolean isArmyOne) throws IllegalArgumentException {
    if (name != null && !name.isEmpty()) {
      if (isArmyOne && !name.equals(armyTwo.getName())) {
        this.armyOne.setName(name);
      } else if (!name.equals(armyOne.getName())) {
        this.armyTwo.setName(name);
      } else {
        throw new IllegalArgumentException("That name is already chosen. Pick a new one.");
      }
    } else {
      throw new IllegalArgumentException("Invalid name.");
    }
  }

  /**
   * Checks if the current army is of valid input, that is the armies has units and the names
   * are not the same.
   *
   * @throws IllegalArgumentException - If units of one of the armies are empty,
   * or the names of the armies are null og empty.
   */
  public void checkValidArmies() throws NullPointerException, IllegalArgumentException {
    if (!armyOne.hasUnits() || !armyTwo.hasUnits()) {
       throw new IllegalArgumentException("Both armies need to have units in them.");
    } else if (armyOne.getName() == null || armyTwo.getName() == null
        || armyOne.getName().isEmpty() || armyTwo.getName().isEmpty()) {
      throw new IllegalArgumentException("Both armies need to have a name.");
    }
  }

  /**
   * The new units to set to either army one, or army two.
   * @param units The list of units to replace with.
   * @param isArmyOne Boolean, if the new units are to army one or two.
   */
  public void setUnits(List<Unit> units, boolean isArmyOne) {
    if (isArmyOne) {
      armyOne.setUnits(units);
    } else {
      armyTwo.setUnits(units);
    }
  }

  /**
   * Method to set the units of both armies at once, uses setUnits and checksValidArmies.
   *
   * @param unitsOne The list of units to army one.
   * @param unitsTwo The list of units to army two.
   * @throws IllegalArgumentException - If units of one of the armies are empty.
   */
  public void setUnitsNameArmyOneAndTwo(List<Unit> unitsOne, List<Unit> unitsTwo )
      throws NullPointerException, IllegalArgumentException {
    checkValidArmies();
    setUnits(unitsOne, true);
    setUnits(unitsTwo, false);
  }

  /**
   * Method to get a list of integer containing all information about the
   * amount of units left for each type. Uses getUnitValues.
   *
   * @param isArmyOne True if armyOne, false otherwise.
   * @return The list of integers of the army unit information.
   */
  public List<Integer> getArmyUnitValues(boolean isArmyOne)  {
    if (isArmyOne) {
      return getUnitValues(armyOne);
    } else {
      return getUnitValues(armyTwo);
    }
  }

  /**
   * Returns a list containing the amount of units for each type,
   * total, infantry, ranged, cavalry and commander. In that order.
   * If this method is being used,
   * it is crucial to get the correct index to access the right information.
   *
   * @param army The army to get the info from.
   * @return List of integers containing the unit info from that army.
   */
  private List<Integer> getUnitValues(Army army) {
    List<Integer> values = new ArrayList<>();
    values.add(army.getUnits().size());
    values.add(army.getAllInfantryUnits().size());
    values.add(army.getAllRangedUnits().size());
    values.add(army.getAllCavalryUnits().size());
    values.add(army.getAllCommanderUnits().size());
    return values;
  }

  /**
   * Method to register an observer to the battle instance.
   * @param unitObserver Used with the observable pattern,
   *                     to register an observer to BattleUpdater through this Battle instance.
   */
  public void registerObserver(UnitObserver unitObserver) {
    battle.register(unitObserver);
  }

  /**
   * Method to make several units using UnitFactory.
   * @return List of all units with these characteristics.
   */
  public List<Unit> makeUnits(List<String> unitValuesAsString) throws IllegalArgumentException {
    return UnitFactory.getInstance().createMultipleUnits(
        unitValuesAsString.get(0), unitValuesAsString.get(1),
        Integer.parseInt(unitValuesAsString.get(2)),
        Integer.parseInt(unitValuesAsString.get(3)),
        Integer.parseInt(unitValuesAsString.get(4)),
        Integer.parseInt(unitValuesAsString.get(5)));
  }

  public String getCurrentTerrain() {
    return currentTerrain;
  }

  /**
   * Sets up the battle, initialises the current battle,
   * and sets up the terrain with the current terrain.
   * @param terrain The terrain to set the battle to.
   */
  public void setBattle(String terrain) {
    this.battle = new Battle(armyOne, armyTwo);
    currentTerrain = terrain;
    battle.setTerrain(currentTerrain);
  }

  /**
   * Runs battle simulation step, to do one step of the simulation at the time, able to
   * synchronise data flow in this way.
   *
   * @return True if this simulation is finish, false if it is not and still running.
   */
  public boolean simulateStep() throws IllegalArgumentException {
    return battle.simulateStep();
  }



  public void duplicateArmies() throws IllegalArgumentException {
    armyOneDuplicate = new Army(armyOne.getName());
    armyTwoDuplicate = new Army(armyTwo.getName());


      for (Unit unit : armyOne.getUnits()) {
        armyOneDuplicate.addUnit(UnitFactory.getInstance().createUnit(
                    unit.getType(),
                    unit.getName(),
                    unit.getHealth(),
                    unit.getAttack(),
                    unit.getArmor()));
      }

      for (Unit unit : armyTwo.getUnits()) {
        armyTwoDuplicate.addUnit(UnitFactory.getInstance().createUnit(
                    unit.getType(),
                    unit.getName(),
                    unit.getHealth(),
                    unit.getAttack(),
                    unit.getArmor()));
      }
  }

  public List<Unit> getArmyOneDuplicate() {
    return armyOneDuplicate.getUnits();
  }

  public List<Unit> getArmyTwoDuplicate() {
    return armyTwoDuplicate.getUnits();
  }

  public void setBattleDuplicatedArmies() {
    this.battle = new Battle(armyOneDuplicate, armyTwoDuplicate);
    battle.setTerrain(currentTerrain);
  }

  public List<Long> simulateMultipleTimes(int n) throws IllegalArgumentException {
    List<Long> distributionOfWinners = new ArrayList<>();
    List<Army> battles = new ArrayList<>();
    duplicateArmies();
    for (int i = 0; i < n; i++) {
      Army army = new Battle(new Army(armyOne.getName(), armyOne.getUnits()),new Army(armyTwo.getName(), armyTwo.getUnits()))
          .simulate(currentTerrain);
      battles.add(army);
    }
    long a = battles.stream().filter(army -> army.getName().equals(armyOne.getName())).count();
    long b = battles.stream().filter(army -> army.getName().equals(armyTwo.getName())).count();
    distributionOfWinners.add(a);
    distributionOfWinners.add(b);
    return distributionOfWinners;
  }

  /**
   * Saves army one to file.
   * Saves the army to csv file in resources, armyOneSaveFile.csv.
   *
   * @param armyName The name of the army.
   * @param units The units of the army.
   * @throws IOException - If file could not be found, or had problems saving.
   * @throws IllegalArgumentException - If army name is null or empty.
   */
  public void saveArmyOneToResources(String armyName, List<Unit> units) throws IOException, IllegalArgumentException {
    ArmyFileHandler.writeArmyCsv(
        new Army(armyName, units), "src/main/resources/savefiles/armyOneSaveFile.csv");
  }

  /**
   * Saves army two to file.
   * Saves the army to csv file in resources, armyTwoSaveFile.csv.
   *
   * @param armyName The name of the army.
   * @param units The units of the army.
   * @throws IOException - If file could not be found, or had problems saving.
   * @throws IllegalArgumentException - If army name is null or empty.
   */
  public void saveArmyTwoToResources(String armyName, List<Unit> units) throws IOException, IllegalArgumentException {
      ArmyFileHandler.writeArmyCsv(
          new Army(armyName, units), "src/main/resources/savefiles/armyTwoSaveFile.csv");
  }

  /**
   * Loads an army from file, for army one.
   * Gets a list of units from file in resources, armyOneSaveFile.csv.
   *
   * @return List of units read from that file.
   * @throws IOException - If the file does not exist or, or could not load properly.
   * @throws IllegalArgumentException- If army name is not correct,
   * or one of the units in the file is not correct.
   */
  public List<Unit> getArmyOneFromResources() throws IOException, IllegalArgumentException {
    Army army =  ArmyFileHandler.readCsv("src/main/resources/savefiles/armyOneSaveFile.csv");
    armyOne.setName(army.getName());
    return army.getUnits();
  }

  /**
   * Loads an army from file, for army two.
   * Gets a list of units from file in resources, armyTwoSaveFile.csv.
   *
   * @return List of units read from that file.
   * @throws IOException - If the file does not exist or, or could not load properly.
   * @throws IllegalArgumentException- If army name is not correct,
   * or one of the units in the file is not correct.
   */
  public List<Unit> getArmyTwoFromResources() throws IOException, IllegalArgumentException {
    Army army =  ArmyFileHandler.readCsv("src/main/resources/savefiles/armyTwoSaveFile.csv");
    armyTwo.setName(army.getName());
    return army.getUnits();
  }

  /**
   * This is a demo file for army one, consists of an army that has already been read to but cannot be changed.
   * This used so the user can get a peek on how an army could look like.
   *
   * Gets a list of units from file in resources, armyTwoDemoFile.csv.
   *
   * @return List of units read from that file.
   * @throws IOException - If the file does not exist or, or could not load properly.
   * @throws IllegalArgumentException- If army name is not correct,
   * or one of the units in the file is not correct.
   */
  public List<Unit> getArmyOneFromDemoFile() throws IOException, IllegalArgumentException {
    Army army =  ArmyFileHandler.readCsv("src/main/resources/savefiles/armyOneDemoFile.csv");
    armyOne.setName(army.getName());
    return army.getUnits();
  }

  /**
   * This is a demo file for army two, consists of an army that has already been read to but cannot be changed.
   * This used so the user can get a peek on how an army could look like.
   *
   * Gets a list of units from file in resources, armyTwoDemoFile.csv.
   *
   * @return List of units read from that file.
   * @throws IOException - If the file does not exist or, or could not load properly.
   * @throws IllegalArgumentException- If army name is not correct,
   * or one of the units in the file is not correct.
   */
  public List<Unit> getArmyTwoFromDemoFile() throws IOException, IllegalArgumentException {
    Army army =  ArmyFileHandler.readCsv("src/main/resources/savefiles/armyTwoDemoFile.csv");
    armyTwo.setName(army.getName());
    return army.getUnits();
  }

  /**
   * Method to load an army from a custom path.
   * Used with a type of file-chooser to get a file consisting of one valid army,
   * and returns the list units found in that file.
   * Sets the name of the army found to armyOne.
   *
   * @param path The path to read from.
   * @return The list of units in demo file.
   * @throws IOException - If the file could not be read from, this not exist or is not supported.
   * @throws IllegalArgumentException -If the army in this file did not have a valid name,
   * or any of the units were wrong.
   */
  public List<Unit> getArmyFromFile(String path) throws IOException, IllegalArgumentException {
    Army army = ArmyFileHandler.readCsv(path);
    this.armyOne.setName(army.getName());
    return army.getUnits();
  }

  /**
   * Returns a list of String containing the info from the last loaded file from FileHandler.
   * List consists of army name, file path and file name of the army that was loaded.
   *
   * @return List of string with the last loaded file info.
   */
  public List<String> getLastLoadedFileInfo() {
    List<String> fileInfo = new ArrayList<>();
    fileInfo.add(ArmyFileHandler.getLastLoadedFileArmyName());
    fileInfo.add(ArmyFileHandler.getLastLoadedFilePath());
    fileInfo.add(ArmyFileHandler.getLastLoadedFileName());
    return fileInfo;
  }

}
