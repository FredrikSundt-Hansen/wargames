package no.ntnu.idatg2001.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RangedUnitTest {
    private Unit r1;
    private Unit r2;

    @BeforeEach
    void setUp() {
        r1 = new RangedUnit("Test", 100);
        r2 = new RangedUnit("Test", 100);
    }

    @Test
    void getResistBonus() {
        assertEquals(6,r1.getResistBonus());

        r2.attack(r1);
        assertEquals(4,r1.getResistBonus());


        r2.attack(r1);
        assertEquals(2,r1.getResistBonus());

    }
}