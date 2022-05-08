package no.ntnu.idatg2001.wargames.model.battles;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.wargames.model.ArmyObserver;
import no.ntnu.idatg2001.wargames.model.units.Unit;

public abstract class BattleUpdater {
  protected List<ArmyObserver> observerList = new ArrayList<>();

  public void register(ArmyObserver observer) {
    this.observerList.add(observer);
  }

  public void updateAll(Unit attacker, Unit defender) {
    for (ArmyObserver o : observerList) {
      o.update(attacker, defender);
    }
  }
}
