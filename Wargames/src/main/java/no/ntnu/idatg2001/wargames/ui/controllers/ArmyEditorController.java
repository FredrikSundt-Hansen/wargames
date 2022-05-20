package no.ntnu.idatg2001.wargames.ui.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.model.units.Unit;

public class ArmyEditorController implements Initializable {

  private int unitHealth;
  private int unitAttack;
  private int unitArmor;
  private int unitAmount;

  @FXML
  private ChoiceBox<String> choiceBoxArmies;
  @FXML
  private ChoiceBox<String> choiceBoxType;
  @FXML
  private TextField textFieldName;
  @FXML
  private Spinner<Integer> spinnerHealth;
  @FXML
  private Spinner<Integer> spinnerAttack;
  @FXML
  private Spinner<Integer> spinnerArmor;
  @FXML
  private Spinner<Integer> spinnerAmount;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    choiceBoxType.setItems(
        FXCollections.observableList(WargameFacade.getInstance().getAllDifferentUnits()));
    choiceBoxArmies.setItems(
        FXCollections.observableList(WargameFacade.getInstance().getArmyNames()));

    setSpinnerValue(spinnerHealth);
    setSpinnerValue(spinnerAttack);
    setSpinnerValue(spinnerArmor);
    setSpinnerValue(spinnerAmount);
    spinnerHealth.valueProperty()
        .addListener((observableValue, oldValue, newValue) -> unitHealth = newValue);
    spinnerAttack.valueProperty()
        .addListener((observableValue, oldValue, newValue) -> unitAttack = newValue);
    spinnerArmor.valueProperty()
        .addListener((observableValue, oldValue, newValue) -> unitArmor = newValue);
    spinnerAmount.valueProperty()
        .addListener(((observableValue, oldValue, newValue) -> unitAmount = newValue));
  }

  private void setSpinnerValue(Spinner<Integer> spinner) {
    spinner.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                spinner.getEditor().setText(oldValue);
              }});

    SpinnerValueFactory<Integer> valueFactoryHeath =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000);
    spinner.setValueFactory(valueFactoryHeath);
  }

  /**
   * Method to return list of units from facade with the given values.
   * @return List of units with the given user inputs.
   */
  public List<Unit> getUnitList() {
    return WargameFacade.getInstance()
        .makeUnits(
            choiceBoxType.getValue(),
            textFieldName.getText(),
            unitHealth,
            unitAttack,
            unitArmor,
            unitAmount);

  }

  /**
   * Method to check user input if is not null og below zero.
   * @return True if the user inputs are correct.
   */
  public boolean validInput() {
    return choiceBoxType.getValue() != null
        && choiceBoxArmies.getValue() != null
        && textFieldName.getText() != null
        && unitHealth >= 0
        && unitArmor >= 0
        && unitAttack >= 0
        && unitAmount >= 0;
  }

  public String getArmyFromChoiceBox() throws IllegalArgumentException {
    return choiceBoxArmies.getValue();
  }
}
