package no.ntnu.idatg2001.wargames.model.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import no.ntnu.idatg2001.wargames.model.armies.Army;
import no.ntnu.idatg2001.wargames.model.battles.Battle;
import no.ntnu.idatg2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatg2001.wargames.model.units.CommanderUnit;
import no.ntnu.idatg2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatg2001.wargames.model.units.RangedUnit;
import no.ntnu.idatg2001.wargames.model.units.Unit;

public class ArmyFileHandler {
  private static int startLine;

  /**
   * Private constructor to hide the implicit public one.
   */
  private ArmyFileHandler() {

  }

  private static void clearFile(String path) throws  IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      writer.write("");
    }
  }


  /**
   * Writes an army to a file in path.
   * @param army The army to write in the file.
   * @param path The path of the file to write to.
   */
  public static void writeArmyCsv(Army army, String path) throws IOException {
    clearFile(path);
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path))) {
      writer.write(army.getName() + "\n");
      for (Unit unit : army.getUnits()) {
        writer.write(
            unit.getClass().getSimpleName() + "," + unit.getName() + "," + unit.getHealth() + "\n");
      }
    }
  }

  private static void writeArmy(BufferedWriter writer, Army army, String path) throws IOException {
    clearFile(path);
    writer.write(army.getName() + "\n");
    for (Unit unit : army.getUnits()) {
      writer.write(
          unit.getClass().getSimpleName() + "," + unit.getName() + "," + unit.getHealth() + "\n");
    }
    writer.write("\n");

  }

  public static void writeBattle(Battle battle, String path) throws IOException {
    try (FileWriter fileWriter = new FileWriter(path, true);
         BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
      writeArmy(bufferedWriter, battle.getArmyOne(), path);
      writeArmy(bufferedWriter, battle.getArmyTwo(), path);
    }
  }

  public static Battle readBattle(String path) throws IOException {
    Battle battle = new Battle(readArmy(path), readArmy(path));
    startLine = 0;
    return battle;
  }



  private static Army readArmy(String path) throws IOException {
    Army army = new Army();
    int currentLine = 0;
    try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(Path.of(path)), startLine)) {
      String lineOfText;

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
        startLine++;
      }
    }
    return army;
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
