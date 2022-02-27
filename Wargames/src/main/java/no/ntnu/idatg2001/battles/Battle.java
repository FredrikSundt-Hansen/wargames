package no.ntnu.idatg2001.battles;

import java.util.InvalidPropertiesFormatException;
import java.util.Random;
import no.ntnu.idatg2001.armies.Army;
import no.ntnu.idatg2001.units.Unit;

public class Battle {
  private final Army armyOne;
  private final Army armyTwo;
  private Army winner;
  private Random rand;

  /**
   * Constructs a new battle between two armies.
   * @param armyOne The first army.
   * @param armyTwo The second army.
   */
  public Battle(Army armyOne, Army armyTwo) {
    this.armyOne = new Army(armyOne.getName(),armyOne.getAllUnits());
    this.armyTwo = new Army(armyTwo.getName(),armyTwo.getAllUnits());
    rand = new Random();
  }


  /**
   * Simulates a battle between two armies, based on random attacks from each army.
   * @return The winner of the armies.
   */
  public Army simulate() {
    boolean simulating = true;
    while (simulating) {
      try {
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
   * @return A string containing the information abut the battle.
   */
  @Override
  public String toString() {
    return "Battle between the armies '" + armyOne.getName() + "' and '" + armyTwo.getName() + " : \n" +
            "\nThe winner was the " + winner + "";
   }
}
