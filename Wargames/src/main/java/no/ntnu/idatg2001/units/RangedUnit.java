package no.ntnu.idatg2001.units;

public class RangedUnit extends Unit{

    /**
     * Constructs a new ranged unit, with all parameters.
     * @param name The name of the unit.
     * @param health The health value of the hunit.
     * @param attack The attack value of the unit.
     * @param armor The armor value of the unit.
     */
    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Constructs new ranged unit, with name and health.
     * @param name The name of the unit.
     * @param health The health value of the unit.
     */
    public RangedUnit(String name, int health) {
        super(name, health);
        attack = 15;
        armor = 8;
    }

    @Override
    public int getAttackBonus() {
        return 3;
    }

    @Override
    public int getResistBonus() {
        if (numberOfDefends == 0){
            return 6;
        }else if (numberOfDefends ==1){
            return 4;
        } else {
            return 2;
        }
    }
}
