package no.ntnu.idatg2001.wargames.model.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import no.ntnu.idatg2001.wargames.model.armies.Army;
import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.units.RangedUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;

public class fileHandler {

  /**
   * Private constructor to hide the implicit public one.
   */
  private fileHandler() {

  }

  public static void clearFile(String path) {

  }

  /**
   * Writes an army to a file in path.
   * @param army The army to write in the file.
   * @param path The path of the file to write to.
   */
  public static void writeCsv(Army army, Path path) {
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write(army.getName() + "\n");
      for (Unit unit : army.getUnits()) {
        writer.write(
            unit.getClass().getSimpleName() + "," + unit.getName() + "," + unit.getHealth() + "\n");
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Reads a csv file to path, and returns the army.
   * @param path The path of the csv file.
   * @return The army read from the file.
   */
  public static Army readCsv(Path path) {
    Army army = new Army();
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      String lineOfText;
      if (reader.readLine() != null) {
        while ((lineOfText = reader.readLine()) != null) {
          String[] words = lineOfText.split(",");

          if (words[0].strip().equalsIgnoreCase(InfantryUnit.class.getSimpleName())) {
            army.addUnit(new InfantryUnit(words[1].strip(), Integer.parseInt(words[2].strip())));
          } else if (words[0].strip().equalsIgnoreCase(RangedUnit.class.getSimpleName())) {
            army.addUnit(new RangedUnit(words[1].strip(), Integer.parseInt(words[2].strip())));
          } else if (words[0].strip().equalsIgnoreCase(CavalryUnit.class.getSimpleName())) {
            army.addUnit(new CavalryUnit(words[1].strip(), Integer.parseInt(words[2].strip())));
          } else if (words[0].strip().equalsIgnoreCase(CommanderUnit.class.getSimpleName())) {
            army.addUnit(new CommanderUnit(words[1].strip(), Integer.parseInt(words[2].strip())));
          }
        }
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    return army;
  }
}
