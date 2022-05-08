package no.ntnu.idatg2001.wargames.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import no.ntnu.idatg2001.wargames.model.ArmyObserver;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.model.units.Unit;

public class SimulateWindowController implements ArmyObserver {

  @FXML
  private Pane paneHitUpdater;

  public void setUp() {
    WargameFacade.getInstance().registerObserver(this);
  }

  @Override
  public void update(Unit attacker, Unit defender) {
    System.out.println("Attacker : " + attacker.getHealth());
    System.out.println("Defemder : " + defender.getHealth());
  }
}
