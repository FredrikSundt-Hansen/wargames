package no.ntnu.idatg2001.units;

public class CavalryUnit extends Unit{

    /**
     * Constructs new cavalry unit, with all parameters.
     * @param name Name of the unit.
     * @param health Health value of the unit.
     * @param attack Attack value of the unit.
     * @param armor Armor vlaue of the unit.
     */
    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Constructs new cavalry unit, with name and health.
     * @param name Name of the unit.
     * @param health Health value of the unit.
     */
    public CavalryUnit(String name, int health) {
        super(name, health);
        attack = 20;
        armor = 12;
    }

    @Override
    public int getAttackBonus() {
    if (this.numberOfAttacks == 0) {
      return 4;
    } else {
        return 2;
        }
    }

    @Override
    public int getResistBonus() {
        return 1;
    }
}
