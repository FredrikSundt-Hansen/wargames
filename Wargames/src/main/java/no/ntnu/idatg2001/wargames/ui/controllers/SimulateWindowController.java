package no.ntnu.idatg2001.wargames.ui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import no.ntnu.idatg2001.wargames.model.UnitObserver;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.ui.views.WargamesApplication;

public class SimulateWindowController implements UnitObserver, Initializable {


  @FXML
  private ScrollPane hitUpdateScrollView;
  @FXML
  private Spinner<Integer> threadSpeedSpinner;
  private XYChart.Series<String, Number> unitsArmy1Chart;
  private XYChart.Series<String, Number> unitsArmy2Chart;
  private Timeline timeline;


  private int armyOneSize;
  private int armyTwoSize;
  private int threadSpeed;
  private int counter;

  private String nameAttacker;
  private int attackValue;
  private int attackBonus;
  private String nameDefender;
  private int healthDefender;
  private int resistDefender;
  private boolean simulating;

  @FXML
  private Label armyTwoSizeLabel;
  @FXML
  private Label armyOneSizeLabel;
  @FXML private Label currentTerrainLabel;
  @FXML private VBox vBoxHitUpdate;
  @FXML private LineChart<String, Number> armySizeLineChart;
  @FXML private TableView<Unit> tableViewArmyTwo;
  @FXML private TableView<Unit> tableViewArmyOne;
  @FXML private TableColumn<Unit, String> columnTypeArmyOne;
  @FXML private TableColumn<Unit, String> columnHealthArmyOne;
  @FXML private TableColumn<Unit, String> columTypeArmyTwo;
  @FXML private TableColumn<Unit, String> columnHealthArmyTwo;
  @FXML private Spinner<Integer> amountOfSimulationSpinner;
  @FXML private ImageView currentTerrainImageView;

  @Override
  public void hitUpdate(String nameAttacker, int attackValue, int attackBonus, String nameDefender,
      int healthDefender, int resistDefender) {
    this.nameAttacker = nameAttacker;
    this.attackValue = attackValue;
    this.attackBonus = attackBonus;
    this.nameDefender = nameDefender;
    this.healthDefender = healthDefender;
    this.resistDefender = resistDefender;

  }

  @Override
  public void sizeUpdate(int sizeOne, int sizeTwo) {
    this.armyOneSize = sizeOne;
    this.armyTwoSize = sizeTwo;

    System.out.println(sizeOne);
    System.out.println(sizeTwo);
  }

  private void step(ActionEvent actionEvent) {
    if (WargameFacade.getInstance().simulateStep()) {
      timeline.stop();
    }

    unitsArmy1Chart.getData().add(new XYChart.Data<>(String.valueOf(counter), armyOneSize));
    unitsArmy2Chart.getData().add(new XYChart.Data<>(String.valueOf(counter), armyTwoSize));
    counter++;

    loadHitBox(nameAttacker, attackValue, attackBonus, nameDefender, healthDefender, resistDefender);

    tableViewArmyOne.refresh();
    tableViewArmyTwo.refresh();
    updateArmySizeLabels();


  }

  private void loadHitBox(String nameAttacker, int attackValue, int attackBonus,
                          String nameDefender, int healthDefender, int resistDefender) {
    FXMLLoader loader =
        new FXMLLoader(
            WargamesApplication.class
                .getClassLoader()
                .getResource("no.ntnu.idatg2001.wargames.ui.views/HitUpdateBoxView.fxml"));
    Parent node;
    try {
      node = loader.load();
      HitUpdateBoxController controller = loader.getController();
      vBoxHitUpdate.getChildren().add(node);
      controller.setValues(
          nameAttacker, attackValue, attackBonus, nameDefender, healthDefender, resistDefender);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void setTableView(
      TableColumn<Unit, String> columnType, TableColumn<Unit, String> columnHealth) {

    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnHealth.setCellValueFactory(new PropertyValueFactory<>("health"));

  }

  private void setCurrentTerrainImageView() {
    String terrain = WargameFacade.getInstance().getCurrentTerrain();
    currentTerrainLabel.setText(terrain);
    currentTerrainImageView.setImage(new Image(
        new File("src/main/resources/images/" +
            terrain+"_Image.jpg").toURI().toString()));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    ObservableList<Unit> unitObservableListArmyOne =
        FXCollections.observableList(WargameFacade.getInstance().getArmyOneUnits());
    tableViewArmyOne.setItems(unitObservableListArmyOne);

    ObservableList<Unit> unitObservableListArmyTwo =
        FXCollections.observableList(WargameFacade.getInstance().getArmyTwoUnits());
    tableViewArmyTwo.setItems(unitObservableListArmyTwo);

    setTableView(columnTypeArmyOne, columnHealthArmyOne);
    setTableView(columTypeArmyTwo, columnHealthArmyTwo);
    WargameFacade.getInstance().registerObserver(this);

    unitsArmy1Chart = new XYChart.Series<>();
    unitsArmy2Chart = new XYChart.Series<>();
    armySizeLineChart.getData().addAll(unitsArmy1Chart, unitsArmy2Chart);

    armySizeLineChart.getXAxis().setTickLabelsVisible(false);
    armySizeLineChart.setCreateSymbols(false);
    armySizeLineChart.setHorizontalGridLinesVisible(false);
    armySizeLineChart.setVerticalGridLinesVisible(false);

    unitsArmy1Chart.setName(WargameFacade.getInstance().getArmyOneName());
    unitsArmy2Chart.setName(WargameFacade.getInstance().getArmyTwoName());

    threadSpeed = 80;

    SpinnerValueFactory<Integer> spinnerValueFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 1000, threadSpeed, 10);
    threadSpeedSpinner.setValueFactory(spinnerValueFactory);

    threadSpeedSpinner.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        threadSpeedSpinner.getEditor().setText(oldValue);
      }});
    threadSpeedSpinner.valueProperty().addListener(((observableValue, oldValue, newValue) ->
        threadSpeed = newValue));

    updateArmySizeLabels();
    setCurrentTerrainImageView();
  }

  private void updateArmySizeLabels() {
    armyOneSizeLabel.setText(String.valueOf(tableViewArmyOne.getItems().size()));
    armyTwoSizeLabel.setText(String.valueOf(tableViewArmyTwo.getItems().size()));
  }


  public void onStartSimulationButtonClick() {
    timeline = new Timeline(new KeyFrame(Duration.millis(threadSpeed), this::step));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  @FXML
  private void onShowMainMenuButtonClick() {
    WargamesApplication.gotToMainMenu();
  }

  @FXML
  private void onShowArmyMakerButtonClick() {
    WargamesApplication.goToBattleMaker();
  }
}
