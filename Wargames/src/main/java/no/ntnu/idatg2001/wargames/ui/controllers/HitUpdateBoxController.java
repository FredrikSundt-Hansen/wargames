package no.ntnu.idatg2001.wargames.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HitUpdateBoxController implements Initializable {

  @FXML
  private Label defenderNameLabel;
  @FXML
  private Label attackValueLabel;
  @FXML
  private Label attackBonusLabel;
  @FXML
  private Label nameAttackerLabel;
  @FXML
  private Label defenderHealthValue;
  @FXML
  private Label defenderResistValue;


  public void setValues(String nameAttacker, int attackValue, int attackBonus, String nameDefender,
      int healthDefender, int resistDefender) {

    defenderNameLabel.setText(nameAttacker);
    attackValueLabel.setText(String.valueOf(attackValue));
    attackBonusLabel.setText("+" + attackBonus);
    nameAttackerLabel.setText(String.valueOf(nameDefender));
    defenderHealthValue.setText(String.valueOf(healthDefender));
    defenderResistValue.setText("-" + resistDefender);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }
}
