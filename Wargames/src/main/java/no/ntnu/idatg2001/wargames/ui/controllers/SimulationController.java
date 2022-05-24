package no.ntnu.idatg2001.wargames.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
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
import no.ntnu.idatg2001.wargames.model.battles.UnitObserver;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.ui.views.WargamesApplication;

public class SimulationController implements UnitObserver, Initializable {

  private int simulationSize;
  private int counter;
  private int threadSpeed;

  private String nameAttacker;
  private int attackValue;
  private int attackBonus;
  private String nameDefender;
  private int healthDefender;
  private int resistDefender;
  private int armyOneSize;
  private int armyTwoSize;

  private Timeline timeline;
  private XYChart.Series<String, Number> armyOneChartData;
  private XYChart.Series<String, Number> armyTwoChartData;

  @FXML private LineChart<String, Number> armySizeLineChart;
  @FXML private PieChart simulationResultPieChart;
  @FXML private VBox vBoxHitUpdate;
  @FXML private ScrollPane hitUpdateScrollView;
  @FXML private Label armyOneNameLabel;
  @FXML private Label armyTwoNameLabel;
  @FXML private Label currentTerrainLabel;
  @FXML private TableView<Unit> armyOneTableView;
  @FXML private TableView<Unit> armyTwoTableView;
  @FXML private TableColumn<Unit, String> armyOneTypeColumn;
  @FXML private TableColumn<Unit, String> armyOneHealthColumn;
  @FXML private TableColumn<Unit, String> armyTwoTypeColumn;
  @FXML private TableColumn<Unit, String> armyTwoHealthColumn;
  @FXML private ImageView currentTerrainImageView;
  @FXML private Spinner<Integer> amountOfSimulationSpinner;
  @FXML private Spinner<Integer> threadSpeedSpinner;
  @FXML private Label totalUnitsArmyOneLabel;
  @FXML private Label totalUnitsArmyTwoLabel;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    WargameFacade instance = WargameFacade.getInstance();

    instance.registerObserver(this);

    String armyOneName = instance.getArmyOneName();
    String armyTwoName = instance.getArmyTwoName();

    initiateTableView(
        WargameFacade.getInstance().getArmyOneUnits(),
        WargameFacade.getInstance().getArmyTwoUnits());

    simulationSize = (armyOneTableView.getItems().size() + armyTwoTableView.getItems().size()) / 2;


    setColumnPropertyFactory(armyOneTypeColumn, armyOneHealthColumn);
    setColumnPropertyFactory(armyTwoTypeColumn, armyTwoHealthColumn);

    initiateGraphValues();

    armyOneChartData.setName(armyOneName);
    armyTwoChartData.setName(armyTwoName);
    armyOneNameLabel.setText(armyOneName);
    armyTwoNameLabel.setText(armyTwoName);


    setSpinnerValue(threadSpeedSpinner,5, 1000, threadSpeed, 5);
    setSpinnerValue(amountOfSimulationSpinner, 1, 10000, 1, 1);

    setCurrentTerrainImageView();

    threadSpeed = 40;

    initiateTimeline();
  }

  private void initiateTimeline() {
    timeline = new Timeline(new KeyFrame(Duration.millis(threadSpeed), this::step));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.stop();
  }

  private void updateAllTotalLabels() {
    totalUnitsArmyOneLabel.setText(String.valueOf(armyOneTableView.getItems().size()));
    totalUnitsArmyTwoLabel.setText(String.valueOf(armyTwoTableView.getItems().size()));
  }

  private void initiateTableView(List<Unit> armyOneUnits, List<Unit> armyTwoUnits) {
    ObservableList<Unit> unitObservableListArmyOne =
        FXCollections.observableList(armyOneUnits);
    armyTwoTableView.setItems(unitObservableListArmyOne);

    ObservableList<Unit> unitObservableListArmyTwo =
        FXCollections.observableList(armyTwoUnits);
    armyOneTableView.setItems(unitObservableListArmyTwo);

    updateAllTotalLabels();
  }


  private void setSpinnerValue(Spinner<Integer> spinner, int minValue, int maxValue, int startValue, int incrementBy) {
    SpinnerValueFactory<Integer> spinnerValueFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue, maxValue, startValue, incrementBy);
    spinner.setValueFactory(spinnerValueFactory);

    spinner.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                spinner.getEditor().setText(oldValue);
              }});

    spinner.valueProperty()
        .addListener(((observableValue, oldValue, newValue) -> threadSpeed = newValue));
  }

  private void initiateGraphValues() {
    armyOneChartData = new XYChart.Series<>();
    armyTwoChartData = new XYChart.Series<>();
    armySizeLineChart.getData().addAll(armyOneChartData, armyTwoChartData);

    armySizeLineChart.getXAxis().setTickLabelsVisible(false);
    armySizeLineChart.getYAxis().setAutoRanging(true);
    armySizeLineChart.setCreateSymbols(false);
    armySizeLineChart.setHorizontalGridLinesVisible(false);
    armySizeLineChart.setVerticalGridLinesVisible(false);
  }


  private void setColumnPropertyFactory(TableColumn<Unit, String> columnType, TableColumn<Unit, String> columnHealth) {
    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnHealth.setCellValueFactory(new PropertyValueFactory<>("health"));
  }


  @FXML
  private void onStartSimulationButtonClick() {
    int amountOfSimulations = amountOfSimulationSpinner.getValue();

    if (amountOfSimulations == 1) {
      timeline.play();
    } else {
      List<Long> result = WargameFacade.getInstance().simulateMultipleTimes(amountOfSimulations);
      PieChart.Data dataWinner = new PieChart.Data(armyOneNameLabel.getText(), result.get(0));
      PieChart.Data dataLoser = new PieChart.Data(armyTwoNameLabel.getText(), result.get(1));
      amountOfSimulationSpinner.setDisable(true);
      simulationResultPieChart.getData().addAll(dataWinner, dataLoser);
    }
  }


  @FXML
  private void onResetSimulationButtonClick() {
    armyOneChartData.getData().clear();
    armyTwoChartData.getData().clear();
    initiateTableView(WargameFacade.getInstance().getArmyOneDuplicate(),
        WargameFacade.getInstance().getArmyTwoDuplicate());
    initiateTimeline();
    WargameFacade.getInstance().setBattleDuplicatedArmies();
    onStartSimulationButtonClick();
  }


  private void step(ActionEvent actionEvent) {
    if (WargameFacade.getInstance().simulateStep()) {
      timeline.stop();
    }
    updateGraphValues();
    loadHitBox(nameAttacker, attackValue, attackBonus, nameDefender, healthDefender, resistDefender);
    updateAllTotalLabels();
    armyTwoTableView.refresh();
    armyOneTableView.refresh();
  }

  private void loadHitBox(String nameAttacker, int attackValue, int attackBonus,
                          String nameDefender, int healthDefender, int resistDefender) {

    FXMLLoader loader = new FXMLLoader(WargamesApplication.class.getClassLoader()
        .getResource("no.ntnu.idatg2001.wargames.ui.views/HitUpdateBoxView.fxml"));
    try {
      vBoxHitUpdate.getChildren().add(loader.load());
      HitUpdateBoxController controller = loader.getController();
      controller.setValues(nameAttacker, attackValue, attackBonus, nameDefender, healthDefender, resistDefender);
      if (vBoxHitUpdate.getChildren().size() > 20) {
        vBoxHitUpdate.getChildren().remove(0);
      }
      hitUpdateScrollView.setVvalue(1);
    } catch (IOException e) {
      showErrorMessage(e.getMessage());
    }
  }

  private void updateGraphValues() {
    armyOneChartData.getData().add(new XYChart.Data<>(String.valueOf(counter), armyOneSize));
    armyTwoChartData.getData().add(new XYChart.Data<>(String.valueOf(counter), armyTwoSize));
    counter++;
  }

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
  }


  private void setCurrentTerrainImageView() {
    String terrain = WargameFacade.getInstance().getCurrentTerrain();
    currentTerrainLabel.setText(terrain);
    currentTerrainImageView.setImage(new Image(String.valueOf(
            getClass().getClassLoader()
                .getResource("no.ntnu.idatg2001.wargames.ui.controllers/"
                    + terrain + "_Image.jpg"))));
  }

  @FXML
  private void onShowMainMenuButtonClick() {
    WargamesApplication.gotToMainMenu();
  }

  @FXML
  private void onShowArmyMakerButtonClick() {
    WargamesApplication.goToBattleMaker();
  }

  /**
   * Method to show an alert error message.
   *
   * @param e The message to show in the alert box.
   */
  private void showErrorMessage(String e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error not a valid input");
    alert.setHeaderText(e);
    alert.setContentText("Please try again");
    alert.showAndWait();
  }






}
