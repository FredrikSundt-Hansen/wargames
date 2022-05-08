package no.ntnu.idatg2001.wargames.model.armies;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.units.RangedUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;

/**
 * Army class, represents an army that consists of several units.
 *
 * @version 1.0.1
 */
public class Army {
  private final List<Unit> units;
  private final Random rand;
  private String name;

  /**
   * Constructs a new Army object containing units.
   *
   * @param name The name of the army.
   * @param units A list containing units.
   */
  public Army(String name, List<Unit> units) {
    this.setName(name);
    this.units = units;
    rand = new Random();
  }

  /**
   * Constructs an empty army with name.
   *
   * @param name The name of the army.
   */
  public Army(String name) {
    this.setName(name);
    this.units = new ArrayList<>();
    rand = new Random();
  }

  /**
   * Copy constructor for an army.
   *
   * @param army The army to copy.
   * @throws NullPointerException - If army is null.
   */
  public Army(Army army) throws NullPointerException {
    if (army != null) {
      this.name = army.name;
      this.units = army.units;
      rand = new Random();
    } else {
      throw new NullPointerException("Null army");
    }
  }

  /** Constructs an empty army with no name. */
  public Army() {
    this.name = null;
    this.units = new ArrayList<>();
    rand = new Random();
  }

  public String getName() {
    return name;
  }

  /**
   * Mutator method to change the name of the unit.
   *
   * @param name The name of the unit.
   * @exception IllegalArgumentException - If name is empty (null).
   */
  public void setName(String name) throws IllegalArgumentException {
    if (!name.isEmpty()) {
      this.name = name;
    } else {
      throw new IllegalArgumentException("Invalid name.");
    }
  }

  public List<Unit> getUnits() {
    return units;
  }

  /**
   * Returns a new list of all cavalry units in the army.
   *
   * @return The list of units, consists of cavalry units.
   */
  public List<Unit> getAllCavalryUnits() {
    return units.stream().filter(CavalryUnit.class::isInstance).toList();
  }

  /**
   * Returns a new list of all commander units in the army.
   *
   * @return The list of units, consists of commander units.
   */
  public List<Unit> getAllCommanderUnits() {
    return units.stream().filter(CommanderUnit.class::isInstance).toList();
  }

  /**
   * Returns a new list of all ranged units in the army.
   *
   * @return The list of units, consists of ranged units.
   */
  public List<Unit> getAllRangedUnits() {
    return units.stream().filter(RangedUnit.class::isInstance).toList();
  }

  /**
   * Returns a new list of all infantry units in the army.
   *
   * @return The list of units, consists of infantry units.
   */
  public List<Unit> getAllInfantryUnits() {
    return units.stream().filter(InfantryUnit.class::isInstance).toList();
  }

  /**
   * Returns a list of all the units in the army.
   *
   * @return New list of all the units.
   */
  public List<Unit> getAllUnits() {
    return new ArrayList<>(this.units);
  }

  /**
   * Returns a random unit in the army.
   *
   * @return A unit.
   */
  public Unit getRandom() {
    if (!units.isEmpty()) {
      return units.get(rand.nextInt(units.size()));
    } else {
      throw new IllegalArgumentException("Empty list");
    }
  }

  /**
   * Adds a unit to the army.
   *
   * @param unit The unit to add.
   */
  public void addUnit(Unit unit) {
    if (unit != null) {
      this.units.add(unit);
    } else {
      throw new NullPointerException("Invalid unit, cannot add it to the army");
    }
  }

  /**
   * Adds a collection of units to the army.
   *
   * @param units The list of units to add.
   */
  public void addAllUnits(List<Unit> units) {
    this.units.addAll(units);
  }

  /**
   * Removes a unit from the army.
   *
   * @param unit The unit to remove,
   */
  public void removeUnit(Unit unit) {
    if (this.units.contains(unit)) {
      this.units.remove(unit);
    } else {
      throw new IllegalArgumentException("Unit already exists in the army.");
    }
  }

  /**
   * Checks if the army is empty or not.
   *
   * @return True if the army contains units, false if it is empty.
   */
  public boolean hasUnits() {
    return !this.units.isEmpty();
  }

  /**
   * Method to set the terrain of every unit in the army units.
   *
   * @param terrain String, the terrain to change to.
   */
  public void setTerrainToUnits(String terrain) {
    for (Unit unit : units) {
      unit.setTerrain(terrain);
    }
  }

  /**
   * Returns the information about the army.
   *
   * @return A string containing the name and how many units.
   */
  @Override
  public String toString() {
    return " '"
        + name
        + "' "
        + "\nUnits           :  "
        + units.size()
        + "\nDifferent units :  "
        + units.stream().map(Unit::getName).collect(Collectors.toSet())
        + "\n";
  }

  /**
   * Compares to armies. Return true if they are equal, takes name and units of the army into
   * consideration.
   *
   * @param o Army to compare.
   * @return True if the two armies are equal, and false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Army)) {
      return false;
    }
    Army army = (Army) o;
    return Objects.equals(name, army.name) && Objects.equals(units, army.units);
  }

  /**
   * Creates a hashcode of the army. Take the name and the units into consideration.
   *
   * @return Hashcode as an integer.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, units);
  }
}
