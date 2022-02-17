package no.ntnu.idatg2001.units;

public class CommanderUnit extends CavalryUnit{

    /**
     * Constructs a new commander unit, with all parameters.
     * @param name Name of the unit.
     * @param health Health value of the unit.
     * @param attack Attack value of the unit.
     * @param armor Armor value of the unit.
     */
    public CommanderUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Constructs commander unit, with name and health.
     * @param name Name of the unit.
     * @param health Health value of the unit.
     */
    public CommanderUnit(String name, int health) {
        super(name, health);
        attack = 25;
        armor = 15;
    }


}
