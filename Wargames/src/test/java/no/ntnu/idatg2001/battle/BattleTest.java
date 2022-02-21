package no.ntnu.idatg2001.battle;

import no.ntnu.idatg2001.armies.Army;
import no.ntnu.idatg2001.units.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BattleTest {
    private Battle battle1;
    private Army army1;
    private Army army2;
    private List<Unit> units;

    private InfantryUnit infantry1;
    private RangedUnit ranged1;
    private CavalryUnit cavalry1;
    private CommanderUnit commander1;

    @BeforeEach
    void setUp() {
        infantry1 = new InfantryUnit("Infantry", 10);
        ranged1 = new RangedUnit("Ranged unit", 10);
        cavalry1 = new CavalryUnit("Cavalry", 10);
        commander1 = new CommanderUnit("Commander", 10);

        units = new ArrayList<>();
        units.add(infantry1);
        units.add(ranged1);
        units.add(cavalry1);
        units.add(commander1);

        army1 = new Army("TestArmy1", units);
        army2 = new Army("TestArmy2", units);

        battle1 = new Battle(army1, army2);
    }

    @Test
    void simulate() {
        Army test = battle1.simulate();
        //System.out.println(battle1.toString());
        System.out.println("Winner: " + test.toString());
    }
}