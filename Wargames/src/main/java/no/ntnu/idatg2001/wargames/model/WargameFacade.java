package no.ntnu.idatg2001.wargames.model;

import java.util.List;
import javafx.event.ActionEvent;
import no.ntnu.idatg2001.wargames.model.armies.Army;
import no.ntnu.idatg2001.wargames.model.battles.Battle;
import no.ntnu.idatg2001.wargames.model.units.Unit;

public class WargameFacade {

  private static volatile WargameFacade instance;
  private Battle battle;

  private WargameFacade() {

  }

  public static WargameFacade getInstance() {
    if (instance == null) {
      synchronized (WargameFacade.class) {
        instance = new WargameFacade();
      }
    }
    return instance;
  }

  private void setBattle(Battle battle) {
    this.battle = battle;
  }

  private void addUnitsToArmy(List<Unit> unitList, String army) {
    if (army.strip().equalsIgnoreCase("armyone") || army.strip().equalsIgnoreCase("one")) {
      battle.getArmyOne().addAllUnits(unitList);
    } else if (army.strip().equalsIgnoreCase("armytwo") || army.strip().equalsIgnoreCase("two")) {
      battle.getArmyTwo().addAllUnits(unitList);
    }
  }

  private void simulate(String terrain) {
    battle.simulate(terrain);
  }


}
