package no.ntnu.idatg2001.units ;

public class InfantryUnit extends Unit{

    /**
     * Constructs new infantry unit, with all parameters.
     * @param name Name of the unit.
     * @param health Health value of the unit.
     * @param attack Attack value of the unit.
     * @param armor Armor value of the unit.
     */
    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Constructs new infantry unit, with only name and health.
     * @param name The name of the unit.
     * @param health The health value of the unit.
     */
    public InfantryUnit(String name, int health) {
        super(name, health);
        attack = 15;
        armor = 10;
    }

    @Override
    public int getAttackBonus() {
        return 2;
    }

    @Override
    public int getResistBonus() {
        return 1;
    }


}
