package no.ntnu.idatg2001.units;

public class CommanderUnit extends CavalryUnit{

    public CommanderUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    public CommanderUnit(String name, int health) {
        super(name, health);
        attack = 25;
        armor = 15;
    }


}
