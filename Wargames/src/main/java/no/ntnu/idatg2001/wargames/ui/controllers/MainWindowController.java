package no.ntnu.idatg2001.wargames.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargames.ui.views.WargamesApplication;

public class MainWindowController implements Initializable {
  @FXML
  private Button buttonNewBattle;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }

  public void onMouseDragOverLogo(MouseEvent mouseDragEvent) {

  }

  public void onNewBattleButtonClick(MouseEvent mouseEvent) throws IOException {
    WargamesApplication.goToBattleMaker();

  }
}
