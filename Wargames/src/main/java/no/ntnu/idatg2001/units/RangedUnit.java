package no.ntnu.idatg2001.units;

public class RangedUnit extends Unit{

    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

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
        if (this.numberOfDefends == 0){
            return 6;
        }else if (this.numberOfDefends <=1){
            return 4;
        } else {
            return 2;
        }
    }
}
