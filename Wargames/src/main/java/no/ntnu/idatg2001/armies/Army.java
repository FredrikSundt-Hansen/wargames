package no.ntnu.idatg2001.armies;
import no.ntnu.idatg2001.units.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Army {
    String name;
    List<Unit> units;

    /**
     * Constructs a new Army object containing units.
     * @param name The name of the army.
     * @param units A list containing units.
     */
    public Army(String name, List<Unit> units) {
        this.name = name;
        addAll(units);
    }

    /**
     * Constructs an empty army.
     * @param name The name of the army.
     */
    public Army(String name) {
        this.name = name;
        this.units = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Unit> getUnits() {
        return units;
    }

    /**
     * Adds a unit to the army.
     * @param unit The unit to add.
     */
    public void add(Unit unit) {
        units.add(unit);
  }

    /**
     * Adds a collection of units to the army.
     * @param units The list of units to add.
     */
  public void addAll(List<Unit> units) {
        this.units.addAll(units);
  }

    /**
     * Removes a unit from the army.
     * @param unit The unit to remove,
     */
  public void remove(Unit unit) {
        this.units.remove(unit);
  }

    /**
     * Checks if the army is empty or not.
     * @return True if the army is not empty, false otherwise.
     */
  public boolean hasUnits() {
        return !this.units.isEmpty();
  }

    /**
     * Returns a list of all the units in the army.
     * @return New list of all the units.
     */
  public List<Unit> getAllUnits() {
        return new ArrayList<>(this.units);
  }

    /**
     * Returns a random unit in the army.
     * @return A unit.
     */
  public Unit getRandom() {
        Random random = new Random();
        return this.units.get(random.nextInt(units.size()-1));
  }

    /**
     * Returns the information about the army.
     * @return A string containing the name and how many units.
     */
    @Override
    public String toString() {
        return  "Army "+ name + '\n'
                + "has " + units + " units.";
    }

    /**
     * Compares to armies. Return true if they are equal, takes name and units of the into consideration.
     * @param o Army to compare.
     * @return True if the two armies are equal, and false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Army)) return false;
        Army army = (Army) o;
        return Objects.equals(name, army.name) && Objects.equals(units, army.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }
}


