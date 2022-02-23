package no.ntnu.idatg2001.battles;

import no.ntnu.idatg2001.armies.Army;
import no.ntnu.idatg2001.units.CavalryUnit;
import no.ntnu.idatg2001.units.CommanderUnit;
import no.ntnu.idatg2001.units.InfantryUnit;
import no.ntnu.idatg2001.units.RangedUnit;
import no.ntnu.idatg2001.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BattleTest {
    private Army humanArmy;
    private Army orcishHorde;

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

    for (int i = 0; i < 500; i++) {
      humanArmy.add(infantryHuman);
      orcishHorde.add(infantryOrc);
      if (i < 100) {
        humanArmy.add(cavalryHuman);
        orcishHorde.add(cavalryOrc);
      }
      if (i < 200) {
        humanArmy.add(rangedHuman);
        orcishHorde.add(rangedOrc);
      }
      if (i < 1) {
        humanArmy.add(commanderHuman);
        orcishHorde.add(commanderOrc);
        }
      }
    }

  @Test
  void simulate() {
    Battle battle = new Battle(humanArmy, orcishHorde);
    battle.simulate();
    System.out.println(battle);
    }
}
