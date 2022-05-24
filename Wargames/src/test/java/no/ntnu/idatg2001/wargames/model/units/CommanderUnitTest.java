package no.ntnu.idatg2001.wargames.model.units;

import static org.junit.jupiter.api.Assertions.assertEquals;

import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.CommanderUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommanderUnitTest {
    private CavalryUnit cm1;
    private CavalryUnit cm2;

    @BeforeEach
    void setUp() {
    cm1 = new CommanderUnit("Test", 100);
    cm2 = new CommanderUnit("Test2", 100);
    }

  @Test
  void testConstructor() {
        assertEquals(25,cm1.getAttack());
        assertEquals(15,cm1.getArmor());
  }

  @Test
  void getAttackBonus() {
          //First attack.
          assertEquals(4,cm1.getAttackBonus());

          //Second attack.
          cm1.attack(cm2);
          assertEquals(2,cm1.getAttackBonus());

      }
  }

