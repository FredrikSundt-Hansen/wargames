package no.ntnu.idatg2001.wargames.ui.views;

import static javafx.application.Application.launch;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WargamesApplication extends Application {

  private static Stage primaryStage;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    WargamesApplication.primaryStage = primaryStage;
    gotToMainMenu();
  }

  @FXML
  public static void gotToMainMenu() throws IOException {
    FXMLLoader loader =
        new FXMLLoader(WargamesApplication.class.getClassLoader().getResource("no.ntnu.idatg2001.wargames.ui.views/MainWindowView.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Main Menu");
    primaryStage.show();
  }

  @FXML
  public static void showSetArmyNames() throws IOException {
    FXMLLoader loader =
        new FXMLLoader(WargamesApplication.class.getClassLoader().getResource("no.ntnu.idatg2001.wargames.ui.views/SetArmyNamesView.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Set Army Names");
    primaryStage.show();
  }

  public static void goToBattleMaker() throws IOException {
    FXMLLoader loader =
        new FXMLLoader(WargamesApplication.class.getClassLoader().getResource("no.ntnu.idatg2001.wargames.ui.views/ArmyMakerView.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Battle Maker");
    primaryStage.show();
  }
}
