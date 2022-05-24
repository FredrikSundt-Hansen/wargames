package no.ntnu.idatg2001.wargames.model.units;

import static org.junit.jupiter.api.Assertions.assertEquals;

import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CavalryUnitTest {
  private Unit c1;
  private Unit c2;

  @BeforeEach
  void setUp() {
    c1 = new CavalryUnit("Test", 100);
    c2 = new CavalryUnit("Test2", 100);
  }

  @Test
  void testConstructor() {
    assertEquals(20, c1.getAttack());
    assertEquals(12, c1.getArmor());
  }

  @Test
  void getAttackBonus() {
    // First attack.
    assertEquals(4, c1.getAttackBonus());

    // Second attack.
    c1.attack(c2);
    assertEquals(2, c1.getAttackBonus());
  }

  @Test
  void setTerrain() {
    int attackBonusPlains = c1.getAttackBonus() + 1;

    c1.setTerrain("plains");
    assertEquals(attackBonusPlains, c1.getAttackBonus());
    assertEquals(c1.getResistBonus(), c1.getResistBonus());

    c1.setTerrain("HILL");
    assertEquals(attackBonusPlains, c1.getAttackBonus());
    assertEquals(c1.getResistBonus(), c1.getResistBonus());

    c1.setTerrain("Forest");
    assertEquals(c1.getAttackBonus(), c1.getAttackBonus());
    assertEquals(c1.getResistBonus(), c1.getResistBonus());



  }
}
