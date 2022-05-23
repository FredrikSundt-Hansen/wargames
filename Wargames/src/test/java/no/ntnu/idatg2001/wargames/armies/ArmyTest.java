package no.ntnu.idatg2001.wargames.armies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.armies.Army;
import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.units.RangedUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArmyTest {
  private Army army1;
  private Army army2;
  private List<Unit> units;


  @BeforeEach
  void setUp() {
    Unit infantry1 = new InfantryUnit("Infantry", 100);
    Unit ranged1 = new RangedUnit("Ranged unit", 100);
    Unit cavalry1 = new CavalryUnit("Cavalry", 100);
    Unit commander1 = new CommanderUnit("Commander", 100);

    Unit infantry2 = new InfantryUnit("Infantry", 100);
    Unit infantry3 = new InfantryUnit("Infantry", 100);

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
    army1.addUnit(new InfantryUnit("Infantry", 100));
    assertEquals("Infantry",army1.getUnits().get(0).getName());
  }

  @Test
  void testAddInvalidUnit() {
    try {
      army1.addUnit(null);
      fail();
    } catch (NullPointerException e) {
      assertTrue(true);
    }

  }

  @Test
  void testAddAllUnits() {
    army1.addAllUnits(units);
    army2.addAllUnits(army1.getAllUnits());
    assertEquals(12,army2.getUnits().size());
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
    InfantryUnit infantry4 = new InfantryUnit("Infantry4", 100);
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
    Army army3 = new Army("TestArmy2",units);
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
  void testInvalidRandom() {
    try {
      //empty army
      army1.getRandom();
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  void getAllCavalryUnits(){
    List<Unit> testCavalryUnits = new ArrayList<>();
    Unit cavalry2 = new CavalryUnit("Cavalry", 100);
    Unit cavalry3 = new CavalryUnit("Cavalry", 100);
    Unit cavalry4 = new CavalryUnit("Cavalry", 100);
    Unit cavalry5 = new CavalryUnit("Cavalry", 100);

    testCavalryUnits.add(cavalry2);
    testCavalryUnits.add(cavalry3);
    testCavalryUnits.add(cavalry4);
    testCavalryUnits.add(cavalry5);

    army1.addAllUnits(testCavalryUnits);
    assertEquals(testCavalryUnits.size(),army1.getAllCavalryUnits().size());
  }

  @Test
  void getALLCommanderUnits(){
    List<Unit> testUnits = new ArrayList<>();
    Unit commander2 = new CommanderUnit("Commander", 100);
    Unit commander3 = new CommanderUnit("Commander", 100);
    Unit commander4 = new CommanderUnit("Commander", 100);
    Unit commander5 = new CommanderUnit("Commander", 100);

    testUnits.add(commander2);
    testUnits.add(commander3);
    testUnits.add(commander4);
    testUnits.add(commander5);

    army1.addAllUnits(testUnits);
    assertEquals(testUnits.size(),army1.getAllCommanderUnits().size());
  }

  @Test
  void getAllRangedUnits(){
    List<Unit> testUnits = new ArrayList<>();
    Unit ranged2 = new RangedUnit("Ranged unit", 100);
    Unit ranged3 = new RangedUnit("Ranged unit", 100);
    Unit ranged4 = new RangedUnit("Ranged unit", 100);
    Unit ranged5 = new RangedUnit("Ranged unit", 100);

    testUnits.add(ranged2);
    testUnits.add(ranged3);
    testUnits.add(ranged4);
    testUnits.add(ranged5);

    army1.addAllUnits(testUnits);
    assertEquals(testUnits.size(),army1.getAllRangedUnits().size());
  }

  @Test
  void getAllInfantryUnits(){
    List<Unit> testCavalryUnits = new ArrayList<>();
    Unit infantry4 = new InfantryUnit("Infantry", 100);
    Unit infantry5 = new InfantryUnit("Infantry", 100);
    Unit infantry6 = new InfantryUnit("Infantry", 100);
    Unit infantry7 = new InfantryUnit("Infantry", 100);

    testCavalryUnits.add(infantry4);
    testCavalryUnits.add(infantry5);
    testCavalryUnits.add(infantry6);
    testCavalryUnits.add(infantry7);

    army1.addAllUnits(testCavalryUnits);
    assertEquals(testCavalryUnits.size(),army1.getAllInfantryUnits().size());
  }

  @Test
  void setTerrainToUnits() {
    army1.setTerrainToUnits("plains");
    for (Unit unit : army1.getAllCavalryUnits()) {
      assertEquals(unit.getAttackBonus() + 1, unit.getAttackBonus());
      assertEquals(unit.getAttackBonus(), unit.getAttackBonus());
    }

    army1.setTerrainToUnits("hill");
    for (Unit unit : army1.getAllRangedUnits()) {
      assertEquals(unit.getAttackBonus() + 1, unit.getAttackBonus());
      assertEquals(unit.getAttackBonus() - 1, unit.getAttackBonus());
    }

    army1.setTerrainToUnits("forest");
    for (Unit unit : army1.getAllInfantryUnits()) {
      assertEquals(unit.getAttackBonus() + 2, unit.getAttackBonus());
      assertEquals(unit.getAttackBonus() + 2, unit.getAttackBonus());
    }

    army1.setTerrainToUnits("test");
    for (Unit unit : army1.getAllUnits()) {
      assertEquals(unit.getAttackBonus(), unit.getAttackBonus());
      assertEquals(unit.getAttackBonus(), unit.getAttackBonus());
    }
  }
}