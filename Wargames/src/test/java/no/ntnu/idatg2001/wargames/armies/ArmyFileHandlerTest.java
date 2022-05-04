package no.ntnu.idatg2001.wargames.armies;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;
import no.ntnu.idatg2001.wargames.model.armies.Army;
import no.ntnu.idatg2001.wargames.model.battles.Battle;
import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.units.RangedUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.model.utility.ArmyFileHandler;
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
      orcishHorde.addUnit(infantryOrc);
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
    System.out.println(humanArmy.getUnits().size());
    System.out.println(humanArmy.getUnits()
                                .stream().map(Unit::getName)
                                .collect(Collectors.toSet()));
  }

  @Test
  void writeArmyCsv() {

  }

  @Test
  void writeBattleCsv() {
    String path = "src/main/resources/savefiles/battleSaveFile.csv";
    Battle battle = new Battle(humanArmy, orcishHorde);
    try {
      ArmyFileHandler.writeBattle(battle, path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}