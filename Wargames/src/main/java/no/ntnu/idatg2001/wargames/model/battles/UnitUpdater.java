package no.ntnu.idatg2001.wargames.model.battles;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.UnitObserver;

public abstract class UnitUpdater {
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
