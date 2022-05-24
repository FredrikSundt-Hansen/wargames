package no.ntnu.idatg2001.wargames.model.units;

import static org.junit.jupiter.api.Assertions.assertEquals;

import no.ntnu.idatg2001.wargames.model.units.RangedUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RangedUnitTest {
  private Unit r1;
  private Unit r2;

  @BeforeEach
  void setUp() {
    r1 = new RangedUnit("Test", 100);
    r2 = new RangedUnit("Test", 100);
  }

  @Test
  void getResistBonus() {
    assertEquals(6, r1.getResistBonus());

    r2.attack(r1);
    assertEquals(4, r1.getResistBonus());

    r2.attack(r1);
    assertEquals(2, r1.getResistBonus());
  }

  @Test
  void setTerrain() {
    int attackBonusHill = r1.getAttackBonus() + 1;
    int attackBonusForest = r1.getAttackBonus() - 1;

    r1.setTerrain("plains");
    assertEquals(r1.getAttackBonus(), r1.getAttackBonus());
    assertEquals(r1.getResistBonus(), r1.getResistBonus());

    r1.setTerrain("HILL");
    assertEquals(attackBonusHill, r1.getAttackBonus());
    assertEquals(r1.getResistBonus(), r1.getResistBonus());

    r1.setTerrain("Forest");
    assertEquals(attackBonusForest, r1.getAttackBonus());
    assertEquals(r1.getResistBonus(), r1.getResistBonus());



  }
}
