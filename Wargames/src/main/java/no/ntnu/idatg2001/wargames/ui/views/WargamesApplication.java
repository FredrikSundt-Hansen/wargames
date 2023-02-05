package no.ntnu.idatg2001.wargames.ui.views;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WargamesApplication extends Application {
  private static Stage primaryStage;

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Displays the main menu.
   */
  @FXML
  public static void gotToMainMenu() {
    FXMLLoader loader =
        new FXMLLoader(WargamesApplication.class.getResource("MainWindowView.fxml"));
    try {
      Parent root = loader.load();
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
    } catch (IOException e) {
      showErrorMessage(e.getMessage());
    }

    primaryStage.setTitle("Main Menu");
    primaryStage.show();
    String url = Objects.requireNonNull(WargamesApplication.class
            .getResource("/no/ntnu/idatg2001/wargames/ui/images/icons/appIcon_255x255.png"))
        .toExternalForm();
    primaryStage.getIcons().add(new Image(url));
  }

  /**
   * Displays the BattleMaker to make armies.
   */
  @FXML
  public static void goToBattleMaker() {
    FXMLLoader loader =
        new FXMLLoader(WargamesApplication.class.getResource("BattleMakerView.fxml"));
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
        new FXMLLoader(WargamesApplication.class.getResource("SimulationView.fxml"));
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

  /**
   * Start method from Application. Calls goToMainMenu.
   */
  @Override
  public void start(Stage primaryStage) {
    WargamesApplication.primaryStage = primaryStage;
    gotToMainMenu();
  }

  public static void exitApplication() {
    Platform.exit();
    System.exit(0);
  }


}
