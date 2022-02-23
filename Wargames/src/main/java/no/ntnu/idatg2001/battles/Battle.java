package no.ntnu.idatg2001.battles;

import java.util.Random;
import no.ntnu.idatg2001.armies.Army;
import no.ntnu.idatg2001.units.Unit;

public class Battle {
  private final Army armyOne;
  private final Army armyTwo;
  private Army winner;
  private Random rand;

  public Battle(Army armyOne, Army armyTwo) {
    this.armyOne = armyOne;
    this.armyTwo = armyTwo;
    rand = new Random();
  }

  public Army simulate() {
    boolean simulating = true;

    while (simulating) {
      try {
        Unit u1 = armyOne.getRand();
        Unit u2 = armyTwo.getRand();

        int n = rand.nextInt(2);
        if (n == 0) {
          u1.attack(u2);
          if (u2.getHealth() <= 0) {
            armyTwo.remove(u2);
          }
        }
        if (n == 1) {
          u2.attack(u1);
          if (u1.getHealth() <= 0) {
            armyOne.remove(u1);
          }
        }
      } catch (IllegalArgumentException e) {
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



  @Override
  public String toString() {
    return "Battle between the armies '" + armyOne.getName() + "' and '" + armyTwo.getName() + " : \n" +
            "\nThe winner was the " + winner + "";
   }
}
