package no.ntnu.idatg2001.wargames.ui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
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
 * then selected based on the url of the image currently showing. Additionally, it has function save
 * armies, load previous one and load an army from file.
 */
public class BattleMakerController implements Initializable {

  private Image plains;
  private Image hills;
  private Image forest;

  private ArmyMakerController armyOneController;

  private ArmyMakerController armyTwoController;

  @FXML
  private Pane armyOnePane;
  @FXML
  private Pane armyTwoPane;
  @FXML
  private ImageView terrainImageView;
  @FXML
  private ChoiceBox<String> terrainChoiceBox;

  /**
   * Initiates all terrain images, sets the terrainImageView to plains. Sets the values for terrain
   * choicebox. Initeates armyOneController and armyTwoController, loading them and adding them to
   * armyOne pane and armyTwo pane.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      String plainsURL = Objects.requireNonNull(WargamesApplication.class
              .getResource("/no/ntnu/idatg2001/wargames/ui/images/terrain/Plains_Image.jpg"))
          .toExternalForm();
      plains = new Image(plainsURL);

      String hillsURL = Objects.requireNonNull(WargamesApplication.class
              .getResource("/no/ntnu/idatg2001/wargames/ui/images/terrain/Hills_Image.jpg"))
          .toExternalForm();
      hills = new Image(hillsURL);

      String forestURL = Objects.requireNonNull(WargamesApplication.class
              .getResource("/no/ntnu/idatg2001/wargames/ui/images/terrain/Forest_Image.jpg"))
          .toExternalForm();
      forest = new Image(forestURL);
    } catch (NullPointerException e) {
      showErrorMessage("Image did not load properly, because the image location is wrong." +
          "You can ignore this message.");
    }

    if (plains != null && hills != null && forest != null) {
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
    }

    FXMLLoader loaderArmyOne =
        new FXMLLoader(WargamesApplication.class.getResource("ArmyMakerView.fxml"));
    try {
      armyOnePane.getChildren().add(loaderArmyOne.load());
      armyOneController = loaderArmyOne.getController();
      armyOneController.setArmyOne(true);
    } catch (IOException e) {
      e.printStackTrace();
    }

    FXMLLoader loaderArmyTwo =
        new FXMLLoader(WargamesApplication.class.getResource("ArmyMakerView.fxml"));
    try {
      armyTwoPane.getChildren().add(loaderArmyTwo.load());
      armyTwoController = loaderArmyTwo.getController();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (WargameFacade.getInstance().isSaveFileFilled()) {
      onLoadPreviousArmyOneButtonClick();
      onLoadPreviousArmyTwoButtonClick();
    }
  }

  /**
   * Method to get the string value associated with the terrain.
   *
   * @param image Image to get the terrain string value from.
   * @return Terrain value as a string.
   */
  private String getTerrainValueFromImage(Image image) {
    return image.getUrl()
        .split("/")[image.getUrl().split("/").length - 1]
        .split("\\.")[0].split("_")[0];
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
    if (terrainImage != null) {
      terrainImageView.setImage(terrainImage);
    }

  }

  /**
   * When user has clicked has decided to switch terrain image. Switches between terrain bases on
   * which image is currently showing.
   */
  @FXML
  private void onSwitchTerrainMouseClick() {
    if (plains != null && terrainImageView.getImage().getUrl().equals(plains.getUrl())) {
      setTerrainImageView(hills);
      terrainChoiceBox.setValue(getTerrainValueFromImage(hills));
    } else if (hills != null && terrainImageView.getImage().getUrl().equals(hills.getUrl())) {
      setTerrainImageView(forest);
      terrainChoiceBox.setValue(getTerrainValueFromImage(forest));
    } else if (forest != null && terrainImageView.getImage().getUrl().equals(forest.getUrl())) {
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

  private void showInfoMessage(String title, String message, String contentText) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(message);
    alert.setContentText(contentText);
    alert.showAndWait();
  }

  /**
   * Saves army one to resources file.
   */
  @FXML
  private void onSaveArmyOneButtonClick() {
    try {
      WargameFacade.getInstance()
          .saveArmyOneToResources(armyOneController.getArmyName(), armyOneController.getUnitList());
    } catch (IOException | IllegalArgumentException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**
   * Saves army two to resources file.
   */
  @FXML
  private void onSaveArmyTwoButtonClick() {
    try {
      WargameFacade.getInstance()
          .saveArmyTwoToResources(armyTwoController.getArmyName(), armyTwoController.getUnitList());
    } catch (IOException | IllegalArgumentException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**
   * Loads previous army one.
   */
  @FXML
  private void onLoadPreviousArmyOneButtonClick() {
    try {
      armyOneController.setArmyControllerValues(
          WargameFacade.getInstance().getArmyOneFromResources(),
          WargameFacade.getInstance().getArmyOneName());
      armyOneController.updateArmyUnits();
    } catch (IOException | NullPointerException | IllegalArgumentException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**
   * Loads previous army two.
   */
  @FXML
  private void onLoadPreviousArmyTwoButtonClick() {
    try {
      armyTwoController.setArmyControllerValues(
          WargameFacade.getInstance().getArmyTwoFromResources(),
          WargameFacade.getInstance().getArmyTwoName());
      armyTwoController.updateArmyUnits();
    } catch (IOException | NullPointerException | IllegalArgumentException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**
   * Loads a demo file that has 161 units on both armies, with names. Used to give the user an idea
   * on how to make an army.
   */
  @FXML
  private void onLoadDemoFileButtonClick() {
    try {
      armyOneController.setArmyControllerValues(
          WargameFacade.getInstance().getArmyOneFromDemoFile(),
          WargameFacade.getInstance().getArmyOneName());
      armyOneController.updateArmyUnits();

      armyTwoController.setArmyControllerValues(
          WargameFacade.getInstance().getArmyTwoFromDemoFile(),
          WargameFacade.getInstance().getArmyTwoName());
      armyTwoController.updateArmyUnits();
    } catch (IOException | NullPointerException | IllegalArgumentException e) {
      e.printStackTrace();
      showErrorMessage("Cannot find file from path: \n" + e.getMessage());
    }
  }

  @FXML
  private void onLoadArmyOneFromFile() {
    showFileChooser(armyOneController, true);
  }

  @FXML
  private void onLoadArmyTwoFromFile() {
    showFileChooser(armyTwoController, false);
  }

  /**
   * Method to show a File-chooser dialog, and uses the armyController to set the list and name of
   * the new army loaded from file.
   *
   * @param armyController The armyController to use.
   */
  private void showFileChooser(ArmyMakerController armyController, boolean armyOne) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Load an army from a csv file. " +
        "The files has to be inside 'resources' folders.");
    File army = fileChooser.showOpenDialog(null);
    try {
      if (armyOne) {
        armyController.setArmyControllerValues(
            WargameFacade.getInstance().getArmyFromFile(
                "/no/ntnu/idatg2001/wargames/savefiles/"+army.getName(), true),
            WargameFacade.getInstance().getArmyOneName());
      } else {
        armyController.setArmyControllerValues(
            WargameFacade.getInstance().getArmyFromFile(
                "/no/ntnu/idatg2001/wargames/savefiles/"+army.getName(), false),
            WargameFacade.getInstance().getArmyTwoName());
      }
      armyController.updateArmyUnits();
    } catch (IOException | IllegalArgumentException | NullPointerException e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**
   * Method to show Simulate view. If army one unit is not empty, and army two units is not empty.
   * Updated units and name to the armies.
   */
  @FXML
  private void onSimulateButtonClick() {
    WargameFacade instance = WargameFacade.getInstance();
    try {
      instance.checkValidArmies();
      instance.setUnitsNameArmyOneAndTwo(
          armyOneController.getUnitList(), armyTwoController.getUnitList());
      instance.setBattle(getTerrainValueFromImage(terrainImageView.getImage()));
      onSaveArmyOneButtonClick();
      onSaveArmyTwoButtonClick();
      instance.updateBackupArmies();
      WargamesApplication.getToSimulateBattle();
    } catch (IllegalArgumentException e) {
      showErrorMessage(e.getMessage());
    }
  }

  @FXML
  private void onShowMainMenuButtonClick() {
    WargamesApplication.gotToMainMenu();
  }


  @FXML
  private void onMenuItemArmyOneFileInfoButtonClick(ActionEvent actionEvent) {
    showInfoMessage("'" + armyOneController.getArmyName() + "'" + " from file : " +
            WargameFacade.getInstance().getArmyOneFileName(),
        "File path : " + WargameFacade.getInstance().getArmyOneFilePath(),
        "Total units : " + armyOneController.getUnitList().size());
  }

  @FXML
  private void onMenuItemArmyTwoFileInfoButtonClick(ActionEvent actionEvent) {
    showInfoMessage("'" + armyTwoController.getArmyName() + "'" + " from file : " +
            WargameFacade.getInstance().getArmyTwoFileName(),
        "File path : " + WargameFacade.getInstance().getArmyTwoFilePath(),
        "Total units : " + armyTwoController.getUnitList().size());
  }
}

