package no.ntnu.idatg2001.wargames.model.battles;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the updating class of the observable pattern. Holds a register of all
 * UnitObservers, and calls them to update.
 */
public abstract class BattleUpdater {
  protected List<UnitObserver> observerList = new ArrayList<>();

  public void register(UnitObserver observer) {
    this.observerList.add(observer);
  }

  public void hitUpdateAll(String nameAttacker, int attackValue, int attackBonus, String nameDefender,
                           int healthDefender, int resistDefender) {
    observerList.forEach(unitObserver -> unitObserver.
        hitUpdate(nameAttacker,attackValue,attackBonus,nameDefender,healthDefender,resistDefender));
  }

  public void sizeUpdateAll(int sizeOne, int sizeTwo) {
    observerList.forEach(unitObserver -> unitObserver
        .sizeUpdate(sizeOne, sizeTwo));
  }
}
