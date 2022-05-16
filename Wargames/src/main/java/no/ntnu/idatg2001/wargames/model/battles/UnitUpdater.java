package no.ntnu.idatg2001.wargames.model.battles;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.UnitObserver;
import no.ntnu.idatg2001.wargames.model.units.Unit;

public abstract class UnitUpdater {
  protected List<UnitObserver> observerList = new ArrayList<>();

  public void register(UnitObserver observer) {
    this.observerList.add(observer);
  }

  public void hitUpdateAll(Unit attacker, Unit defender) {
    observerList.forEach(unitObserver -> unitObserver.hitUpdate(attacker, defender));
  }

  public void sizeUpdateAll(int armySize, int infantrySize, int rangedSize, int cavalrySize, int commanderSize) {
    observerList.forEach(unitObserver -> unitObserver
        .sizeUpdate(armySize, infantrySize, rangedSize, cavalrySize, commanderSize));
  }
}
