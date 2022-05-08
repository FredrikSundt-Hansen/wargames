package no.ntnu.idatg2001.wargames.armies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import no.ntnu.idatg2001.wargames.model.armies.Army;
import no.ntnu.idatg2001.wargames.model.battles.Battle;
import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.units.RangedUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.model.armies.ArmyFileHandler;
import org.junit.jupiter.api.BeforeEach;
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
    String path = "src/main/resources/savefiles/armyOneSaveFile.csv";
    try {
      ArmyFileHandler.writeArmyCsv(humanArmy,path);
      Army army = ArmyFileHandler.readCsv(path);
      assertEquals(army, humanArmy);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void readAndWriteBattleCsv() {
    String pathArmyOne = "src/main/resources/savefiles/armyOneSaveFile.csv";
    String pathArmyTwo = "src/main/resources/savefiles/armyTwoSaveFile.csv";

    try {
      ArmyFileHandler.writeArmyCsv(humanArmy, pathArmyOne);
      ArmyFileHandler.writeArmyCsv(orcishHorde, pathArmyTwo);
      Battle battle =
          new Battle(ArmyFileHandler.readCsv(pathArmyOne), ArmyFileHandler.readCsv(pathArmyTwo));
      assertEquals(battle.simulate("hills").getName(),humanArmy.getName());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void readNewPath() {
    String path = "src/test/java/no/ntnu/idatg2001/wargames/newArmyTestFile.csv";

    try {
      ArmyFileHandler.writeArmyCsv(humanArmy, path);
      assertEquals(ArmyFileHandler.readCsv(path), humanArmy);
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
      ArmyFileHandler.readCsv("src/test/java/no/ntnu/idatg2001/wargames/armies/nullArmyFile.csv");
      fail();
    } catch (IOException | NullPointerException e) {
      assertTrue(true);
    }
  }

  @Test
  void readArmyWithoutName() {
    String path = "src/test/java/no/ntnu/idatg2001/wargames/armies/armyWithoutName.csv";
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      for (Unit unit : humanArmy.getUnits()) {
        writer.write(
            unit.getClass().getSimpleName() + "," + unit.getName() + "," + unit.getHealth() + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      ArmyFileHandler.readCsv(path);
      fail();
    } catch (IOException | NullPointerException e) {
      assertTrue(true);
    }
  }
}