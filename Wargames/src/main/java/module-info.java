module no.ntnu.idatg2001 {
  requires javafx.controls;
  requires javafx.fxml;

  opens no.ntnu.idatg2001.wargames.model.units;
  opens no.ntnu.idatg2001.wargames.model.battles;
  opens no.ntnu.idatg2001.wargames.model.armies;
  opens no.ntnu.idatg2001.wargames.ui.views;
  opens no.ntnu.idatg2001.wargames.ui.controllers;

  exports no.ntnu.idatg2001.wargames.ui.controllers;
  exports no.ntnu.idatg2001.wargames.ui.views;
  opens no.ntnu.idatg2001.wargames.model.utility;


}