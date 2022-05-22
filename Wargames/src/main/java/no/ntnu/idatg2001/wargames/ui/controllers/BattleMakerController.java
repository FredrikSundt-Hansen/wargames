package no.ntnu.idatg2001.wargames.ui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.ui.views.WargamesApplication;

/**
 * The Controller for the ArmyMakerView. Controls the user interaction from the gui in
 * ArmyMakerView, and the dialog ArmyEditorDialog. It hols the tableview data, as well as the
 * terrain image data. It shows the current terrain for the battle. The terrain for simulating is
 * then selected based on the url of the image currently showing.
 * Additionally, it has function save armies, load previous one
 * and load an army from file.
 */
public class BattleMakerController implements Initializable {


  private Image plains;
  private Image hills;
  private Image forest;

  private ArmyMakerController armyOneController;
  private ArmyMakerController armyTwoController;

  @FXML private Label armyTwoFilePath;
  @FXML private Label armyOneFilePath;
  @FXML private Pane armyOnePane;
  @FXML private Pane armyTwoPane;
  @FXML private ImageView terrainImageView;
  @FXML private ChoiceBox<String> terrainChoiceBox;
  @FXML private Label currentTerrainLabel;
  @FXML private Label filePathLabel;

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

    FXMLLoader loaderArmyOne = new FXMLLoader(BattleMakerController.class.getClassLoader()
                .getResource("no.ntnu.idatg2001.wargames.ui.views/ArmyMakerView.fxml"));
    try {
      armyOnePane.getChildren().add(loaderArmyOne.load());
      armyOneController = loaderArmyOne.getController();
      armyOneController.setArmyOne(true);
      armyOneController.setArmyName(WargameFacade.getInstance().getArmyOneName());
    } catch (IOException e) {
      e.printStackTrace();
    }

    FXMLLoader loaderArmyTwo = new FXMLLoader(BattleMakerController.class.getClassLoader()
                .getResource("no.ntnu.idatg2001.wargames.ui.views/ArmyMakerView.fxml"));
    try {
      armyTwoPane.getChildren().add(loaderArmyTwo.load());
      armyTwoController = loaderArmyTwo.getController();
      armyTwoController.setArmyName(WargameFacade.getInstance().getArmyTwoName());
    } catch (IOException e) {
      e.printStackTrace();
    }

    armyOneFilePath.setVisible(false);
    armyTwoFilePath.setVisible(false);
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

  /**
   * Method to show Simulate view.
   * If army one unit is not empty, and army two units is not empty.
   * Updated units and name to the armies.
   */
  @FXML
  private void onSimulateButtonClick() {
    if (!WargameFacade.getInstance().getArmyOneUnits().isEmpty() && !WargameFacade.getInstance().getArmyTwoUnits().isEmpty()) {
      WargameFacade instance  = WargameFacade.getInstance();
    instance.setArmyOneName(armyOneController.getArmyName());
    instance.setArmyTwoName(armyTwoController.getArmyName());
    instance.setUnits(armyOneController.getUnitList(), armyOneController.getArmyName());
    instance.setUnits(armyTwoController.getUnitList(), armyTwoController.getArmyName());

      instance.setBattle();
      instance.setCurrentTerrain(getTerrainValueFromImage(terrainImageView.getImage()));
      WargamesApplication.getToSimulateBattle();
    } else {
      showErrorMessage("Both armies need to have units in them.");
    }
  }

  private void setFilePath(boolean armyOne) {
    List<String> fileInfo = WargameFacade.getInstance().getLastLoadedFileInfo();
      if (armyOne) {
        armyOneFilePath.setVisible(true);
        armyOneFilePath.setText("Army " + "'" + fileInfo.get(0) + "'" + " loaded from :" +
            fileInfo.get(1) + " \nFile name : " + fileInfo.get(2));
      } else {
        armyTwoFilePath.setVisible(true);
        armyTwoFilePath.setText("Army " + fileInfo.get(0) + " loaded from :" +
            fileInfo.get(1) + " \nFile name : " + fileInfo.get(2));
      }
  }

  /** Saves army one to resources file.*/
  @FXML
  private void onSaveArmyOneButtonClick() {
    try {
      WargameFacade.getInstance().setArmyOneName(armyOneController.getArmyName());
      WargameFacade.getInstance().setUnits(armyOneController.getUnitList(), armyOneController.getArmyName());
      WargameFacade.getInstance().saveArmyOneToResources();
    } catch (IOException | IllegalArgumentException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /** Saves army two to resources file.*/
  @FXML
  private void onSaveArmyTwoButtonClick() {
    try {
      WargameFacade.getInstance().setArmyTwoName(armyTwoController.getArmyName());
      WargameFacade.getInstance().setUnits(armyTwoController.getUnitList(), armyTwoController.getArmyName());
      WargameFacade.getInstance().saveArmyTwoToResources();
    } catch (IOException | IllegalArgumentException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**Loads previous army one.*/
  @FXML
  private void onLoadPreviousArmyOneButtonClick() {
    try {
      armyOneController.setUnitList(WargameFacade.getInstance().getArmyOneFromResources());
      armyOneController.setArmyName(WargameFacade.getInstance().getArmyOneName());
      armyOneController.updateArmyUnits();
      setFilePath(true);
    } catch (IOException | IllegalArgumentException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**Loads previous army two.*/
  @FXML
  private void onLoadPreviousArmyTwoButtonClick() {
    try {
      armyTwoController.setUnitList(WargameFacade.getInstance().getArmyTwoFromResources());
      armyTwoController.setArmyName(WargameFacade.getInstance().getArmyTwoName());
      armyTwoController.updateArmyUnits();
      setFilePath(false);
    } catch (IOException | IllegalArgumentException e) {
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
      armyOneController.setUnitList(WargameFacade.getInstance().getArmyOneFromDemoFile());
      armyOneController.setArmyName(WargameFacade.getInstance().getArmyOneName());
      armyOneController.updateArmyUnits();
      setFilePath(true);

      armyTwoController.setUnitList(WargameFacade.getInstance().getArmyTwoFromDemoFile());
      armyTwoController.setArmyName(WargameFacade.getInstance().getArmyTwoName());
      armyTwoController.updateArmyUnits();
      setFilePath(false);
    } catch (IOException | IllegalArgumentException e) {
      showErrorMessage(e.getMessage());
    }
  }

  @FXML
  private void onLoadArmyOneFromFile() {
    showFileChooser(armyOneController);
    setFilePath(true);
  }

  @FXML
  private void onLoadArmyTwoFromFile() {
    showFileChooser(armyTwoController);
    setFilePath(false);
  }

  /**
   * Method to show a File-chooser dialog, and uses the armyController to set the list and name of
   * the new army loaded from file.
   *
   * @param armyController The armyController to use.
   */
  private void showFileChooser(ArmyMakerController armyController) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Load an army from a csv file.");
    File army = fileChooser.showOpenDialog(null);
    try {
      armyController.setUnitList(WargameFacade.getInstance().getArmyOneFromFile(army.getAbsolutePath()));
      armyController.setArmyName(WargameFacade.getInstance().getArmyOneName());
      armyController.updateArmyUnits();
    } catch (IOException | IllegalArgumentException e) {
      showErrorMessage(e.getMessage());
    }
  }


}
