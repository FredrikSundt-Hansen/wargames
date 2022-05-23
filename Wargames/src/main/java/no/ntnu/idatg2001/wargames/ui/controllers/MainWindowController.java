package no.ntnu.idatg2001.wargames.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.ui.views.WargamesApplication;

/**
 * The main window of the application. The user can either quit the application,
 * or click on new battle to make armies.
 */
public class MainWindowController implements Initializable {

  @FXML private TextField textFieldArmyOne;
  @FXML private TextField textFieldArmyTwo;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // Class implements Initializable interface to be used by FXML loader.
    // But does not have anything to initialise.
  }

  /**Shows the window for setting army names for the new battle.*/
  @FXML
  private void onNewBattleButtonClick() {
    WargamesApplication.goToSetArmyNames();
  }

  /**
   * Shows battle maker after setting the new army names.
   * Army names does not have to be set, and can be set later in the application.
   */
  @FXML
  private void onGoToBattleMakerButtonClick() {
    try {
      WargameFacade.getInstance().setArmyName(textFieldArmyOne.getText(), true);
      WargameFacade.getInstance().setArmyName(textFieldArmyTwo.getText(), false);
    } catch (IllegalArgumentException ignored) {
      //Exception is ignored because the names can be set later.
    }

    WargamesApplication.goToBattleMaker();
  }

  @FXML
  private void onGoToMainMenuButtonClick() {
    WargamesApplication.gotToMainMenu();
  }
}
