package no.ntnu.idatg2001.wargames.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import no.ntnu.idatg2001.wargames.model.ArmyObserver;
import no.ntnu.idatg2001.wargames.model.armies.Army;
import no.ntnu.idatg2001.wargames.model.units.Unit;

public class SimulateWindowController implements ArmyObserver {

  private Army army;

  @FXML
  private Pane paneHitUpdater;

  public void hitUpdate() {

  }

  @Override
  public void update() {
  }


}
