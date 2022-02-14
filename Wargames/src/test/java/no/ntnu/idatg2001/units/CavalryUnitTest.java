package no.ntnu.idatg2001.units;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
      assertEquals(20,c1.getAttack());
      assertEquals(12,c1.getArmor());
  }

    @Test
    void getAttackBonus() {
      //First attack.
    assertEquals(4,c1.getAttackBonus());

    //Second attack.
      c1.attack(c2);
      assertEquals(2,c1.getAttackBonus());

    }
}