package no.ntnu.idatg2001.units;

public class CavalryUnit extends Unit{

    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

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
