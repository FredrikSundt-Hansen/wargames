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

public class ArmyFileHandler {

  /** Private constructor to hide the implicit public one. */
  private ArmyFileHandler() {}

  private static void clearFile(String path) throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      writer.write("");
    }
  }

  /**
   * Writes an army to a file in path.
   *
   * @param army The army to write in the file.
   * @param path The path of the file to write to.
   */
  public static void writeArmyCsv(Army army, String path) throws IOException {
    clearFile(path);
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      writer.write(army.getName());
      writer.newLine();
      for (Unit unit : army.getUnits()) {
        writer.write(
            unit.getClass().getSimpleName() + "," + unit.getName() + "," + unit.getHealth() + "\n");
      }
    }
  }

  /**
   * Reads a csv file to path, and returns the army.
   *
   * @param path The path of the csv file.
   * @return The army read from the file.
   */
  public static Army readCsv(String path) throws IOException, NullPointerException {
    Army army = new Army();
    try (BufferedReader reader = Files.newBufferedReader(Path.of(path))) {
      String lineOfText;
      if ((lineOfText = reader.readLine()) != null && !lineOfText.contains(",")) {
        army.setName(lineOfText);
      } else {
        throw new NullPointerException("First line is null");
      }

      while ((lineOfText = reader.readLine()) != null) {
        String[] words = lineOfText.split(",");
        assignLineToUnit(army, words);
      }
    }
    return new Army(army);
  }

  private static void assignLineToUnit(Army army, String[] words) {
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
