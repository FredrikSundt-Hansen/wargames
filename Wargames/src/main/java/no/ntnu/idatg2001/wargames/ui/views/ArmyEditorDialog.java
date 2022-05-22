package no.ntnu.idatg2001.wargames.ui.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import no.ntnu.idatg2001.wargames.ui.controllers.ArmyEditorController;

/**
 * Dialog view for the that returns a list of units made by the use in ArmyEditorDialogView
 * (ArmyEditorController). This is being used to have a modality view, and can then use
 * the custom dialog, ArmyEditorDialogView to make units
 * instead of making one manually in this class.
 */
public class ArmyEditorDialog extends Dialog<List<String>> implements Initializable {

  private List<String> unitValues = new ArrayList<>();

  /**
   * Constructor for the dialog. Adds an apply button and calls showArmyEditor to load the view.
   */
  public ArmyEditorDialog(String armyName) {
    super();
    getDialogPane().getButtonTypes().add(ButtonType.APPLY);
    showArmyEditor(armyName);
  }

  /**
   * Method to show ArmyEditorView in this dialog. Will also setResultConverter
   * (what the dialog returns) with values from the controller.
   */
  @FXML
  public void showArmyEditor(String armyName) {
    FXMLLoader loader =
        new FXMLLoader(ArmyEditorDialog.
            class.getClassLoader().getResource("no.ntnu.idatg2001.wargames.ui.views/ArmyEditorDialogView.fxml"));
    try {
      getDialogPane().setContent(loader.load());
    } catch (IOException e) {
      showErrorMessage(e.getMessage());
    }
    ArmyEditorController controller = loader.getController();
    controller.setArmyNameLabel(armyName);

    setResultConverter(
        buttonType -> {
          if (buttonType == ButtonType.APPLY) {
            unitValues = controller.getUnitList();
            if (unitValues.get(0) != null && unitValues.get(1) != null) {
              return unitValues;
            } else {
              return null;
            }
          }
          return unitValues;
        });
  }

  /**
   * Method to show an error message as an alert box.
   * @param e The error message to show.
   */
  private void showErrorMessage(String e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error not a valid input");
    alert.setHeaderText(e);
    alert.setContentText("Please try again");
    alert.showAndWait();
  }

  /**
   * The class implements to Initializable interface to work with JavaFX.
   * However the view is loaded in showArmyEditorAdd, and nothing else is iniziated.
   * Hence this method is required, but is empty.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }



}
