package no.ntnu.idatg2001.wargames.model.armies;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.units.RangedUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;

/**
 * File handler for Army class. Writes and reads armies using buffered-writer and - reader.
 */
public class ArmyFileHandler {
  //Represents the info about the path of the last loaded file.
  private static String lastLoadedFilePath;
  //Represents the file name of the last loaded file.
  private static String lastLoadedFileName;

  private static boolean hasSavedFile; //True if the write method has been called.

  /**
   * Private constructor to hide the implicit public one.
   */
  private ArmyFileHandler() {
  }

  public static boolean hasSavedFile() {
    return hasSavedFile;
  }

  public static String getLastLoadedFilePath() {
    return lastLoadedFilePath;
  }

  public static String getLastLoadedFileName() {
    return lastLoadedFileName;
  }

  public static void setHasSavedFileFalse() {
    hasSavedFile = false;
  }

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
  public static void writeArmyCsv(Army army, String path)
      throws IOException, NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(army);

    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      if (army.getName() != null || !army.getName().isEmpty()) {
        writer.write(army.getName());
      } else {
        throw new IllegalArgumentException("Army has no name.");
      }
      writer.newLine();
      for (Unit unit : army.getUnits()) {
        writer.write(
            unit.getClass().getSimpleName() + "," + unit.getName() + "," + unit.getHealth() + "\n");
      }
      hasSavedFile = true;
    }
  }

  /**
   * Reads a csv file to path, and returns the army.
   *
   * @param path The path of the csv file.
   * @return The army read from the file.
   */
  public static Army readCsv(String path)
      throws IOException, IllegalArgumentException, NullPointerException {
    Army army = new Army();

    InputStream inputStream = ArmyFileHandler.class.getResourceAsStream(path);

    if (inputStream != null) {
      try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
          StandardCharsets.UTF_8);
           BufferedReader reader = new BufferedReader(inputStreamReader)) {
        String lineOfText;
        if ((lineOfText = reader.readLine()) != null && !lineOfText.contains(",")) {
          army.setName(lineOfText);
        } else {
          throw new IllegalArgumentException("Error. Army name is not correct. ");
        }

        while ((lineOfText = reader.readLine()) != null) {
          String[] words = lineOfText.split(",");
          if (words.length == 3) {
            assignLineToUnit(army, words);
          } else {
            throw new IllegalArgumentException("Invalid unit.");
          }
        }
      }
    } else {
      throw new NullPointerException();
    }

    File file = new File(Objects.requireNonNull(ArmyFileHandler.class.getResource(path)).getFile());
    lastLoadedFileName = file.getName();

    lastLoadedFilePath = Objects.requireNonNull(ArmyFileHandler.class.getResource(path))
        .toExternalForm();

    return new Army(army.getName(), army.getUnits());
  }

  /**
   * Reads a csv file to path, and returns the army.
   *
   * @param path The path of the csv file.
   * @return The army read from the file.
   */
  public static Army readCsvTestFiles(String path)
      throws IOException, IllegalArgumentException, NullPointerException {
    Army army = new Army();

    Path currentPath = Path.of(path);

    try (BufferedReader reader = Files.newBufferedReader(currentPath)) {
      String lineOfText;
      if ((lineOfText = reader.readLine()) != null && !lineOfText.contains(",")) {
        army.setName(lineOfText);
      } else {
        throw new IllegalArgumentException("Error. Army name is not correct. ");
      }

      while ((lineOfText = reader.readLine()) != null) {
        String[] words = lineOfText.split(",");
        if (words.length == 3) {
          assignLineToUnit(army, words);
        } else {
          throw new IllegalArgumentException("Invalid unit.");
        }
      }
    }
    return new Army(army);
  }

  /**
   * Method takes one line of words and checks which unit it corresponds to.
   *
   * @param army  The army to the new unit to.
   * @param words The line of words possible containing a unit.
   * @throws IllegalArgumentException - If it does not find a match to the known units.
   */
  private static void assignLineToUnit(Army army, String[] words) throws IllegalArgumentException {
    if (words[0].strip().equalsIgnoreCase(InfantryUnit.class.getSimpleName())) {
      army.addUnit(new InfantryUnit(words[1].strip(), Integer.parseInt(words[2].strip())));
    } else if (words[0].strip().equalsIgnoreCase(RangedUnit.class.getSimpleName())) {
      army.addUnit(new RangedUnit(words[1].strip(), Integer.parseInt(words[2].strip())));
    } else if (words[0].strip().equalsIgnoreCase(CavalryUnit.class.getSimpleName())) {
      army.addUnit(new CavalryUnit(words[1].strip(), Integer.parseInt(words[2].strip())));
    } else if (words[0].strip().equalsIgnoreCase(CommanderUnit.class.getSimpleName())) {
      army.addUnit(new CommanderUnit(words[1].strip(), Integer.parseInt(words[2].strip())));
    } else {
      throw new IllegalArgumentException("Not a known unit. ");
    }
  }
}
