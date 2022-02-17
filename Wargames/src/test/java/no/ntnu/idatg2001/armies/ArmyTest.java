package no.ntnu.idatg2001.armies;

import no.ntnu.idatg2001.units.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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

    this.units = new ArrayList<>();
    this.units.add(infantry1);
    this.units.add(ranged1);
    this.units.add(cavalry1);
    this.units.add(commander1);

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

}