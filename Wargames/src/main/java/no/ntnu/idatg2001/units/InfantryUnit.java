package no.ntnu.idatg2001.unit;

public class InfantryUnit extends Unit{

    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

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
