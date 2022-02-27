package no.ntnu.idatg2001.armies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.units.CavalryUnit;
import no.ntnu.idatg2001.units.CommanderUnit;
import no.ntnu.idatg2001.units.InfantryUnit;
import no.ntnu.idatg2001.units.RangedUnit;
import no.ntnu.idatg2001.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArmyTest {
  private Army army1;
  private Army army2;
  private Army army3;
  private List<Unit> units;

  private InfantryUnit infantry1;
  private RangedUnit ranged1;
  private CavalryUnit cavalry1;
  private CommanderUnit commander1;

  @BeforeEach
  void setUp() {
    infantry1 = new InfantryUnit("Infantry", 100);
    ranged1 = new RangedUnit("Ranged unit", 100);
    cavalry1 = new CavalryUnit("Cavalry", 100);
    commander1 = new CommanderUnit("Commander", 100);

    InfantryUnit infantry2 = new InfantryUnit("Infantry", 100);
    InfantryUnit infantry3 = new InfantryUnit("Infantry", 100);

    units = new ArrayList<>();
    units.add(infantry1);
    units.add(ranged1);
    units.add(cavalry1);
    units.add(commander1);
    units.add(infantry2);
    units.add(infantry3);

    army1 = new Army("TestArmy");
    army2 = new Army("TestArmy2", units);
  }

  @Test
  void testAddUnit() {
    army1.addUnit(infantry1);
    assertEquals("Infantry",army1.getUnits().get(0).getName());
  }

  @Test
  void testAddAllUnits() {
    army1.addAllUnits(units);
    assertEquals(6,army1.getUnits().size());
  }

  @Test
  void testRemove() {
    army2.getUnits().remove(2);
    assertEquals(5,army2.getUnits().size());
    assertNotEquals("Cavalry",army2.getUnits().get(2).getName());
    assertEquals("Commander",army2.getUnits().get(2).getName());
  }

  @Test
  void testRemoveInvalidUnit() {
    InfantryUnit infantry4 = new InfantryUnit("Infantry", 100);
    try {
      army2.removeUnit(infantry4);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  void testHasUnits() {
    assertFalse(army1.hasUnits());
    assertTrue(army2.hasUnits());
  }

  @Test
  void testGetAllUnits() {
    List<Unit> unitsReturned = army2.getAllUnits();
    assertEquals(army2.getUnits().size(),unitsReturned.size());
  }

  @Test
  void testEquals() {
    army3 = new Army("TestArmy2",units);
    assertTrue(army2.equals(army3));
    assertFalse(army2.equals(army1));
  }

  @Test
  void testGetRandom() {
    Unit u1 = army2.getRandom();
    Unit u2 = army2.getRandom();
    Unit u3 = army2.getRandom();
    Unit u4 = army2.getRandom();
    Unit u5 = army2.getRandom();
    Unit u6 = army2.getRandom();

    assertFalse(u1.equals(u2) && u1.equals(u3) && u1.equals(u4) && u1.equals(u5) && u1.equals(u6));
  }

  @Test
  void testInvalidRandom() {}
}