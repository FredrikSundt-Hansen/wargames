package no.ntnu.idatg2001.wargames.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * A hit-box for the hit-log, shows the information of one attack.
 * Consists of the attacker information, name, attack value and bonuses,
 * and Defender information, name health and resist bonus.
 */
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

  /**
   * Sets the corresponding values to the labels.
   *
   * @param nameAttacker Name of the attacker.
   * @param attackValue Attack value of the attacker.
   * @param attackBonus Attack bonus of the attacker.
   * @param nameDefender Name of the defender.
   * @param healthDefender Health value of the defender.
   * @param resistDefender Resist bonus of the defender.
   */
  public void setValues(String nameAttacker, int attackValue, int attackBonus, String nameDefender,
      int healthDefender, int resistDefender) {

    defenderNameLabel.setText(nameAttacker);
    attackValueLabel.setText(String.valueOf(attackValue));
    attackBonusLabel.setText("+" + attackBonus);
    nameAttackerLabel.setText(String.valueOf(nameDefender));
    defenderHealthValue.setText(String.valueOf(healthDefender));
    defenderResistValue.setText("-" + resistDefender);
  }

  /** T */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // The class implements Initializable, but it does not initialise anything else.
    // This method is required for JavaFx to work.
  }
}
