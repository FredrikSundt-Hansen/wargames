package no.ntnu.idatg2001.battle;

import java.util.Random;
import no.ntnu.idatg2001.armies.Army;
import no.ntnu.idatg2001.units.Unit;

public class Battle {
  private final Army armyOne;
  private final Army armyTwo;
  private Random rand;

  public Battle(Army armyOne, Army armyTwo) {
    this.armyOne = armyOne;
    this.armyTwo = armyTwo;
  }

  public Army simulate() {
    Army winner;
    do {
      rand = new Random();
      Unit u1 = armyOne.getRandom();
      Unit u2 = armyTwo.getRandom();
      int number = rand.nextInt(3);
      if (number == 1) {
        u1.attack(u2);
      } else if (number == 2) {
        u2.attack(u1);
      }
      if (u1.getHealth() == 0) {
        armyOne.remove(u1);
      } else if (u2.getHealth() == 0) {
        armyTwo.remove(u2);
      }
    } while (!armyOne.hasUnits() || !armyTwo.hasUnits());
    if (armyOne.hasUnits()) {
      winner = armyOne;
    } else {
      winner = armyTwo;
    }
    return winner;
  }

  @Override
  public String toString() {
    return "Battle between " + "army " + armyOne.getName() + "and army " + armyTwo.getName();
  }
}
