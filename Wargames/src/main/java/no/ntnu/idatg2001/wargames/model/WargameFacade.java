package no.ntnu.idatg2001.wargames.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
 *
 * If Maven tests do not work, or the stage the main menu is white, delete target folder.
 */
public class WargameFacade {

  private static volatile WargameFacade instance;
  private Army armyOneBackup;
  private Army armyTwoBackup;
  private Army armyOne;
  private Army armyTwo;
  private Battle battle;
  private String currentTerrain;
  private String armyOneFileName;
  private String armyOneFilePath;
  private String armyTwoFileName;
  private String armyTwoFilePath;

  /**
   * Constructor creates an instance.
   * Notice this is a singleton, so this will only be initiated once.
   */
  private WargameFacade() {
    armyOne = new Army();
    armyTwo = new Army();
    armyOneBackup = new Army();
    armyTwoBackup = new Army();
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

  public String getArmyOneFileName() {
    return armyOneFileName;
  }

  public String getArmyOneFilePath() {
    return armyOneFilePath;
  }

  public String getArmyTwoFileName() {
    return armyTwoFileName;
  }

  public String getArmyTwoFilePath() {
    return armyTwoFilePath;
  }

  public void reset() {
    armyOne = new Army();
    armyTwo = new Army();
    armyOneBackup = new Army();
    armyTwoBackup = new Army();
    ArmyFileHandler.setHasSavedFileFalse();
  }

  public void updateBackupArmies() {
    armyOneBackup = new Army(armyOne);
    armyTwoBackup = new Army(armyTwo);
  }

  public void setArmiesToBackupArmies() {
    armyOne = new Army(armyOneBackup);
    armyTwo = new Army(armyTwoBackup);
    battle = new Battle(armyOne, armyTwo);
    battle.setFirstStepFalse();
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
        this.armyOneBackup.setName(name);
      } else if (!name.equals(armyOne.getName())) {
        this.armyTwo.setName(name);
        this.armyTwoBackup.setName(name);
      } else {
        throw new IllegalArgumentException("That name is already chosen. Pick a new one.");
      }
    } else {
      throw new IllegalArgumentException("Invalid name.");
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
    setUnits(unitsOne, true);
    setUnits(unitsTwo, false);
  }

  /**
   * Checks if the current army is of valid input, that is the armies has units and the names
   * are not the same.
   *
   * @throws IllegalArgumentException - If units of one of the armies are empty,
   * or the names of the armies are null og empty.
   * @return
   */
  public void checkValidArmies() throws NullPointerException, IllegalArgumentException {
    if (armyOne.getName() == null || armyTwo.getName() == null
        || armyOne.getName().isEmpty() || armyTwo.getName().isEmpty()) {
      throw new IllegalArgumentException("Both armies need to have a name.");
    } else if (!armyOne.hasUnits() || !armyTwo.hasUnits()) {
      throw new IllegalArgumentException("Both armies need to have units in them.");
    }
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

  /**
   * If write method has been called in {@ArmyFilehandler}.
   * (If one of the save filed has been written to).
   *
   * @return True if one save file has been written to, false otherwise.
   */
  public boolean isSaveFileFilled() {
    return ArmyFileHandler.hasSavedFile();
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
    String absolutePath =
        Objects.requireNonNull(WargameFacade.class.getResource(
                "/no/ntnu/idatg2001/wargames/savefiles/armyOneSaveFile.csv"))
            .toExternalForm();
    String path = absolutePath.split(":")[absolutePath.split(":").length - 1];

    ArmyFileHandler.writeArmyCsv(new Army(armyName, units), path);
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
    String absolutePath =
        Objects.requireNonNull(WargameFacade.class.getResource(
                "/no/ntnu/idatg2001/wargames/savefiles/armyTwoSaveFile.csv"))
            .toExternalForm();
    String path = absolutePath.split(":")[absolutePath.split(":").length - 1];


    ArmyFileHandler.writeArmyCsv(new Army(armyName, units), path);
  }

  /**f
   * Loads an army from file, for army one.
   * Gets a list of units from file in resources, armyOneSaveFile.csv.
   *
   * @return List of units read from that file.
   * @throws IOException - If the file does not exist or, or could not load properly.
   * @throws IllegalArgumentException- If army name is not correct,
   * or one of the units in the file is not correct.
   */
  public List<Unit> getArmyOneFromResources() throws IOException, IllegalArgumentException, NullPointerException {
    String path = "/no/ntnu/idatg2001/wargames/savefiles/armyOneSaveFile.csv";

    Army army =  ArmyFileHandler.readCsv(path);
    armyOne.setName(army.getName());
    armyOneFileName = ArmyFileHandler.getLastLoadedFileName();
    armyOneFilePath = ArmyFileHandler.getLastLoadedFilePath();
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
  public List<Unit> getArmyTwoFromResources() throws IOException, IllegalArgumentException, NullPointerException {
    String path = "/no/ntnu/idatg2001/wargames/savefiles/armyTwoSaveFile.csv";

    Army army =  ArmyFileHandler.readCsv(path);
    armyTwo.setName(army.getName());
    armyTwoFileName = ArmyFileHandler.getLastLoadedFileName();
    armyTwoFilePath = ArmyFileHandler.getLastLoadedFilePath();
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
    String path = "/no/ntnu/idatg2001/wargames/savefiles/armyOneDemoFile.csv";

    Army army =  ArmyFileHandler.readCsv(path);
    armyOne.setName(army.getName());
    armyOneFileName = ArmyFileHandler.getLastLoadedFileName();
    armyOneFilePath = ArmyFileHandler.getLastLoadedFilePath();
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
  public List<Unit> getArmyTwoFromDemoFile() throws IOException, IllegalArgumentException, NullPointerException {
    String path = "/no/ntnu/idatg2001/wargames/savefiles/armyTwoDemoFile.csv";

    Army army = ArmyFileHandler.readCsv(path);
    armyTwo.setName(army.getName());
    armyTwoFileName = ArmyFileHandler.getLastLoadedFileName();
    armyTwoFilePath = ArmyFileHandler.getLastLoadedFilePath();
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
  public List<Unit> getArmyFromFile(String path, boolean armyOne) throws IOException, IllegalArgumentException {
    Army army = ArmyFileHandler.readCsv(path);
    if (armyOne) {
      this.armyOne.setName(army.getName());
    } else {
      this.armyTwo.setName(army.getName());
    }
    return army.getUnits();
  }

}
