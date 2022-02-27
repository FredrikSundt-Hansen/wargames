package no.ntnu.idatg2001.battles;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.armies.Army;
import no.ntnu.idatg2001.units.CavalryUnit;
import no.ntnu.idatg2001.units.CommanderUnit;
import no.ntnu.idatg2001.units.InfantryUnit;
import no.ntnu.idatg2001.units.RangedUnit;
import no.ntnu.idatg2001.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BattleTest {
    private Army humanArmy;
    private Army orcishHorde;

  @DisplayName("Makes a human army and an orcish army before every test.")
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
      humanArmy.addUnit(infantryHuman);
      orcishHorde.addUnit(infantryOrc);
      if (i < 100) {
        humanArmy.addUnit(cavalryHuman);
        orcishHorde.addUnit(cavalryOrc);
      }
      if (i < 200) {
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
  @DisplayName("Shows the distribution of wins over a many battles. " +
      "If it is about equally many wins, then the random generator " +
      "is working correctly ( 50/50 chance for an army to win).")
  void simulate() {
      List<Army> battles = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      Army army = new Battle(humanArmy,orcishHorde).simulate();
      battles.add(army);
    }

    long a = battles.stream().filter(army -> army.getName().equals("Human army")).count();
    long b = battles.stream().filter(army -> army.getName().equals("Orcish horde")).count();

    System.out.println("Human wins : " + a + "\n" +
                       "Orcish wins: " + b + "\n" +
                       "Ratio      : " + (float) a/b );
  }

  @Test
  void simulate2() {
    Army a1 = new Battle(humanArmy,orcishHorde).simulate();
    Army a2 = new Battle(humanArmy,orcishHorde).simulate();
    Army a3 = new Battle(humanArmy,orcishHorde).simulate();
    Army a4 = new Battle(humanArmy,orcishHorde).simulate();
    Army a5 = new Battle(humanArmy,orcishHorde).simulate();

    assertFalse(a1.equals(a2) && a1.equals(a3) && a1.equals(a4) && a1.equals(a5));
  }

}
