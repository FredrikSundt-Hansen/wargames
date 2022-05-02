package no.ntnu.idatg2001.wargames.units;

import static org.junit.jupiter.api.Assertions.assertEquals;

import no.ntnu.idatg2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InfantryUnitTest {
    private Unit u1;
    private Unit u2;

    @BeforeEach
    void setUp() {
        u1 = new InfantryUnit("Test", 100, 10, 10);
        u2 = new InfantryUnit("Test", 100);
    }

  @Test
  void testConstructor() {
        assertEquals(10,u2.getArmor());
        assertEquals(15,u2.getAttack());
  }

  @Test
  void getAttackBonus() {
        assertEquals(2,u1.getAttackBonus());
  }


    @Test
    void getResistBonus() {
        assertEquals(1,u1.getResistBonus());
    }
}