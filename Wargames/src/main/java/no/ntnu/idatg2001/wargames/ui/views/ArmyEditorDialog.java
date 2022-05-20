package no.ntnu.idatg2001.wargames.ui.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.ui.controllers.ArmyEditorController;


public class ArmyEditorDialog extends Dialog<List<Unit>> implements Initializable{

  private List<Unit> unitList = new ArrayList<>();
  private String currentArmy;
  private boolean validInput;

  public ArmyEditorDialog() throws IOException {
    super();
    getDialogPane().getScene().getWindow().setOnCloseRequest(windowEvent -> close());
    getDialogPane().getButtonTypes().add(ButtonType.APPLY);
    final Button applyButton = (Button) getDialogPane().lookupButton(ButtonType.APPLY);
    applyButton.addEventFilter(
        ActionEvent.ACTION,
        event -> {
          if (!validInput) {
            event.consume();
          }
        });

    getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    showArmyEditorAdd();
  }

  /**
   * Method to show ArmyEditorView in this dialog. Will also setResultConverter
   * (what the dialog returns) with from the values in the controller.
   * @throws IOException - If FXML loader does not load properly.
   */
  @FXML
  public void showArmyEditorAdd() throws IOException {
    FXMLLoader loader =
        new FXMLLoader(ArmyEditorDialog.
            class.getClassLoader().getResource("no.ntnu.idatg2001.wargames.ui.views/ArmyEditorDialogView.fxml"));
    getDialogPane().setContent(loader.load());
    ArmyEditorController controller = loader.getController();

    setResultConverter(
          buttonType -> {
            if (buttonType == ButtonType.APPLY) {
              unitList = controller.getUnitList();
              currentArmy = controller.getArmyFromChoiceBox();
              validInput = controller.validInput();
            }
            return unitList;
          });

  }

  public String getCurrentArmy() {
    return currentArmy;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }



}
