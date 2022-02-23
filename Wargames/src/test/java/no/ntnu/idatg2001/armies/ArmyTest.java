package no.ntnu.idatg2001.armies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    InfantryUnit infantry4= new InfantryUnit("Infantry", 100);
    InfantryUnit infantry5 = new InfantryUnit("Infantry", 100);

    units = new ArrayList<>();
    units.add(infantry1);
    units.add(ranged1);
    units.add(cavalry1);
    units.add(commander1);
    units.add(infantry2);
    units.add(infantry3);
    units.add(infantry4);
    units.add(infantry5);

    army1 = new Army("TestArmy");
    army2 = new Army("TestArmy2", units);
  }

  @Test
  void add() {
    army1.add(infantry1);
    assertEquals("Infantry",army1.getUnits().get(0).getName());
  }

  @Test
  void addAll() {
    army1.addAll(units);
    assertEquals(4,army1.getUnits().size());
  }

  @Test
  void remove() {
    army2.getUnits().remove(2);
    assertEquals(3,army2.getUnits().size());
    assertNotEquals("Cavalry",army2.getUnits().get(2).getName());
    assertEquals("Commander",army2.getUnits().get(2).getName());
  }

  @Test
  void hasUnits() {
    assertFalse(army1.hasUnits());
    assertTrue(army2.hasUnits());
  }

  @Test
  void getAllUnits() {
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
    Unit u1 = army2.getRand();
    Unit u2 = army2.getRand();
    Unit u3 = army2.getRand();
    Unit u4 = army2.getRand();
    Unit u5 = army2.getRand();
    Unit u6 = army2.getRand();

    assertFalse(u1.equals(u2) && u1.equals(u3) && u1.equals(u4) && u1.equals(u5) && u1.equals(u6));

  }
}