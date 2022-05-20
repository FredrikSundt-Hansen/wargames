package no.ntnu.idatg2001.wargames.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.ui.views.WargamesApplication;

public class MainWindowController implements Initializable {

  @FXML
  private TextField textFieldArmyOne;
  @FXML
  private TextField textFieldArmyTwo;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  public void onMouseDragOverLogo(MouseEvent mouseDragEvent) {

  }

  public void onNewBattleButtonClick(ActionEvent actionEvent) throws IOException {
    WargamesApplication.showSetArmyNames();
  }

  public void onGoToBattleMakerButtonClick(ActionEvent actionEvent) throws IOException {
    try {
      WargameFacade.getInstance().setArmyOneName(textFieldArmyOne.getText());
      WargameFacade.getInstance().setArmyTwoName(textFieldArmyTwo.getText());

    } catch (IllegalArgumentException ignored) {

    }
    WargamesApplication.goToBattleMaker();
  }

  public void onGoToMainMenuButtonClick(ActionEvent actionEvent) throws IOException {
    WargamesApplication.gotToMainMenu();
  }
}
