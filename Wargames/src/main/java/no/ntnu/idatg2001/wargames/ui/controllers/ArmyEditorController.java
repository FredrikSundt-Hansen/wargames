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

/**
 * Controller class for ArmyEditorDialogView. Sets up every javafx component in the view,
 * (spinners, choice-boxes and text-field) and holds the values so the user can make new units and
 * add it to the army of their choice.
 * Implements initializable interface, replacing the constructor, and is being called by the javafx
 * runtime upon initialization of the GUI.
 */
public class ArmyEditorController implements Initializable {

  @FXML
  private ChoiceBox<String> choiceBoxArmyNames;
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
    choiceBoxArmyNames.setItems(
        FXCollections.observableList(WargameFacade.getInstance().getArmyNames()));

    textFieldName.setPromptText("name");

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
        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 10, 10);
    spinner.setValueFactory(valueFactory);
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
            spinnerHealth.getValue(),
            spinnerAttack.getValue(),
            spinnerArmor.getValue(),
            spinnerAmount.getValue());
  }

  /**
   * Method to check user input if it is valid for adding to armies.
   * Checks that the user has chosen an army, a type and a name.
   * All these criteria are needed to make a unit.
   * Spinners does not need to checked because their spinner-factory
   * makes sure the value cannot be lower than 1, or higher than 1000,
   * and can only have integer input.
   *
   * @return True if the user inputs are correct.
   */
  public boolean validInput() {
    return choiceBoxType.getValue() != null
        && choiceBoxArmyNames.getValue() != null
        && textFieldName.getText() != null;
  }

  /**
   * Accessor method for the {@code choiceBoxArmyNames}. Gets the selected army from the choicebox.
   *
   * @return The selected army.
   */
  public String getArmyFromChoiceBox() {
    return choiceBoxArmyNames.getValue();
  }
}
