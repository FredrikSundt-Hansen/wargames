package no.ntnu.idatg2001.wargames.model;

import no.ntnu.idatg2001.wargames.model.units.Unit;

public interface UnitObserver {

  void hitUpdate(Unit attacker, Unit defender);

  void sizeUpdate(int armySize, int infantrySize, int rangedSize, int cavalrySize, int commanderSize);
}
