package no.ntnu.idatg2001.wargames.ui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.ui.views.ArmyEditorDialog;

/**
 * Controller for ArmyMakerView. Control buttons to add, remove and refresh units.
 * Also shows how many units of each type.
 */
public class ArmyMakerController implements Initializable {

  private List<Unit> unitList;
  private boolean isArmyOne;

  @FXML private Label totalUnitsLabel;
  @FXML private Label totalInfantryUnitsUnitsLabel;
  @FXML private Label totalRangedUnitsLabel;
  @FXML private Label totalCavalryUnitsLabel;
  @FXML private Label totalCommanderUnitsUnitsLabel;
  @FXML private TableView<Unit> armyTableView;
  @FXML private TableColumn<Unit, String> typeColumn;
  @FXML private TableColumn<Unit, String> nameColumn;
  @FXML private TableColumn<Unit, String> healthColumn;
  @FXML private TableColumn<Unit, String> armorColumn;
  @FXML private TableColumn<Unit, String> attackColumn;
  @FXML private Label armyNameLabel;

  /** Initiates the this.unitsList, tableView and columns. */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    ObservableList<Unit> unitObservableListArmyOne =
        FXCollections.observableList(new ArrayList<>());
    unitList = unitObservableListArmyOne;
    armyTableView.setItems(unitObservableListArmyOne);
    armyTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    setTableView(typeColumn, nameColumn, healthColumn, armorColumn, attackColumn);
  }

  public void setArmyOne(boolean armyOne) {
    this.isArmyOne = armyOne;
  }

  /** Updated the units in the facade to the actual units from the tableView. */
  public void updateArmyUnits() {
    WargameFacade.getInstance().setUnits(armyTableView.getItems(), isArmyOne);
    updateAllTotalLabels();
  }

  /** Updated all the labels showing the different the amount of different types of units. */
  private void updateAllTotalLabels() {
    List<Integer> unitValues = WargameFacade.getInstance().getArmyUnitValues(isArmyOne);
    setValueToLabel(
        unitValues, totalUnitsLabel, totalInfantryUnitsUnitsLabel, totalRangedUnitsLabel,
        totalCavalryUnitsLabel, totalCommanderUnitsUnitsLabel);
  }

  private void setValueToLabel(List<Integer> unitValuesArmyTwo, Label totalInfantriesUnitsLabel,
      Label totalRangedUnitsLabel, Label totalCavalriesUnitsLabel, Label totalCommandersUnitsLabel,
      Label totalUnitsLabel) {

    totalUnitsLabel.setText(String.valueOf(unitValuesArmyTwo.get(0)));
    totalInfantriesUnitsLabel.setText(String.valueOf(unitValuesArmyTwo.get(1)));
    totalRangedUnitsLabel.setText(String.valueOf(unitValuesArmyTwo.get(2)));
    totalCavalriesUnitsLabel.setText(String.valueOf(unitValuesArmyTwo.get(3)));
    totalCommandersUnitsLabel.setText(String.valueOf(unitValuesArmyTwo.get(4)));
  }

  /**
   * Method to set the correct value for the all the columns.
   *
   * @param columnType The column for the unit type.
   * @param columnName The column for the unit name.
   * @param columnHealth The column for the unit health value.
   * @param columnAttack The column for the unit attack value.
   * @param columnArmor The column for the unit armor value.
   */
  private void setTableView(
      TableColumn<Unit, String> columnType,
      TableColumn<Unit, String> columnName,
      TableColumn<Unit, String> columnHealth,
      TableColumn<Unit, String> columnAttack,
      TableColumn<Unit, String> columnArmor) {

    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnHealth.setCellValueFactory(new PropertyValueFactory<>("health"));
    columnAttack.setCellValueFactory(new PropertyValueFactory<>("attack"));
    columnArmor.setCellValueFactory(new PropertyValueFactory<>("armor"));
  }

  @FXML
  private void onAddUnitsButtonClick() {
    showDialogAddUnits();
  }

  /**
   * Method to show the dialog to add units to the army. If the user input is not valid, it will
   * simply not add.
   */
  private void showDialogAddUnits() {
    ArmyEditorDialog dialog = new ArmyEditorDialog();
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
    Optional<List<String>> result = dialog.showAndWait();


    if (result.isPresent()) {
      try {
        unitList.addAll(WargameFacade.getInstance().makeUnits(result.get()));
        updateArmyUnits();
      } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
        showErrorMessage(e.getMessage());
      }
    }
  }

  /** Method to remove all selected rows form the tableviewArmyOne when remove button is pressed. */
  @FXML
  private void onRemoveUnitsButtonClick() {
    ObservableList<Unit> selectedRows = armyTableView.getSelectionModel().getSelectedItems();
    ArrayList<Unit> rows = new ArrayList<>(selectedRows);
    if (showAlertDelete(rows.size(), armyNameLabel.getText())) {
      rows.forEach(row -> armyTableView.getItems().remove(row));
    }
    updateAllTotalLabels();
  }

  /*** Method to reset (delete all units) of army one. Activated by button.*/
  @FXML
  private void onResetUnitsButtonClick() {
    if (showAlertDelete(armyTableView.getItems().size(), armyNameLabel.getText())) {
      armyTableView.getItems().clear();
    }
    updateAllTotalLabels();
  }

  /**
   * Method to show an alert when deleting units. Will trigger if the size are more than 0 and more
   * than 10. Between those it will not trigger, to get a confirmation when deleting many units, but
   * not few.
   *
   * @param size How many units are selected.
   * @param army The army name that was deleted from.
   * @return boolean true if the size is more than zero.
   */
  private boolean showAlertDelete(int size, String army) {
    if (size > 0) {
      if (size > 10) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unit deleter");
        alert.setHeaderText("Delete units from : " + army);
        alert.setContentText("Are you sure you want to delete " + size + " units?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
      } else {
        return size < 10;
      }
    }
    return false;
  }

  /**
   * Shows a {@code TextInputDialog}, dialog to change the name of an army.
   *
   * @return String, user input from dialog.
   */
  private String showNameTextInputDialog() {
    TextInputDialog textInputDialog = new TextInputDialog("name");
    textInputDialog.setHeaderText("Change name?");
    textInputDialog.setTitle("Army name");
    textInputDialog.setContentText("Army name : ");

    Optional<String> result = textInputDialog.showAndWait();
    return result.orElse(null);
  }

  /** Method to change the name army one. Activated by button. */
  @FXML
  private void onChangeArmyNameButtonClick() {
    try {
      String name = showNameTextInputDialog();
      if (name != null) {
        WargameFacade.getInstance().setArmyName(name, isArmyOne);
        armyNameLabel.setText(name);
      }
    } catch (IllegalArgumentException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**
   * Method to show an alert error message.
   *
   * @param e The message to show in the alert box.
   */
  private void showErrorMessage(String e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error not a valid input");
    alert.setHeaderText(e);
    alert.setContentText("Please try again");
    alert.showAndWait();
  }

  public List<Unit> getUnitList() {
    return unitList;
  }

  /**
   * Method to set the unitList and armyName for this controller.
   *
   * @param unitList The unitList to add.
   * @param armyName The army name to set.
   */
  public void setArmyControllerValues(List<Unit> unitList, String armyName) {
    WargameFacade.getInstance().setArmyName(armyName, isArmyOne);
    this.armyNameLabel.setText(armyName);
    this.unitList.addAll(unitList);
    updateArmyUnits();
  }

  public String getArmyName() {
    return armyNameLabel.getText();
  }
}
