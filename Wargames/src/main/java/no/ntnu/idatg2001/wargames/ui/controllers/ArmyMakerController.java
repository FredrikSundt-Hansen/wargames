package no.ntnu.idatg2001.wargames.ui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.ui.views.ArmyEditorDialog;

public class ArmyMakerController implements Initializable {

  private List<Unit> unitList;

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

  private boolean armyOne;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    ObservableList<Unit> unitObservableListArmyOne =
        FXCollections.observableList(new ArrayList<>());
    unitList = unitObservableListArmyOne;
    armyTableView.setItems(unitObservableListArmyOne);
    armyTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    setTableView(typeColumn, nameColumn, healthColumn, armorColumn,
        attackColumn);
  }

  public void setArmyOne(boolean armyOne) {
    this.armyOne = armyOne;
  }

  /**
   * Updated the units in the facade to the actual units from the tableView.
   */
  public void updateArmyUnits() {
    WargameFacade.getInstance().setUnits(armyTableView.getItems(), armyNameLabel.getText());
    updateAllTotalLabels();
  }

  /**
   *Updated all the labels showing the different the amount of different types of units.
   */
  private void updateAllTotalLabels() {
    WargameFacade instance = WargameFacade.getInstance();
    totalUnitsLabel.setText(String.valueOf(instance.geAmountOfUnits(armyNameLabel.getText())));
    totalInfantryUnitsUnitsLabel.setText(String.valueOf(instance.getArmyOneAmountInfantry(armyNameLabel.getText())));
    totalRangedUnitsLabel.setText(String.valueOf(instance.getArmyOneAmountRanged(armyNameLabel.getText())));
    totalCavalryUnitsLabel.setText(String.valueOf(instance.getArmyOneAmountCavalry(armyNameLabel.getText())));
    totalCommanderUnitsUnitsLabel.setText(String.valueOf(instance.getArmyOneAmountCommander(armyNameLabel.getText())));
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
   * Method to show the dialog to add units to the army.
   * If the user input is not valid, it will simply not add.
   */
  private void showDialogAddUnits() {
    if ((armyNameLabel.getText() != null && !armyNameLabel.getText().isEmpty())) {
      ArmyEditorDialog dialog = new ArmyEditorDialog(armyNameLabel.getText());
      Optional<List<String>> result = dialog.showAndWait();

      if (result.isPresent()) {
        try {
          unitList.addAll(WargameFacade.getInstance().makeUnits(result.get()));
          updateArmyUnits();
        } catch (IllegalArgumentException e) {
          showErrorMessage(e.getMessage());
        }
      }
    } else {
      showErrorMessage("Need to have a name on at least on army");
    }
  }

  /** Method to remove all selected rows form the tableviewArmyOne when remove button is pressed.
   * Activated by button.
   */
  public void onRemoveUnitsButtonClick() {
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
   * Method to show an alert when deleting units.
   * Will trigger if the size are more than 0 and more than 10.
   * Between those it will not trigger, to get a confirmation when deleting many units, but not few.
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
      } else return size < 10;
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
    textInputDialog.getDialogPane().setContentText("Army name : ");
    TextField input = textInputDialog.getEditor();

    Optional<String> result = textInputDialog.showAndWait();
    if (result.isPresent()) {
      return input.getText();
    } else {
      return null;
    }
  }

  /**Method to change the name army one. Activated by button.*/
  @FXML
  private void onChangeArmyOneNameButtonClick() {
    String name = showNameTextInputDialog();
    if (name != null && !name.isEmpty()) {
      setArmyNameLabel(name);

    }
  }

  private void setArmyNameLabel(String name) {
    if (name != null && !name.isEmpty()) {
      armyNameLabel.setText(name);
      if (armyOne) {
        WargameFacade.getInstance().setArmyOneName(name);
      } else {
        WargameFacade.getInstance().setArmyTwoName(name);
      }
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

  public void setUnitList(List<Unit> unitList) {
    this.unitList.addAll(unitList);

  }

  public String getArmyName() {
    return armyNameLabel.getText();
  }

  public void setArmyName(String armyName) {
    this.armyNameLabel.setText(armyName);
  }
}
