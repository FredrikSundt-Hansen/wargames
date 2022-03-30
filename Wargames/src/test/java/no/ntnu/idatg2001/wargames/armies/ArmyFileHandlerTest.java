package no.ntnu.idatg2001.wargames.armies;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;
import no.ntnu.idatg2001.wargames.battles.Battle;
import no.ntnu.idatg2001.wargames.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.units.RangedUnit;
import no.ntnu.idatg2001.wargames.units.Unit;
import org.junit.jupiter.api.AfterEach;
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

    Unit infantryOrc = new InfantryUnit("Grunt", 100);
    Unit cavalryOrc = new CavalryUnit("Raider", 100);
    Unit rangedOrc = new RangedUnit("Spearman", 100);
    Unit commanderOrc = new CommanderUnit("Gul´dan", 180);

    humanArmy = new Army("Human army");
    orcishHorde = new Army("Orcish horde");

    for (int i = 0; i < 50; i++) {
      humanArmy.addUnit(infantryHuman);
      //orcishHorde.addUnit(infantryOrc);
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
  void readAndWriteCsv() {
    String path = "src/main/resources/armyCsv/army.csv";
    ArmyFileHandler.writeCsv(humanArmy, Path.of(path));
    Army testArmy = ArmyFileHandler.readCsv(Path.of(path));

    assertEquals(testArmy.getName(),humanArmy.getName());
    assertEquals(testArmy.getUnits().size(),humanArmy.getUnits().size());
    //assertEquals(testArmy, humanArmy);
  }

  @Test
  void readAndWriteTwoArmies() {
    String pathHumanArmy = "src/main/resources/armyCsv/humanArmy.csv";
    String pathOrcishHorde = "src/main/resources/armyCsv/orcishHorde.csv";

    ArmyFileHandler.writeCsv(humanArmy,Path.of(pathHumanArmy));
    Army newHumanArmy = ArmyFileHandler.readCsv(Path.of(pathHumanArmy));

    ArmyFileHandler.writeCsv(orcishHorde,Path.of(pathOrcishHorde));
    Army newOrcishHorde = ArmyFileHandler.readCsv(Path.of(pathOrcishHorde));

    assertEquals(newHumanArmy.getName(),humanArmy.getName());
    assertEquals(newHumanArmy.getUnits().size(),humanArmy.getUnits().size());

    assertEquals(newOrcishHorde.getName(),orcishHorde.getName());
    assertEquals(newOrcishHorde.getUnits().size(),orcishHorde.getUnits().size());

    Battle battle = new Battle(newHumanArmy,newOrcishHorde);

    assertEquals(newHumanArmy.getName(), battle.simulate().getName());
  }
}