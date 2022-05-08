package no.ntnu.idatg2001.wargames.model;

import no.ntnu.idatg2001.wargames.model.units.Unit;

public interface ArmyObserver {

  void update(Unit opponent, Unit defender);
}
