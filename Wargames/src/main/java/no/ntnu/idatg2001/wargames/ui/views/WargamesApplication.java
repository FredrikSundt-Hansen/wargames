package no.ntnu.idatg2001.wargames.ui.views;

import static javafx.application.Application.launch;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class WargamesApplication extends Application {
  private static Stage primaryStage;

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Start method from Application. Calls goToMainMenu.
   */
  @Override
  public void start(Stage primaryStage) {
    WargamesApplication.primaryStage = primaryStage;
    gotToMainMenu();
  }

  /**
   * Displays the main menu.
   */
  @FXML
  public static void gotToMainMenu() {
    FXMLLoader loader =
        new FXMLLoader(WargamesApplication.class.getClassLoader().getResource("no.ntnu.idatg2001.wargames.ui.views/MainWindowView.fxml"));
    try {
      Parent root = loader.load();
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
    } catch (IOException e) {
      showErrorMessage(e.getMessage());
    }

    primaryStage.setTitle("Main Menu");
    primaryStage.show();
  }

  /**
   * Displays the view to set army names.
   */
  @FXML
  public static void goToSetArmyNames() {
    FXMLLoader loader =
        new FXMLLoader(WargamesApplication.class.getClassLoader().getResource("no.ntnu.idatg2001.wargames.ui.views/SetArmyNamesView.fxml"));
    try {
      Parent root = loader.load();
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
    } catch (IOException e) {
      showErrorMessage(e.getMessage());
    }
    primaryStage.setTitle("Set Army Names");
    primaryStage.show();
  }

  /**
   * Displays the BattleMaker to make armies.
   */
  @FXML
  public static void goToBattleMaker() {
    FXMLLoader loader =
        new FXMLLoader(WargamesApplication.class.getClassLoader().getResource("no.ntnu.idatg2001.wargames.ui.views/BattleMakerView.fxml"));
    try {
      Parent root = loader.load();
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
    } catch (IOException e) {
      e.printStackTrace();
    }
    primaryStage.setTitle("Battle Maker");
    primaryStage.show();
  }

  /**
   * Displays the Simulator view.
   */
  @FXML
  public static void getToSimulateBattle() {
    FXMLLoader loader =
        new FXMLLoader(WargamesApplication.class.getClassLoader().getResource("no.ntnu.idatg2001.wargames.ui.views/SimulationView.fxml"));
    try {
      Parent root = loader.load();
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
    } catch (IOException e) {
      e.printStackTrace();
    }
    primaryStage.setTitle("Battle Maker");
    primaryStage.show();
  }

  /**
   * Method to show an alert error message.
   *
   * @param e The message to show in the alert box.
   */
  private static void showErrorMessage(String e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error not a valid input");
    alert.setHeaderText(e);
    alert.setContentText("Please try again");
    alert.showAndWait();
  }


}
