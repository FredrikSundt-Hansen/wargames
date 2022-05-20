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

/**
 * The Controller for the ArmyMakerView. Controls the user interaction from the gui in ArmyMakerView,
 * and the dialog ArmyEditorDialog. It hols the tableview data, as well as the terrain image data.
 */
public class ArmyMakerController implements Initializable {

  private Image plains;
  private Image hills;
  private Image forest;

  private List<Unit> unitListArmyOne;
  private List<Unit> unitListArmyTwo;

  @FXML
  private TableView<Unit> tableViewArmyOne;
  @FXML
  private TableColumn<Unit, String> columnTypeArmyOne;
  @FXML
  private TableColumn<Unit, String>  columnNameArmyOne;
  @FXML
  private TableColumn<Unit, String>  columnHealthArmyOne;
  @FXML
  private TableColumn<Unit, String>  columnArmorArmyOne;
  @FXML
  private TableColumn<Unit, String>  columnAttackArmyOne;
  @FXML
  private TableView<Unit> tableViewArmyTwo;
  @FXML
  private TableColumn<Unit, String>  columnTypeArmyTwo;
  @FXML
  private TableColumn<Unit, String>  columnNameArmyTwo;
  @FXML
  private TableColumn<Unit, String>  columnHealthArmyTwo;
  @FXML
  private TableColumn<Unit, String>  columnArmorArmyTwo;
  @FXML
  private TableColumn<Unit, String>  columnAttackArmyTwo;
  @FXML
  private Label labelArmyOneName;
  @FXML
  private Label labelArmyTwoName;
  @FXML
  private ImageView imageViewTerrain;
  @FXML
  private ChoiceBox<String> choiceBoxTerrains;
  @FXML
  private Label labelCurrentTerrain;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    plains = new Image(new File("src/main/resources/images/Plains_Image.jpg").toURI().toString());
    hills = new Image(new File("src/main/resources/images/Hills_Image.jpg").toURI().toString());
    forest = new Image(new File("src/main/resources/images/Forest_Image.jpg").toURI().toString());
    imageViewTerrain.setImage(plains);

    List<String> differentTerrains = new ArrayList<>();

    String stringPlains = getTerrainValueFromImage(this.plains);
    String stringHills = getTerrainValueFromImage(this.hills);
    String stringForest = getTerrainValueFromImage(this.forest);

    differentTerrains.add(stringPlains);
    differentTerrains.add(stringHills);
    differentTerrains.add(stringForest);

    choiceBoxTerrains.setItems(FXCollections.observableList(differentTerrains));
    choiceBoxTerrains.setValue(stringPlains);

    choiceBoxTerrains.onActionProperty()
        .setValue(actionEvent -> setImage(choiceBoxTerrains.getValue()));

    setLabelArmyOneName(WargameFacade.getInstance().getArmyOneName());
    setLabelArmyTwoName(WargameFacade.getInstance().getArmyTwoName());

    ObservableList<Unit> unitObservableListArmyOne =
        FXCollections.observableList(WargameFacade.getInstance().getArmyOneUnits());
    unitListArmyOne = unitObservableListArmyOne;
    tableViewArmyOne.setItems(unitObservableListArmyOne);
    tableViewArmyOne.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    setTableView(
        columnTypeArmyOne,
        columnNameArmyOne,
        columnHealthArmyOne,
        columnAttackArmyOne,
        columnArmorArmyOne);

    ObservableList<Unit> unitObservableListArmyTwo =
        FXCollections.observableList(WargameFacade.getInstance().getArmyTwoUnits());
    unitListArmyTwo = unitObservableListArmyTwo;
    tableViewArmyTwo.setItems(unitObservableListArmyTwo);
    tableViewArmyTwo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    setTableView(
        columnTypeArmyTwo,
        columnNameArmyTwo,
        columnHealthArmyTwo,
        columnAttackArmyTwo,
        columnArmorArmyTwo);
  }

  /**
   * Method to set the correct value for the all the columns.
   * @param columnType The column for the unit type.
   * @param columnName The column for the unit name.
   * @param columnHealth The column for the  unit health value.
   * @param columnAttack The column for the  unit attack value.
   * @param columnArmor The column for the  unit armor value.
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
   * Method to set the image of terrain in the middle of the view.
   * Will switch image depending on which image is currently showing.
   * @param terrain The terrain as a string.
   */
  private void setImage(String terrain) {
    if (terrain.equalsIgnoreCase(getTerrainValueFromImage(plains))) {
      setImageViewTerrain(plains);
    } else if (terrain.equalsIgnoreCase(getTerrainValueFromImage(hills))) {
      setImageViewTerrain(hills);
    } else if (terrain.equalsIgnoreCase(getTerrainValueFromImage(forest))) {
      setImageViewTerrain(forest);
    }
  }

  /**
   * Method to get the string value associated with the terrain.
   * @param image Image to get the terrain string value from.
   * @return Terrain value as a string.
   */
  private String getTerrainValueFromImage(Image image) {
    return image.getUrl().split("/")[14].split("\\.")[0].split("_")[0];
  }

  public void onSwitchTerrainMouseClick() {
    switchTerrain();
  }

  public void onSwitchTerrainButtonClick() {
    switchTerrain();
  }

  private void setImageViewTerrain(Image terrainImage) {
    imageViewTerrain.setImage(terrainImage);
    labelCurrentTerrain.setVisible(true);
    labelCurrentTerrain.setText(getTerrainValueFromImage(terrainImage));

  }

  private void switchTerrain() {
    if (imageViewTerrain.getImage().getUrl().equals(plains.getUrl())) {
      setImageViewTerrain(hills);
      choiceBoxTerrains.setValue(getTerrainValueFromImage(hills));
    } else if (imageViewTerrain.getImage().getUrl().equals(hills.getUrl())) {
      setImageViewTerrain(forest);
      choiceBoxTerrains.setValue(getTerrainValueFromImage(forest));
    } else if (imageViewTerrain.getImage().getUrl().equals(forest.getUrl())) {
      setImageViewTerrain(plains);
      choiceBoxTerrains.setValue(getTerrainValueFromImage(plains));
    }
  }

  public void onAddUnitsArmyOneButtonClick() throws IOException {
    showDialogEditArmy();
  }

  public void onAddUnitsArmyTwoButtonClick() throws IOException {
    showDialogEditArmy();
  }

  /**
   * Method to remove all selected rows form the tableviewArmyOne when remove button is pressed.
   */
  public void onRemoveUnitsArmyOneButtonClick() {
    ObservableList<Unit> selectedRows = tableViewArmyOne.getSelectionModel().getSelectedItems();
    ArrayList<Unit> rows = new ArrayList<>(selectedRows);
    if (showAlertDelete(rows.size(), labelArmyOneName.getText())) {
      rows.forEach(row -> tableViewArmyOne.getItems().remove(row));
    }
  }

  /**
   * Method to remove all selected rows form the tableviewArmyTwo when remove button is pressed.
   */
  public void onRemoveUnitsArmyTwoButtonClick() {
    ObservableList<Unit> selectedRows = tableViewArmyTwo.getSelectionModel().getSelectedItems();
    ArrayList<Unit> rows = new ArrayList<>(selectedRows);
    if (showAlertDelete(rows.size(), labelArmyTwoName.getText())) {
      rows.forEach(row -> tableViewArmyTwo.getItems().remove(row));
    }
  }

  public void onResetArmyOneButtonClick() {
    if (showAlertDelete(tableViewArmyOne.getItems().size(), labelArmyOneName.getText())) {
      tableViewArmyOne.getItems().clear();
    }
  }

  public void onResetArmyTwoButtonClick() {
    if (showAlertDelete(tableViewArmyTwo.getItems().size(), labelArmyTwoName.getText())) {
      tableViewArmyTwo.getItems().clear();
    }
  }

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
   * Method to show the dialog to add units to the army.
   * @throws IOException
   * @throws IllegalArgumentException
   */
  private void showDialogEditArmy() throws IOException, IllegalArgumentException {
    if (labelArmyOneName.getText() != null || labelArmyTwoName.getText() != null) {
      ArmyEditorDialog dialog = new ArmyEditorDialog();
      Optional<List<Unit>> result = dialog.showAndWait();
      String army =  dialog.getCurrentArmy();
      if (result.isPresent() && army != null) {
        List<Unit> resultList = result.get();
        if (!resultList.isEmpty()) {
          if (army.equals(labelArmyOneName.getText())) {
            unitListArmyOne.addAll(resultList);
          } else if (army.equals(labelArmyTwoName.getText())) {
            unitListArmyTwo.addAll(resultList);
          }
        } else {
          showAlertFromInputData("InvalidInput");
        }
      }
    } else {
      throw new IllegalArgumentException("Need to have a name on at least on army");
    }
  }

  private void showAlertFromInputData(String e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error not a valid input");
    alert.setHeaderText(e);
    alert.setContentText("Please try again");
    alert.showAndWait();
  }


  public void onChangeArmyOneNameMouseClick() {
    String name = showTextInputDialog("Name of the left Army");
    if (name != null && !name.isEmpty()) {
      WargameFacade.getInstance().setArmyOneName(name);
      setLabelArmyOneName(name);
    }
  }

  public void onChangeArmyTwoNameMouseClick() {
    String name = showTextInputDialog("Name of the right Army");
    if (name != null && !name.isEmpty()) {
      WargameFacade.getInstance().setArmyTwoName(name);
      setLabelArmyTwoName(name);
    }
  }

  private String showTextInputDialog(String message) {
    TextInputDialog textInputDialog = new TextInputDialog("name");
    textInputDialog.setHeaderText("Name Editor");
    textInputDialog.setTitle(message);
    textInputDialog.getDialogPane().setContentText("Army : ");
    textInputDialog.showAndWait();
    TextField input = textInputDialog.getEditor();
    return input.getText();
  }

  private void setLabelArmyOneName(String name) {
    if (name != null && !name.equals(labelArmyTwoName.getText())) {
      labelArmyOneName.setText(name);
    }
  }

  private void setLabelArmyTwoName(String name) {
    if (name != null && !name.equals(labelArmyOneName.getText())) {
      labelArmyTwoName.setText(name);
    }
  }

  public void onResetArmiesClick() {
    onResetArmyOneButtonClick();
    onResetArmyTwoButtonClick();
  }

  public void onSimulateButtonClick() {
  }

  public void onSaveArmiesButtonClick() {
  }

  public void onLoadFromFileButtonClick() {
  }

  public void onLoadPreviousSaveButtonClick() {

  }



}
