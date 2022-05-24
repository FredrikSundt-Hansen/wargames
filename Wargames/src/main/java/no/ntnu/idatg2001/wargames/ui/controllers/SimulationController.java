package no.ntnu.idatg2001.wargames.ui.controllers;

import java.io.File;
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
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import no.ntnu.idatg2001.wargames.model.WargameFacade;
import no.ntnu.idatg2001.wargames.model.battles.UnitObserver;
import no.ntnu.idatg2001.wargames.model.units.Unit;
import no.ntnu.idatg2001.wargames.ui.views.WargamesApplication;

/**
 * Controller class for SimulationView.Able to start a simulation, and reset simulation,
 * Takes the user back to BattleMaker. This class is a part of the
 * BattleUpdater/UnitUpdater observable pattern,
 * implements UnitObserver to update graph and hit log.
 */
public class SimulationController implements UnitObserver, Initializable {
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
  @FXML private Label totalUnitsArmyOneLabel;
  @FXML private Label totalUnitsArmyTwoLabel;
  @FXML private ImageView winnerTrophyImageView;
  @FXML private Label winnerLabel;
  @FXML private ListView<String> hitLogListView;

  /**
   * Registers this class to the Observable pattern.
   * Sets up tableView, graph values, and spinner values.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    WargameFacade instance = WargameFacade.getInstance();

    instance.registerObserver(this);

    initiateGraphValues();

    int n = setTableViewValues(instance.getArmyOneUnits(), instance.getArmyTwoUnits());

    if (n > 60) { // To make the simulation not take to long.
      threadSpeed = 10;
    } else {
      threadSpeed = n;
    }

    setColumnPropertyFactory(armyOneTypeColumn, armyOneHealthColumn);
    setColumnPropertyFactory(armyTwoTypeColumn, armyTwoHealthColumn);

    String armyOneName = instance.getArmyOneName();
    String armyTwoName = instance.getArmyTwoName();
    armyOneChartData.setName(armyOneName);
    armyTwoChartData.setName(armyTwoName);
    armyOneNameLabel.setText(armyOneName);
    armyTwoNameLabel.setText(armyTwoName);

    initiateTimeline();

    setCurrentTerrainImageView();
    winnerLabel.setVisible(false);
    winnerTrophyImageView.setVisible(false);
  }

  /**
   * Sets TerrainImageView to match the current terrain of the battle.
   */
  private void setCurrentTerrainImageView() {
    String terrain = WargameFacade.getInstance().getCurrentTerrain();
    currentTerrainLabel.setText(terrain);
    currentTerrainImageView.setImage(new Image(
        new File("src/main/resources/no.ntnu.idatg2001.wargames.ui.controllers/images/"
            + terrain + "_Image.jpg").toURI().toString()));
  }

  /**
   * Sets tableView values to the corresponding lists of units.
   *
   * @param armyOneUnits List of units for armyOneTableView.
   * @param armyTwoUnits List of units for armyTwoTableView.
   * @returns The average size of armyOneUnits and armyTwoUnits,
   */
  private int setTableViewValues(List<Unit> armyOneUnits, List<Unit> armyTwoUnits) {
    ObservableList<Unit> unitObservableListArmyTwo =
        FXCollections.observableList(armyOneUnits);
    armyOneTableView.setItems(unitObservableListArmyTwo);

    ObservableList<Unit> unitObservableListArmyOne =
        FXCollections.observableList(armyTwoUnits);
    armyTwoTableView.setItems(unitObservableListArmyOne);

    updateAllTotalLabels();

    return (armyOneUnits.size() + armyTwoUnits.size()) / 2;
  }

  /**
   * Updates armyOne and armyTwo size label.
   */
  private void updateAllTotalLabels() {
    totalUnitsArmyOneLabel.setText(String.valueOf(armyOneTableView.getItems().size()));
    totalUnitsArmyTwoLabel.setText(String.valueOf(armyTwoTableView.getItems().size()));
  }

  /**
   * Sets the cell factory of the tableColumn to match the unit field values.
   *
   * @param columnType The unit type column.
   * @param columnHealth The unit health column.
   */
  private void setColumnPropertyFactory(TableColumn<Unit, String> columnType,
                                        TableColumn<Unit, String> columnHealth) {
    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnHealth.setCellValueFactory(new PropertyValueFactory<>("health"));
  }

  /**
   * Makes an instance of timeline.
   */
  private void initiateTimeline() {
    timeline = new Timeline(new KeyFrame(Duration.millis(threadSpeed), this::step));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.stop();
  }

  /**
   * Initiates graph values to.
   */
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

  /**
   * Method to start the simulation when this button is clicked.
   */
  @FXML
  private void onStartSimulationButtonClick() {
    timeline.play();
  }

  /**
   * Method to reset the simulation when this button is clicked.
   * Goes to BattleMaker, and laos the same units there.
   */
  @FXML
  private void onResetSimulationButtonClick() {
    WargamesApplication.goToBattleMaker();
  }

  /**
   * Represents one step of the simulation, used with timeline to synchronise data
   * flow of the observable pattern.
   */
  private void step(ActionEvent actionEvent) {
    if (WargameFacade.getInstance().simulateStep()) {
      timeline.stop();
      winnerTrophyImageView.setVisible(true);
      winnerLabel.setVisible(true);
      if (Integer.parseInt(totalUnitsArmyOneLabel.getText())
          > Integer.parseInt(totalUnitsArmyTwoLabel.getText())) {
        winnerLabel.setText(armyOneNameLabel.getText());
      } else {
        winnerLabel.setText(armyTwoNameLabel.getText());
      }
    }

    updateGraphValues();
    hitLogListView.getItems()
        .add(nameAttacker + " attacked  " + nameDefender + " with resist "
            + " -(" + resistDefender + ") " + "Dealing " + attackValue
            + " +(" + attackBonus + ")" + " damage "
            + " Health : " + healthDefender);
    hitLogListView.scrollTo(hitLogListView.getItems().size());
    updateAllTotalLabels();
    armyTwoTableView.refresh();
    armyOneTableView.refresh();
  }

  /**
   * Updates the graph to the current known values of armyOneSize and armyTwoSize.
   */
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

  @FXML
  private void onShowMainMenuButtonClick() {
    WargamesApplication.gotToMainMenu();
  }

  @FXML
  private void onShowArmyMakerButtonClick() {
    WargamesApplication.goToBattleMaker();
  }

}
