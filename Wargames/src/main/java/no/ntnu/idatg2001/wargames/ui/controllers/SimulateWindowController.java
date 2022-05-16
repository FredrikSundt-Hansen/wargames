package no.ntnu.idatg2001.wargames.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import no.ntnu.idatg2001.wargames.model.UnitObserver;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.model.units.Unit;

public class SimulateWindowController implements UnitObserver {

  @FXML
  private Pane paneHitUpdater;

  public void setUp() {
    WargameFacade.getInstance().registerObserver(this);
  }

  @Override
  public void hitUpdate(Unit attacker, Unit defender) {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Attacker : " + attacker.getHealth());
    System.out.println("Defemder : " + defender.getHealth());
  }

  @Override
  public void sizeUpdate(int armySize, int infantrySize, int rangedSize, int cavalrySize, int commanderSize) {
    System.out.println(armySize);
    System.out.println(infantrySize);
    System.out.println(rangedSize);
    System.out.println(cavalrySize);
    System.out.println(commanderSize);
  }
}
