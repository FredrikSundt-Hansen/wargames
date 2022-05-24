package no.ntnu.idatg2001.wargames.units;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.units.RangedUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.model.units.UnitFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnitFactoryTest {

  @BeforeEach
  void setUp() {
  }

  @Test
  void createUnit() {
    String unitName = "the new unit";
    int unitHealth = 200;
    int unitAttack = 5;
    int unitArmor = 30;
    Unit unit = UnitFactory.getInstance().createUnit("InfantryUnit", unitName, unitHealth, unitAttack, unitArmor);
    assertEquals(unit.getName(), unitName);
    assertEquals(unit.getHealth(), unitHealth);
    assertEquals(unit.getAttack(), unitAttack);
    assertEquals(unit.getArmor(), unitArmor);

    assertTrue(unit instanceof InfantryUnit);

    Unit unit2 = UnitFactory.getInstance().createUnit("CavalryUnit", unitName, unitHealth, unitAttack, unitArmor);
    assertTrue(unit2 instanceof CavalryUnit);

    Unit unit3 = UnitFactory.getInstance().createUnit("RangedUnit", unitName, unitHealth, unitAttack, unitArmor);
    assertTrue(unit3 instanceof RangedUnit);

    Unit unit4 = UnitFactory.getInstance().createUnit("CommanderUnit", unitName, unitHealth, unitAttack, unitArmor);
    assertTrue(unit4 instanceof CommanderUnit);

  }

  @Test
  void createMultipleUnits() {
    String unitName = "the new unit";
    int unitHealth = 200;
    int unitAttack = 5;
    int unitArmor = 30;
    List<Unit>
        unitList = UnitFactory.getInstance().createMultipleUnits("InfantryUnit", unitName, unitHealth, unitAttack, unitArmor, 500);
    assertEquals(500, unitList.size());

    for (Unit unit : unitList) {
      assertEquals(unit.getName(), unitName);
      assertEquals(unit.getHealth(), unitHealth);
      assertEquals(unit.getAttack(), unitAttack);
      assertEquals(unit.getArmor(), unitArmor);
      assertTrue(unit instanceof InfantryUnit);
    }

    List<Unit>
        unitList2 = UnitFactory.getInstance().createMultipleUnits("CavalryUnit", unitName, unitHealth, unitAttack, unitArmor, 300);
    assertEquals(300, unitList2.size());

    for (Unit unit : unitList2) {
      assertEquals(unit.getName(), unitName);
      assertEquals(unit.getHealth(), unitHealth);
      assertEquals(unit.getAttack(), unitAttack);
      assertEquals(unit.getArmor(), unitArmor);
      assertTrue(unit instanceof CavalryUnit);
    }

    List<Unit>
        unitList3 = UnitFactory.getInstance().createMultipleUnits("CommanderUnit", unitName, unitHealth, unitAttack, unitArmor, 600);
    assertEquals(600, unitList3.size());

    for (Unit unit : unitList3) {
      assertEquals(unit.getName(), unitName);
      assertEquals(unit.getHealth(), unitHealth);
      assertEquals(unit.getAttack(), unitAttack);
      assertEquals(unit.getArmor(), unitArmor);
      assertTrue(unit instanceof CommanderUnit);
    }

    List<Unit>
        unitList4 = UnitFactory.getInstance().createMultipleUnits("RangedUnit", unitName, unitHealth, unitAttack, unitArmor, 800);
    assertEquals(800, unitList4.size());

    for (Unit unit : unitList4) {
      assertEquals(unit.getName(), unitName);
      assertEquals(unit.getHealth(), unitHealth);
      assertEquals(unit.getAttack(), unitAttack);
      assertEquals(unit.getArmor(), unitArmor);
      assertTrue(unit instanceof RangedUnit);
    }

  }

}