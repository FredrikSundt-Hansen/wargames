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
   * @return List of all units with these characteristics.
   */
  public List<Unit> makeUnits(List<String> unitValuesAsString) throws IllegalArgumentException {
    return UnitFactory.getInstance().createMultipleUnits(
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

  public List<Unit> getArmyOneFromFile(String path) throws IOException {
    Army army = ArmyFileHandler.readCsv(path);
    this.armyOne.setName(army.getName());

    return army.getUnits();
  }

  public List<Unit> getArmyTwoFromFile(String path) throws IOException {
    Army army = ArmyFileHandler.readCsv(path);
    this.armyOne.setName(army.getName());
    return army.getUnits();
  }

  public List<String> getLastLoadedFileInfo() {
    List<String> fileInfo = new ArrayList<>();
    fileInfo.add(ArmyFileHandler.getLastLoadedFileArmyName());
    fileInfo.add(ArmyFileHandler.getLastLoadedFilePath());
    fileInfo.add(ArmyFileHandler.getLastLoadedFileName());
    return fileInfo;
  }

  public List<Integer> getArmyOneAmountsUnitTypes() {
    return getUnitValues(armyOne);
  }

  public List<Integer> getArmyTwoAmountsUnitTypes() {
    return getUnitValues(armyTwo);
  }

  private List<Integer> getUnitValues(Army army) {
    List<Integer> values = new ArrayList<>();
    values.add(army.getUnits().size());
    values.add(army.getAllInfantryUnits().size());
    values.add(army.getAllRangedUnits().size());
    values.add(army.getAllCavalryUnits().size());
    values.add(army.getAllCommanderUnits().size());

    return values;
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

  public List<Long> simulateMultipleTimes(int n) {
    List<Long> distributionOfWinners = new ArrayList<>();
    List<Army> battles = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      Army army = new Battle(armyOne,armyTwo).simulate(currentTerrain);
      battles.add(army);
    }
    long a = battles.stream().filter(army -> army.getName().equals(armyOne.getName())).count();
    long b = battles.stream().filter(army -> army.getName().equals(armyTwo.getName())).count();
    distributionOfWinners.add(a);
    distributionOfWinners.add(b);
    return distributionOfWinners;
  }



  public void setBattle() {
    this.battle = new Battle(armyOne, armyTwo);
  }

  public Battle getBattle() {
    return battle;
  }
}
