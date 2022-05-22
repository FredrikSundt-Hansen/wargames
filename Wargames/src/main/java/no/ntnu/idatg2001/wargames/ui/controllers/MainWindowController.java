package no.ntnu.idatg2001.wargames.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.ui.views.WargamesApplication;

public class MainWindowController implements Initializable {

  @FXML
  private TextField textFieldArmyOne;
  @FXML
  private TextField textFieldArmyTwo;

  /**
   * Class implements Initializable interface to be used by FXML loader.
   * But does not have anything to initialise.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }

  @FXML
  private void onNewBattleButtonClick() {
    WargamesApplication.goToSetArmyNames();
  }

  /**
   * Shows battle maker after setting the new army names.
   * Army names does not have to be set, and be set later in the application.
   */
  @FXML
  private void onGoToBattleMakerButtonClick() {
    try {
      WargameFacade.getInstance().setArmyOneName(textFieldArmyOne.getText());
      WargameFacade.getInstance().setArmyTwoName(textFieldArmyTwo.getText());
    } catch (IllegalArgumentException ignored) {}
    WargamesApplication.goToBattleMaker();
  }

  @FXML
  private void onGoToMainMenuButtonClick() {
    WargamesApplication.gotToMainMenu();
  }
}
