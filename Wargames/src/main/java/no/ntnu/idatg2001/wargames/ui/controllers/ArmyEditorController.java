package no.ntnu.idatg2001.wargames.ui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

/**
 * Controller class for ArmyEditorDialogView. Sets up every javafx component in the view,
 * (spinners, choice-boxes and text-field) and holds the values so the user can make new units and
 * add it to the army of their choice.
 * Implements initializable interface, replacing the constructor, and is being called by the javafx
 * runtime upon initialization of the GUI.
 */
public class ArmyEditorController implements Initializable {

  @FXML private ChoiceBox<String> choiceBoxType;
  @FXML private TextField textFieldName;
  @FXML private Spinner<Integer> spinnerHealth;
  @FXML private Spinner<Integer> spinnerAttack;
  @FXML private Spinner<Integer> spinnerArmor;
  @FXML private Spinner<Integer> spinnerAmount;

  /**
   *Initiates the all fxml elements, sets textFieldName to not take comma as input.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    List<String> unitTypes = new ArrayList<>();
    unitTypes.add("InfantryUnit");
    unitTypes.add("RangedUnit");
    unitTypes.add("CavalryUnit");
    unitTypes.add("CommanderUnit");
    choiceBoxType.setItems(
        FXCollections.observableList(unitTypes));

    textFieldName.setPromptText("name");

    textFieldName
        .textProperty()
        .addListener(
            ((observableValue, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                textFieldName.setText(newValue.replace(",",""));
              }}));

    setSpinnerValue(spinnerHealth);
    setSpinnerValue(spinnerAttack);
    setSpinnerValue(spinnerArmor);
    setSpinnerValue(spinnerAmount);
  }

  /**
   * Method to set the default spinner values for this view for all spinners being used.
   * Makes sure that the user cannot write letters in the spinner, only integers.
   * Adds a value-factory to the spinner with min 1, maks 1000, default value 10,
   * and increments by 10.
   * @param spinner The spinner to change.
   */
  private void setSpinnerValue(Spinner<Integer> spinner) {

    spinner.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                spinner.getEditor().setText(oldValue);
              }});

    SpinnerValueFactory<Integer> valueFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 10, 5);
    spinner.setValueFactory(valueFactory);
  }

  /**
   * Method to return list of units from facade with the given values.
   * @return List of units with the given user inputs.
   */
  public List<String> getUnitList() {
    List<String> userInputValues = new ArrayList<>();
    userInputValues.add(choiceBoxType.getValue());
    userInputValues.add(textFieldName.getText());
    userInputValues.add(String.valueOf(spinnerHealth.getValue()));
    userInputValues.add(String.valueOf(spinnerAttack.getValue()));
    userInputValues.add(String.valueOf(spinnerArmor.getValue()));
    userInputValues.add(String.valueOf(spinnerAmount.getValue()));

    return userInputValues;
  }
}
