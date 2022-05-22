package no.ntnu.idatg2001.wargames.ui.controllers;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.ui.views.ArmyEditorDialog;
import no.ntnu.idatg2001.wargames.ui.views.WargamesApplication;

/**
 * The Controller for the ArmyMakerView. Controls the user interaction from the gui in
 * ArmyMakerView, and the dialog ArmyEditorDialog. It hols the tableview data, as well as the
 * terrain image data. It shows the current terrain for the battle. The terrain for simulating is
 * then selected based on the url of the image currently showing.
 * Additionally, it has function save armies, load previous one
 * and load an army from file.
 */
public class ArmyMakerController implements Initializable {

  private Image plains;
  private Image hills;
  private Image forest;

  private List<Unit> unitListArmyOne;
  private List<Unit> unitListArmyTwo;

  @FXML private Label infantryTotalArmyOneLabel;
  @FXML private Label rangedTotalArmyOneLabel;
  @FXML private Label cavalryTotalArmyOneLabel;
  @FXML private Label commanderTotalArmyOneLabel;
  @FXML private Label infantryTotalArmyTwoLabel;
  @FXML private Label rangedTotalArmyTwoLabel;
  @FXML private Label cavalryTotalArmyTwoLabel;
  @FXML private Label commanderTotalArmyTwoLabel;
  @FXML private TableView<Unit> armyOneTableView;
  @FXML private TableColumn<Unit, String> typeArmyOneColumn;
  @FXML private TableColumn<Unit, String> nameArmyOneColumn;
  @FXML private TableColumn<Unit, String> healthArmyOneColumn;
  @FXML private TableColumn<Unit, String> armorArmyOneColumn;
  @FXML private TableColumn<Unit, String> attackArmyOneColumn;
  @FXML private TableView<Unit> armyTwoTableView;
  @FXML private TableColumn<Unit, String> typeArmyTwoColumn;
  @FXML private TableColumn<Unit, String> nameArmyTwoColumn;
  @FXML private TableColumn<Unit, String> healthArmyTwoColumn;
  @FXML private TableColumn<Unit, String> armorArmyTwoColumn;
  @FXML private TableColumn<Unit, String> attackArmyTwoColumn;
  @FXML private Label armyOneNameLabel;
  @FXML private Label armyTwoNameLabel;
  @FXML private ImageView terrainImageView;
  @FXML private ChoiceBox<String> terrainChoiceBox;
  @FXML private Label currentTerrainLabel;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    plains = new Image(new File("src/main/resources/images/Plains_Image.jpg").toURI().toString());
    hills = new Image(new File("src/main/resources/images/Hills_Image.jpg").toURI().toString());
    forest = new Image(new File("src/main/resources/images/Forest_Image.jpg").toURI().toString());
    terrainImageView.setImage(plains);

    List<String> differentTerrains = new ArrayList<>();
    String stringPlains = getTerrainValueFromImage(this.plains);
    String stringHills = getTerrainValueFromImage(this.hills);
    String stringForest = getTerrainValueFromImage(this.forest);

    differentTerrains.add(stringPlains);
    differentTerrains.add(stringHills);
    differentTerrains.add(stringForest);

    terrainChoiceBox.setItems(FXCollections.observableList(differentTerrains));
    terrainChoiceBox.setValue(stringPlains);

    terrainChoiceBox
        .onActionProperty()
        .setValue(actionEvent -> setImage(terrainChoiceBox.getValue()));

    ObservableList<Unit> unitObservableListArmyOne =
        FXCollections.observableList(new ArrayList<>());
    unitListArmyOne = unitObservableListArmyOne;
    armyOneTableView.setItems(unitObservableListArmyOne);
    armyOneTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    ObservableList<Unit> unitObservableListArmyTwo =
        FXCollections.observableList(new ArrayList<>());
    unitListArmyTwo = unitObservableListArmyTwo;
    armyTwoTableView.setItems(unitObservableListArmyTwo);
    armyTwoTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    setTableView(typeArmyOneColumn, nameArmyOneColumn, healthArmyOneColumn, armorArmyOneColumn,
        attackArmyOneColumn);

    setTableView(typeArmyTwoColumn, nameArmyTwoColumn, healthArmyTwoColumn, attackArmyTwoColumn,
        armorArmyTwoColumn);

    updateArmyUnits();
    updateArmyNamesLabels();
  }

  /**
   * Updated the units in the facade to the actual units from the tableView.
   */
  private void updateArmyUnits() {
    WargameFacade.getInstance().addUnitsArmyOne(armyOneTableView.getItems());
    WargameFacade.getInstance().addUnitsArmyTwo(armyTwoTableView.getItems());
    updateAllTotalLabels();
  }

  /**
   *Updated all the labels showing the different the amount of different types of units.
   */
  private void updateAllTotalLabels() {
    WargameFacade instance = WargameFacade.getInstance();

    infantryTotalArmyOneLabel.setText(String.valueOf(instance.getArmyOneAmountInfantry()));
    rangedTotalArmyOneLabel.setText(String.valueOf(instance.getArmyOneAmountRanged()));
    cavalryTotalArmyOneLabel.setText(String.valueOf(instance.getArmyOneAmountCavalry()));
    commanderTotalArmyOneLabel.setText(String.valueOf(instance.getArmyOneAmountCommander()));
    infantryTotalArmyTwoLabel.setText(String.valueOf(instance.getArmyTwoAmountInfantry()));
    rangedTotalArmyTwoLabel.setText(String.valueOf(instance.getArmyTwoAmountRanged()));
    cavalryTotalArmyTwoLabel.setText(String.valueOf(instance.getArmyTwoAmountCavalry()));
    commanderTotalArmyTwoLabel.setText(String.valueOf(instance.getArmyTwoAmountCommander()));
  }

  /**
   * Updated the labels with the values from {@code armyOneName} and {@code armyTwoName}.
   */
  private void updateArmyNamesLabels() {
    armyOneNameLabel.setText(WargameFacade.getInstance().getArmyOneName());
    armyTwoNameLabel.setText(WargameFacade.getInstance().getArmyTwoName());
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

  /**
   * Method to get the string value associated with the terrain.
   *
   * @param image Image to get the terrain string value from.
   * @return Terrain value as a string.
   */
  private String getTerrainValueFromImage(Image image) {
    return image.getUrl().split("/")[14].split("\\.")[0].split("_")[0];
  }

  /**
   * Method to set the image of terrain in the middle of the view. Will switch image depending on
   * which image is currently showing.
   *
   * @param terrain The terrain as a string.
   */
  private void setImage(String terrain) {
    if (terrain.equalsIgnoreCase(getTerrainValueFromImage(plains))) {
      setTerrainImageView(plains);
    } else if (terrain.equalsIgnoreCase(getTerrainValueFromImage(hills))) {
      setTerrainImageView(hills);
    } else if (terrain.equalsIgnoreCase(getTerrainValueFromImage(forest))) {
      setTerrainImageView(forest);
    }
  }

  /**
   * Method to set an image as the current terrain-image.
   *
   * @param terrainImage The image to load, showing the terrain.
   */
  private void setTerrainImageView(Image terrainImage) {
    terrainImageView.setImage(terrainImage);
    currentTerrainLabel.setVisible(true);
    currentTerrainLabel.setText(getTerrainValueFromImage(terrainImage));
  }

  /**
   * When user has clicked has decided to switch terrain image.
   * Switches between terrain bases on which image is currently showing.
   */
  @FXML
  private void onSwitchTerrainMouseClick() {
    if (terrainImageView.getImage().getUrl().equals(plains.getUrl())) {
      setTerrainImageView(hills);
      terrainChoiceBox.setValue(getTerrainValueFromImage(hills));
    } else if (terrainImageView.getImage().getUrl().equals(hills.getUrl())) {
      setTerrainImageView(forest);
      terrainChoiceBox.setValue(getTerrainValueFromImage(forest));
    } else if (terrainImageView.getImage().getUrl().equals(forest.getUrl())) {
      setTerrainImageView(plains);
      terrainChoiceBox.setValue(getTerrainValueFromImage(plains));
    }
  }

  @FXML
  private void onAddUnitsArmyOneButtonClick() {
    showDialogAddUnits();

  }

  @FXML
  private void onAddUnitsArmyTwoButtonClick() {
    showDialogAddUnits();
  }

  /**
   * Method to show the dialog to add units to the army.
   * If the user input is not valid, it will simply not add.
   */
  private void showDialogAddUnits() {
    if (!armyOneNameLabel.getText().isEmpty()
        || !armyTwoNameLabel.getText().isEmpty()) {

      ArmyEditorDialog dialog = new ArmyEditorDialog();
      Optional<List<Unit>> result = dialog.showAndWait();

      if (result.isPresent() && dialog.getValidInput()) {
          String armyName = dialog.getCurrentArmy();
          addUnitsToCorrectArmy(result.get(), armyName);
      }
    } else {
      showErrorMessage("Need to have a name on at least on army");
    }
  }

  /**
   * Method to add a list of units with the army name to the correct army based on the name.
   *
   * @param units The units to add.
   * @param armyName The name of the army, if it is the same as one of the current army names.
   */
  private void addUnitsToCorrectArmy(List<Unit> units, String armyName) {
    if (armyName.equals(armyOneNameLabel.getText())) {
      unitListArmyOne.addAll(units);
    } else if (armyName.equals(armyTwoNameLabel.getText())) {
      unitListArmyTwo.addAll(units);
    }
    updateArmyUnits();

  }

  /** Method to remove all selected rows form the tableviewArmyOne when remove button is pressed.
   * Activated by button.
   */
  public void onRemoveUnitsArmyOneButtonClick() {
    ObservableList<Unit> selectedRows = armyOneTableView.getSelectionModel().getSelectedItems();
    ArrayList<Unit> rows = new ArrayList<>(selectedRows);
    if (showAlertDelete(rows.size(), armyOneNameLabel.getText())) {
      rows.forEach(row -> armyOneTableView.getItems().remove(row));
    }
  }

  /** Method to remove all selected rows form the tableviewArmyTwo when remove button is pressed.
   * Activated by button.
   */
  @FXML
  private void onRemoveUnitsArmyTwoButtonClick() {
    ObservableList<Unit> selectedRows = armyTwoTableView.getSelectionModel().getSelectedItems();
    ArrayList<Unit> rows = new ArrayList<>(selectedRows);
    if (showAlertDelete(rows.size(), armyTwoNameLabel.getText())) {
      rows.forEach(row -> armyTwoTableView.getItems().remove(row));
    }
  }

  /*** Method to reset (delete all units) of army one. Activated by button.*/
  @FXML
  private void onResetArmyOneButtonClick() {
    if (showAlertDelete(armyOneTableView.getItems().size(), armyOneNameLabel.getText())) {
      armyOneTableView.getItems().clear();
    }
  }

  /*** Method to reset (delete all units) of army two. Activated by button.*/
  @FXML
  private void onResetArmyTwoButtonClick() {
    if (showAlertDelete(armyTwoTableView.getItems().size(), armyTwoNameLabel.getText())) {
      armyTwoTableView.getItems().clear();
    }
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

  /**Method to change the name army one. Activated by button.*/
  @FXML
  private void onChangeArmyOneNameButtonClick() {
    String name = showNameTextInputDialog("Army one name");
    if (name != null && !name.isEmpty()) {
      setArmyOneNameLabel(name);
    }
  }

  /**Method to change the name army two. Activated by button.*/
  @FXML
  private void onChangeArmyTwoNameButtonClick() {
    String name = showNameTextInputDialog("Army two name");
    if (name != null && !name.isEmpty()) {
      setArmyTwoNameLabel(name);
    }
  }

  /**
   * Shows a {@code TextInputDialog}, dialog to change the name of an army.
   *
   * @param message The title of the dialog.
   * @return String, user input from dialog.
   */
  private String showNameTextInputDialog(String message) {
    TextInputDialog textInputDialog = new TextInputDialog("name");
    textInputDialog.setHeaderText("Change name?");
    textInputDialog.setTitle(message);
    textInputDialog.getDialogPane().setContentText("Army name : ");
    TextField input = textInputDialog.getEditor();

    Optional<String> result = textInputDialog.showAndWait();
    if (result.isPresent()) {
      return input.getText();
    } else {
      return null;
    }
  }

  private void setArmyOneNameLabel(String name) {
    if (name != null && !name.equals(armyTwoNameLabel.getText())) {
      armyOneNameLabel.setText(name);
    }
  }

  private void setArmyTwoNameLabel(String name) {
    if (name != null && !name.equals(armyOneNameLabel.getText())) {
      armyTwoNameLabel.setText(name);
    }
  }

  /**
   * Method to show Simulate view.
   * If army one unit is not empty, and army two units is not empty.
   * Updated units and name to the armies.
   */
  @FXML
  private void onSimulateButtonClick() {
    if (!unitListArmyOne.isEmpty() && !unitListArmyTwo.isEmpty()) {
      WargameFacade.getInstance().setArmyOneName(armyOneNameLabel.getText());
      WargameFacade.getInstance().setArmyTwoName(armyTwoNameLabel.getText());
      updateArmyUnits();
      WargameFacade.getInstance()
          .setCurrentTerrain(getTerrainValueFromImage(terrainImageView.getImage()));
      WargameFacade.getInstance().setBattleTerrain();
      WargamesApplication.getToSimulateBattle();
    } else {
      showErrorMessage("Both armies need to have units in them.");
    }
  }

  /** Saves army one to resources file.*/
  @FXML
  private void onSaveArmyOneButtonClick() {
    try {
      WargameFacade.getInstance().saveArmyOneToResources();
      WargameFacade.getInstance().setArmyOneName(armyOneNameLabel.getText());
    } catch (IOException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /** Saves army two to resources file.*/
  @FXML
  private void onSaveArmyTwoButtonClick() {
    try {
      WargameFacade.getInstance().saveArmyTwoToResources();
      WargameFacade.getInstance().setArmyTwoName(armyTwoNameLabel.getText());
    } catch (IOException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**Loads previous army one.*/
  @FXML
  private void onLoadPreviousArmyOneButtonClick() {
    try {
      unitListArmyOne.addAll(WargameFacade.getInstance().getArmyOneFromResources());
      updateArmyNamesLabels();
      updateArmyUnits();
    } catch (IOException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**Loads previous army two.*/
  @FXML
  private void onLoadPreviousArmyTwoButtonClick() {
    try {
      unitListArmyTwo.addAll(WargameFacade.getInstance().getArmyTwoFromResources());
      updateArmyNamesLabels();
      updateArmyUnits();
    } catch (IOException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**
   * Loads a demo file that has 161 units on both armies, with names.
   * Used to give the user an idea on how to make an army.
   */
  @FXML
  private void onLoadDemoFileButtonClick() {
    try {
      unitListArmyOne.addAll(WargameFacade.getInstance().getArmyOneFromDemoFile());
      unitListArmyTwo.addAll(WargameFacade.getInstance().getArmyTwoFromDemoFile());
    } catch (IOException e) {
      showErrorMessage(e.getMessage());
    }
    updateArmyNamesLabels();
    updateArmyUnits();
  }
}
