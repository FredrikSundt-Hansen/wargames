package no.ntnu.idatg2001.wargames.battles;

import java.util.Random;
import no.ntnu.idatg2001.wargames.armies.Army;
import no.ntnu.idatg2001.wargames.units.Unit;

/**
 * Battle class, simulates a battle between two armies.
 *
 * @version 1.0.1
 */
public class Battle {
  private final Army armyOne;
  private final Army armyTwo;
  private Army winner;
  private Random rand;

  /**
   * Constructs a new battle between two armies.
   *
   * @param armyOne The first army.
   * @param armyTwo The second army.
   */
  public Battle(Army armyOne, Army armyTwo) {
    this.armyOne = new Army(armyOne.getName(), armyOne.getAllUnits());
    this.armyTwo = new Army(armyTwo.getName(), armyTwo.getAllUnits());
    rand = new Random();
  }

  /**
   * Picks a random unit from each army, and randomizing which unit is attacking the other.
   *
   * @param armyOne The first army in the battle.
   * @param armyTwo The second army in the battle.
   */
  private void randomAttack(Army armyOne, Army armyTwo) {
    Unit u1 = armyOne.getRandom();
    Unit u2 = armyTwo.getRandom();

    int n = rand.nextInt(2);
    if (n == 0) {
      u1.attack(u2);
      if (u2.getHealth() <= 0) {
        armyTwo.removeUnit(u2);
      }
    }
    if (n == 1) {
      u2.attack(u1);
      if (u1.getHealth() <= 0) {
        armyOne.removeUnit(u1);
      }
    }
  }

  /**
   * Simulates a battle between two armies, based on random attacks from each army.
   *
   * @return The winner of the armies.
   */
  public Army simulate() {
    boolean simulating = true;
    while (simulating) {
      try {
        randomAttack(armyOne, armyTwo);
      } catch (IllegalArgumentException  e) {
        if (!armyOne.hasUnits()) {
          winner = armyTwo;
          simulating = false;
        } else if (!armyTwo.hasUnits()) {
          winner = armyOne;
          simulating = false;
        }
      }
    }
    return winner;
  }

  /**
   * Represents the information about the battle, the names of the armies and the winner, will also
   * show how many units were left from the winner.
   *
   * @return A string containing the information abut the battle.
   */
  @Override
  public String toString() {
    return "Battle between the armies '" + armyOne.getName() + "' and '" + armyTwo.getName()
        + " : \n" + "\nThe winner was the " + winner + "";
  }
}
