package no.ntnu.idatg2001.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnitTest {
    private Unit u1;
    private Unit u2;

  @BeforeEach
  void setUp() {
      u1 = new InfantryUnit("Test", 100, 10, 10);
      u2 = new InfantryUnit("Test", 100);
  }

  @Test
  void testConstructor1() {
    assertEquals("Test",u1.getName());
    assertEquals(100,u1.getHealth());
    assertEquals(10,u1.getAttack());
    assertEquals(10,u1.getArmor());
  }


  @Test
  void testConstructor2() {
      assertEquals("Test",u2.getName());
      assertEquals(100,u2.getHealth());
  }

  @Test
  void testInvalidName() {
    try {
      Unit u3 = new InfantryUnit("", 100);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  void testInvalidAttack() {
    try {
      Unit u5 = new InfantryUnit("Test", 100, -10, 10);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  void testInvalidArmor() {
    try {
      Unit u6 = new InfantryUnit("Test", 100, 10, -10);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }



  @Test
  void attack() {
      //No attacks, or defends
      assertEquals(0,u1.numberOfDefends);
      assertEquals(0,u2.numberOfAttacks);

      //First attack, first defend
      u1.attack(u2);
      assertNotEquals(100,u2.getHealth());
      assertEquals(1,u2.numberOfDefends);
      assertEquals(1,u1.numberOfAttacks);

      //Second attack, second defend. One attack does one damage.
      u1.attack(u2);
      assertEquals(98,u2.getHealth());
      assertEquals(2,u2.numberOfDefends);
      assertEquals(2,u1.numberOfAttacks);

      //Third attack, third defend
      u1.attack(u2);
      assertEquals(97,u2.getHealth());
      assertEquals(3,u2.numberOfDefends);
      assertEquals(3,u1.numberOfAttacks);


  }

}