package no.ntnu.idatg2001.wargames.model;

public interface UnitObserver {

  void hitUpdate(String nameAttacker, int attackValue, int attackBonus, String nameDefender,
                 int healthDefender, int resistDefender);

  void sizeUpdate(int sizeOne, int sizeTwo);
}
