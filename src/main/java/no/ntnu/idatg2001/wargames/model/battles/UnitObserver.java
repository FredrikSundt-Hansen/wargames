package no.ntnu.idatg2001.wargames.model.battles;

/**
 * This is observable class of the observable pattern.
 * Other classes that are going to update unit information extends this interface.
 * Update unit information, and two sizes.
 */
public interface UnitObserver {

  void hitUpdate(String nameAttacker, int attackValue, int attackBonus, String nameDefender,
                 int healthDefender, int resistDefender);

  void sizeUpdate(int sizeOne, int sizeTwo);
}
