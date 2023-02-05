package no.ntnu.idatg2001.wargames.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.ui.views.WargamesApplication;

/**
 * The main window of the application. The user can either quit the application,
 * or click on new battle to make armies.
 */
public class MainWindowController implements Initializable {

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // Class implements Initializable interface to be used by FXML loader.
    // But does not have anything to initialise.
  }

  /**Shows the window for setting army names for the new battle.*/
  @FXML
  private void onNewBattleButtonClick() {
    WargameFacade.getInstance().reset();
    WargamesApplication.goToBattleMaker();
  }

  @FXML
  private void onExitButtonClick(ActionEvent actionEvent) {
    WargamesApplication.exitApplication();
  }
}
