package no.ntnu.idatg2001.wargames.model.battles;

import java.util.Random;
import no.ntnu.idatg2001.wargames.model.armies.Army;
import no.ntnu.idatg2001.wargames.model.units.Unit;

/**
 * Battle class, simulates a battle between two armies.
 * Battle also extends BattleUpdater to update information about the battle.
 *
 * @version 1.0.1
 */
public class Battle extends BattleUpdater {
  private Army armyOne;
  private Army armyTwo;
  private final Random rand;
  private boolean firstStep;

  /**
   * Constructs a new battle between two armies.
   *
   * @param armyOne The first army.
   * @param armyTwo The second army.
   */
  public Battle(Army armyOne, Army armyTwo) {
    this.armyOne = armyOne;
    this.armyTwo = armyTwo;
    rand = new Random();
  }

  public Army getArmyOne() {
    return armyOne;
  }

  public Army getArmyTwo() {
    return armyTwo;
  }

  public void setArmyOne(Army army) {
    armyOne = army;
  }

  public void setArmyTwo(Army army) {
    armyTwo = army;
  }

  /**
   * Picks a random unit from each army, and randomizing which unit is attacking the other.
   *
   * @param armyOne The first army in the battle.
   * @param armyTwo The second army in the battle.
   */
  private void randomAttack(Army armyOne, Army armyTwo) throws IllegalArgumentException {
    Unit u1 = armyOne.getRandom();
    Unit u2 = armyTwo.getRandom();

    int n = rand.nextInt(2);
    if (n == 0) {
      u1.attack(u2);
      hitUpdate(u1,u2);
      if (u2.getHealth() <= 0) {
        armyTwo.removeUnit(u2);
        sizeUpdate(armyOne.getUnits().size(), armyTwo.getUnits().size());
      }
    }
    if (n == 1) {
      u2.attack(u1);
      hitUpdate(u2,u1);
      if (u1.getHealth() <= 0) {
        armyOne.removeUnit(u1);
        sizeUpdate(armyOne.getUnits().size(), armyTwo.getUnits().size());
      }
    }
  }

  private void hitUpdate(Unit attacker, Unit defender) {
      hitUpdateAll(
          attacker.getName(),
          attacker.getAttack(),
          attacker.getAttackBonus(),
          defender.getName(),
          defender.getHealth(),
          defender.getResistBonus());

  }

  private void sizeUpdate(int armyOneSize, int armyTwoSize) {
    sizeUpdateAll(armyOneSize, armyTwoSize);
  }

  /**
   * Simulates a battle between two armies, based on random attacks from each army.
   *
   * @param terrain String, the terrain to change to battle to.
   * @return The winner of the armies.
   */
  public Army simulate(String terrain) {
    setTerrain(terrain);
    boolean simulating = true;
    Army army = null;
    while (simulating) {
      try {
        randomAttack(armyOne, armyTwo);
      } catch (IllegalArgumentException e) {
        if (!armyOne.hasUnits()) {
          simulating = false;
          army = armyTwo;
        } else if (!armyTwo.hasUnits()) {
          simulating = false;
          army = armyOne;
        }
      }
    }
    return army;
  }

  public boolean simulateStep() {
    try {
      if (!firstStep) {
        sizeUpdate(armyOne.getUnits().size(), armyTwo.getUnits().size());
        firstStep = true;
      }
      randomAttack(armyOne, armyTwo);
    } catch (IllegalArgumentException e) {
      return true;
    }
    return false;
  }

  public void setFirstStepFalse() {
    firstStep = false;
  }

  public void setTerrain(String terrain) {
    armyOne.setTerrainToUnits(terrain);
    armyTwo.setTerrainToUnits(terrain);
    
  }

  /**
   * Represents the information about the battle, the names of the armies and the winner, will also
   * show how many units were left from the winner.
   *
   * @return A string containing the information abut the battle.
   */
  public String getBattleInformation() {
    return "Battle between the armies '"
        + armyOne.getName()
        + "' and '"
        + armyTwo.getName();
  }
}
