package no.ntnu.idatg2001.wargames.ui.views;

import static javafx.application.Application.launch;

import java.io.IOException;
import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    FXMLLoader loader =
        new FXMLLoader(getClass().getClassLoader().getResource("no.ntnu.idatg2001.wargames.ui.views/MainWindowView.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    WargamesApplication.primaryStage = primaryStage;
    primaryStage.setTitle("Wargames");
    primaryStage.show();
  }

  public static void goToBattleMaker() throws IOException {
    FXMLLoader loader =
        new FXMLLoader(WargamesApplication.class.getClassLoader().getResource("no.ntnu.idatg2001.wargames.ui.views/BattleMakerView.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    WargamesApplication.primaryStage.setScene(scene);
    WargamesApplication.primaryStage.setTitle("Wargames");
    WargamesApplication.primaryStage.show();
  }
}
