package no.ntnu.idatg2001.wargames.model.armies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import no.ntnu.idatg2001.wargames.model.battles.Battle;
import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.units.RangedUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArmyFileHandlerTest {
  Army humanArmy;
  Army orcishHorde;

  @BeforeEach
  void setUp() {
    Unit infantryHuman = new InfantryUnit("Footman", 100);
    Unit cavalryHuman = new CavalryUnit("Knight", 100);
    Unit rangedHuman = new RangedUnit("Archer", 100);
    Unit commanderHuman = new CommanderUnit("Mountain King", 180);

    Unit cavalryOrc = new CavalryUnit("Raider", 100);
    Unit rangedOrc = new RangedUnit("Spearman", 100);
    Unit commanderOrc = new CommanderUnit("Gul´dan", 180);

    humanArmy = new Army("Human army");
    orcishHorde = new Army("Orcish horde");

    for (int i = 0; i < 50; i++) {
      humanArmy.addUnit(infantryHuman);
      if (i < 10) {
        humanArmy.addUnit(cavalryHuman);
        orcishHorde.addUnit(cavalryOrc);
      }
      if (i < 20) {
        humanArmy.addUnit(rangedHuman);
        orcishHorde.addUnit(rangedOrc);
      }
      if (i < 1) {
        humanArmy.addUnit(commanderHuman);
        orcishHorde.addUnit(commanderOrc);
      }
    }
  }

  @Test
  void readAndWriteArmyCsv() {
    String path = "src/test/java/no/ntnu/idatg2001/wargames/model/armies/armyOneSaveFile.csv";
    try {
      ArmyFileHandler.writeArmyCsv(humanArmy,path);
      Army army = ArmyFileHandler.readCsvTestFiles(path);
      assertEquals(army, humanArmy);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("Writees and read two armies used in a battle.")
  void readAndWriteBattleCsv() {
    String pathArmyOne = "src/test/java/no/ntnu/idatg2001/wargames/model/armies/armyOneSaveFile.csv";
    String pathArmyTwo = "src/test/java/no/ntnu/idatg2001/wargames/model/armies/armyTwoSaveFile.csv";

    try {
      ArmyFileHandler.writeArmyCsv(humanArmy, pathArmyOne);
      ArmyFileHandler.writeArmyCsv(orcishHorde, pathArmyTwo);
      Battle battle =
          new Battle(ArmyFileHandler.readCsvTestFiles(pathArmyOne), ArmyFileHandler.readCsvTestFiles(pathArmyTwo));
      assertEquals(battle.simulate("hills").getName(),humanArmy.getName());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("Tires to read to a new path. Deletes the file afterwards.")
  void readNewPath() {
    String path = "src/test/java/no/ntnu/idatg2001/wargames/model/newArmyTestFile.csv";

    try {
      ArmyFileHandler.writeArmyCsv(humanArmy, path);
      assertEquals(ArmyFileHandler.readCsvTestFiles(path), humanArmy);
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Files.delete(Path.of(path));
    } catch (IOException ignored) {

    }
  }

  @Test
  void readNullArmy() {
    try {
      ArmyFileHandler.readCsvTestFiles("src/test/java/no/ntnu/idatg2001/model/wargames/armies/nullArmyFile.csv");
      fail();
    } catch (IOException | IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  void readArmyWithoutName() {
    String path = "src/test/java/no/ntnu/idatg2001/wargames/model/armies/armyWithoutName.csv";
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      for (Unit unit : humanArmy.getUnits()) {
        writer.write(
            unit.getClass().getSimpleName() + "," + unit.getName() + "," + unit.getHealth() + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      ArmyFileHandler.readCsvTestFiles(path);
      fail();
    } catch (IOException | IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  void readFromNonExcisitngFile() {
    try {
      ArmyFileHandler.readCsvTestFiles("newNonExcisitngFile.csv");
      fail();
    } catch (IOException | IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  @DisplayName("File already contains one army, with one line not containing a the right size.")
  void readArmyWithInvalidUnit() {
    try {
      ArmyFileHandler.readCsvTestFiles("src/test/java/no/ntnu/idatg2001/wargames/model/armies/armyWithInvalidUnit.csv");
      fail();
    } catch (IOException | IllegalArgumentException e) {
      assertTrue(true);
    }


  }

  @Test
  void writeNullArmy() {
    try {
      ArmyFileHandler.writeArmyCsv(null, "src/main/resources/savefiles/armyOneSaveFile.csv");
      fail();
    } catch (IOException | NullPointerException | IllegalArgumentException exception) {
      assertTrue(true);
    }

    try {
      ArmyFileHandler.writeArmyCsv(new Army(), "src/main/resources/savefiles/armyOneSaveFile.csv");
      fail();
    } catch (IOException | IllegalArgumentException | NullPointerException exception) {
      assertTrue(true);
    }
  }
}