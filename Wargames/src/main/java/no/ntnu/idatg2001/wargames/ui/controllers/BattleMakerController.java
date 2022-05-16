package no.ntnu.idatg2001.wargames.ui.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class BattleMakerController implements Initializable {

  private Image plains;
  private Image hills;
  private Image forest;

  @FXML
  private TableView tableViewArmyOne;
  @FXML
  private TableColumn tableColumnTypeOne;
  @FXML
  private TableColumn tableColumnNameOne;
  @FXML
  private TableColumn tableColumnHealthOne;
  @FXML
  private TableColumn tableColumnArmorOne;
  @FXML
  private TableColumn tableColumnAttackOne;
  @FXML
  private TableView tableViewArmyTwo;
  @FXML
  private TableColumn tableColumnTypeTwo;
  @FXML
  private TableColumn tableColumnNameTwo;
  @FXML
  private TableColumn tableColumnHealthTwo;
  @FXML
  private TableColumn tableColumnArmorTwo;
  @FXML
  private TableColumn tableColumnAttackTwo;
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

  }

  private void setImage(String terrain) {
    if (terrain.equalsIgnoreCase(getTerrainValueFromImage(plains))) {
      setImageViewTerrain(plains);
    } else if (terrain.equalsIgnoreCase(getTerrainValueFromImage(hills))) {
      setImageViewTerrain(hills);
    } else if (terrain.equalsIgnoreCase(getTerrainValueFromImage(forest))) {
      setImageViewTerrain(forest);
    }
  }

  private String getTerrainValueFromImage(Image image) {
    return image.getUrl().split("/")[14].split("\\.")[0].split("_")[0];
  }

  public void onSwitchTerrainMouseClick(MouseEvent mouseEvent) {
    switchTerrain();
  }

  public void onSwitchTerrainButtonClick(ActionEvent actionEvent) {
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

  public void onAddUnitsArmyButtonClick(ActionEvent actionEvent) {
  }

  public void onAddUnitsArmyOneButtonClick(ActionEvent actionEvent) {
  }

  public void onAddUnitsArmyTwoButtonClick(ActionEvent actionEvent) {
  }

  public void onResetArmyOneButtonClick(ActionEvent actionEvent) {
  }

  public void onResetArmyTwoButtonClick(ActionEvent actionEvent) {
  }

  public void onResetArmiesClick(ActionEvent actionEvent) {
  }

  public void onSimulateButtonClick(ActionEvent actionEvent) {
  }

  public void onSaveArmiesButtonClick(ActionEvent actionEvent) {
  }

  public void onLoadPreviousSaveButtonClick(ActionEvent actionEvent) {
  }

  public void onLoadFromFileButtonClick(ActionEvent actionEvent) {
  }
}
