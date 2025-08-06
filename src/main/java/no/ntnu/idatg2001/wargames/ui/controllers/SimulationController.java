package no.ntnu.idatg2001.wargames.ui.controllers;

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
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
  private int refreshUpdater;
  private boolean simulating;

  private Timeline timeline;
  private XYChart.Series<String, Number> armyOneChartData;
  private XYChart.Series<String, Number> armyTwoChartData;

  @FXML private Slider onDragTimerSlider;
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
    threadSpeed = ((int) onDragTimerSlider.getValue());

    initiateGraphValues();
    initiateTimeline();

    onDragTimerSlider.valueProperty().addListener(
        (observableValue, oldValue, newValue) -> {
          timeline.setRate(newValue.intValue());
        }
    );

    setColumnPropertyFactory(armyOneTypeColumn, armyOneHealthColumn);
    setColumnPropertyFactory(armyTwoTypeColumn, armyTwoHealthColumn);



    setTableViewValues(WargameFacade.getInstance().getArmyOneUnits(),
        WargameFacade.getInstance().getArmyTwoUnits());

    currentTerrainLabel.setText(WargameFacade.getInstance().getCurrentTerrain());

    winnerLabel.setVisible(false);
    winnerTrophyImageView.setVisible(false);
  }

  /**
   * Sets tableView values to the corresponding lists of units.
   *
   * @param armyOneUnits List of units for armyOneTableView.
   * @param armyTwoUnits List of units for armyTwoTableView.
   */
  private void setTableViewValues(List<Unit> armyOneUnits, List<Unit> armyTwoUnits) {
    ObservableList<Unit> unitObservableListArmyTwo =
        FXCollections.observableList(armyOneUnits);
    armyOneTableView.setItems(unitObservableListArmyTwo);

    ObservableList<Unit> unitObservableListArmyOne =
        FXCollections.observableList(armyTwoUnits);
    armyTwoTableView.setItems(unitObservableListArmyOne);

    updateAllTotalLabels(armyOneTableView.getItems().size(), armyTwoTableView.getItems().size());
  }

  /**
   * Updates armyOne and armyTwo size label.
   */
  private void updateAllTotalLabels(int armyOneSize, int armyTwoSize) {
    totalUnitsArmyOneLabel.setText(String.valueOf(armyOneSize));
    totalUnitsArmyTwoLabel.setText(String.valueOf(armyTwoSize));
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
    String armyOneName = WargameFacade.getInstance().getArmyOneName();
    String armyTwoName = WargameFacade.getInstance().getArmyTwoName();
    armyOneChartData.setName(armyOneName);
    armyTwoChartData.setName(armyTwoName);
    armyOneNameLabel.setText(armyOneName);
    armyTwoNameLabel.setText(armyTwoName);
    armySizeLineChart.getData().addAll(armyOneChartData, armyTwoChartData);
  }

  /**
   * Method to start the simulation when this button is clicked.
   */
  @FXML
  private void onStartSimulationButtonClick() {
    if (!simulating) {
      timeline.play();
      simulating = true;
    } else {
      timeline.stop();
      simulating = false;
    }
  }

  @FXML
  private void onResetSimulationButtonClick() {
    winnerLabel.setVisible(false);
    winnerTrophyImageView.setVisible(false);

    WargameFacade.getInstance().setArmiesToBackupArmies();
    setTableViewValues(WargameFacade.getInstance().getArmyOneUnits(),
        WargameFacade.getInstance().getArmyTwoUnits());
    initiateTimeline();
    simulating = false;
    hitLogListView.getItems().clear();
    hitLogListView.refresh();
    armySizeLineChart.getData().clear();
    initiateGraphValues();
    onStartSimulationButtonClick();
    WargameFacade.getInstance().registerObserver(this);

  }

  /**
   * Represents one step of the simulation, used with timeline to synchronise data
   * flow of the observable pattern.
   */
  private void step(ActionEvent actionEvent) {
    if (WargameFacade.getInstance().simulateStep()) {
      timeline.stop();
      armyOneTableView.refresh();
      armyTwoTableView.refresh();
      winnerTrophyImageView.setVisible(true);
      winnerLabel.setVisible(true);
      if (Integer.parseInt(totalUnitsArmyOneLabel.getText())
          > Integer.parseInt(totalUnitsArmyTwoLabel.getText())) {
        winnerLabel.setText(armyOneNameLabel.getText());
      } else {
        winnerLabel.setText(armyTwoNameLabel.getText());
      }
    }

    if (refreshUpdater >= 250) {
      refreshUpdater = 0;
      armyOneTableView.refresh();
      armyTwoTableView.refresh();
    }
    refreshUpdater++;
  }

  /**
   * Updates the graph to the current known values of armyOneSize and armyTwoSize.
   */
  private void updateGraphValues(int sizeOne, int sizeTwo) {
    armyOneChartData.getData().add(new XYChart.Data<>(String.valueOf(counter), sizeOne));
    armyTwoChartData.getData().add(new XYChart.Data<>(String.valueOf(counter), sizeTwo));
    counter++;
  }

  @Override
  public void hitUpdate(String nameAttacker, int attackValue, int attackBonus, String nameDefender,
      int healthDefender, int resistDefender) {
    hitLogListView.getItems()
        .add(nameAttacker + " " +attackValue + "+(" + attackBonus + ")"
            + "  ->  " + nameDefender + " -(" + resistDefender + ")"
            + " = " + healthDefender);
    hitLogListView.scrollTo(hitLogListView.getItems().size());
  }

  @Override
  public void sizeUpdate(int sizeOne, int sizeTwo) {
    updateAllTotalLabels(sizeOne, sizeTwo);
    updateGraphValues(sizeOne, sizeTwo);
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
