package no.ntnu.idatg2001.wargames.ui.controllers;

import java.io.File;
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

public class SimulationController implements UnitObserver, Initializable {

  private XYChart.Series<String, Number> unitsArmy1Chart;
  private XYChart.Series<String, Number> unitsArmy2Chart;
  private PieChart.Data dataWinner;
  private PieChart.Data dataLoser;


  private Timeline timeline;

  private int armyOneSize;
  private int armyTwoSize;
  private int threadSpeed;
  private int counter;

  private boolean simulating;

  private String nameAttacker;
  private int attackValue;
  private int attackBonus;
  private String nameDefender;
  private int healthDefender;
  private int resistDefender;

  @FXML private PieChart simulationResultPieChart;

  @FXML private ScrollPane hitUpdateScrollView;
  @FXML private Spinner<Integer> threadSpeedSpinner;

  @FXML private Label totalInfantriesArmyOneLabel;
  @FXML private Label totalRangedArmyOneLabel;
  @FXML private Label totalCavalriesArmyOneLabel;
  @FXML private Label totalCommandersArmyOneLabel;
  @FXML private Label totalUnitsArmyOneLabel;

  @FXML private Label totalInfantriesArmyTwoUnitsLabel;
  @FXML private Label totalRangedArmyTwoLabel;
  @FXML private Label totalCavalriesArmyTwoLabel;
  @FXML private Label totalCommandersArmyTwoLabel;
  @FXML private Label totalArmyTwoLabel;

  @FXML private Label armyOneNameLabel;
  @FXML private Label armyTwoNameLabel;

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
  public void initialize(URL url, ResourceBundle resourceBundle) {
    WargameFacade instance = WargameFacade.getInstance();

    instance.registerObserver(this);

    String armyOneName = WargameFacade.getInstance().getArmyOneName();
    String armyTwoName = WargameFacade.getInstance().getArmyTwoName();

    ObservableList<Unit> unitObservableListArmyOne =
        FXCollections.observableList(instance.getBattle().getArmyOne().getUnits());
    tableViewArmyOne.setItems(unitObservableListArmyOne);

    ObservableList<Unit> unitObservableListArmyTwo =
        FXCollections.observableList(instance.getBattle().getArmyTwo().getUnits());
    tableViewArmyTwo.setItems(unitObservableListArmyTwo);

    setColumnPropertyFactory(columnTypeArmyOne, columnHealthArmyOne);
    setColumnPropertyFactory(columTypeArmyTwo, columnHealthArmyTwo);

    initiateGraphValues();

    unitsArmy1Chart.setName(armyOneName);
    unitsArmy2Chart.setName(armyTwoName);
    armyOneNameLabel.setText(armyOneName);
    armyTwoNameLabel.setText(armyTwoName);

    threadSpeed = 80;
    setSpinnerValue(threadSpeedSpinner,10, 1000, threadSpeed, 10);
    setSpinnerValue(amountOfSimulationSpinner, 1, 10000, 1, 1);

    setCurrentTerrainImageView();
  }

  private void setSpinnerValue(
      Spinner<Integer> spinner, int minValue, int maxValue, int startValue, int incrementBy) {

    SpinnerValueFactory<Integer> spinnerValueFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue, maxValue, startValue, incrementBy);
    spinner.setValueFactory(spinnerValueFactory);

    spinner
        .getEditor()
        .textProperty()
        .addListener(
            (observableValue, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                spinner.getEditor().setText(oldValue);
              }
            });

    spinner
        .valueProperty()
        .addListener(((observableValue, oldValue, newValue) -> threadSpeed = newValue));
  }

  private void initiateGraphValues() {
    unitsArmy1Chart = new XYChart.Series<>();
    unitsArmy2Chart = new XYChart.Series<>();
    armySizeLineChart.getData().addAll(unitsArmy1Chart, unitsArmy2Chart);

    armySizeLineChart.getXAxis().setTickLabelsVisible(false);
    armySizeLineChart.setCreateSymbols(false);
    armySizeLineChart.setHorizontalGridLinesVisible(false);
    armySizeLineChart.setVerticalGridLinesVisible(false);
  }

  private void setColumnPropertyFactory(TableColumn<Unit, String> columnType, TableColumn<Unit, String> columnHealth) {
    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnHealth.setCellValueFactory(new PropertyValueFactory<>("health"));

  }

  private void step(ActionEvent actionEvent) {
    addDataToCharts();



  }

  private void addDataToCharts() {
    if (amountOfSimulationSpinner.getValue() == 1){
      if (WargameFacade.getInstance().simulateStep()) {
        timeline.stop();

      }
      unitsArmy1Chart.getData().add(new XYChart.Data<>(String.valueOf(counter), armyOneSize));
      unitsArmy2Chart.getData().add(new XYChart.Data<>(String.valueOf(counter), armyTwoSize));
      counter++;

      loadHitBox(nameAttacker, attackValue, attackBonus, nameDefender, healthDefender, resistDefender);

      tableViewArmyOne.refresh();
      tableViewArmyTwo.refresh();

      updateAllTotalLabels();

    }
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
      e.printStackTrace();
    }
  }

  private void updateAllTotalLabels() {
    List<Integer> unitValuesArmyOne = WargameFacade.getInstance().getArmyOneAmountsUnitTypes();
    List<Integer> unitValuesArmyTwo = WargameFacade.getInstance().getArmyTwoAmountsUnitTypes();

    setValueToLabel(unitValuesArmyOne, totalInfantriesArmyOneLabel, totalRangedArmyOneLabel,
        totalCavalriesArmyOneLabel, totalCommandersArmyOneLabel, totalUnitsArmyOneLabel);

    setValueToLabel(unitValuesArmyTwo, totalInfantriesArmyTwoUnitsLabel, totalRangedArmyTwoLabel,
        totalCavalriesArmyTwoLabel, totalCommandersArmyTwoLabel, totalArmyTwoLabel);
  }

  private void setValueToLabel(List<Integer> unitValuesArmyTwo,
                              Label totalInfantriesArmyTwoUnitsLabel,
                              Label totalRangedArmyTwoLabel, Label totalCavalriesArmyTwoLabel,
                              Label totalCommandersArmyTwoLabel, Label totalArmyTwoLabel) {

    totalInfantriesArmyTwoUnitsLabel.setText(String.valueOf(unitValuesArmyTwo.get(0)));
    totalRangedArmyTwoLabel.setText(String.valueOf(unitValuesArmyTwo.get(1)));
    totalCavalriesArmyTwoLabel.setText(String.valueOf(unitValuesArmyTwo.get(2)));
    totalCommandersArmyTwoLabel.setText(String.valueOf(unitValuesArmyTwo.get(3)));
    totalArmyTwoLabel.setText(String.valueOf(unitValuesArmyTwo.get(4)));
  }


  public void onStartSimulationButtonClick() {
    int amountOfSimulations = amountOfSimulationSpinner.getValue();

    if (amountOfSimulations == 1) {
      if (!simulating) {
        timeline = new Timeline(new KeyFrame(Duration.millis(threadSpeed), this::step));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
      } else {
        timeline.stop();
      }
    } else {
      List<Long> result = WargameFacade.getInstance().simulateMultipleTimes(amountOfSimulations);
      dataWinner = new PieChart.Data(armyOneNameLabel.getText(), result.get(0));
      dataLoser = new PieChart.Data(armyTwoNameLabel.getText(), result.get(1));
      amountOfSimulationSpinner.setDisable(true);
    }

    simulationResultPieChart.getData().addAll(dataWinner, dataLoser);
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
    currentTerrainImageView.setImage(new Image(
        new File("src/main/resources/images/" +
            terrain+"_Image.jpg").toURI().toString()));
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
