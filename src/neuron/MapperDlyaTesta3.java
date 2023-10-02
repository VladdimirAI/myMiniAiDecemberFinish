package neuron;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapperDlyaTesta3 {
    public static void main(String[] args) {
//        String inputFilePath = "src/neuron/outputResult.txt";
//        String outputFilePath = "src/neuron/outputResultDlyaPorverok.txt";

        String inputFilePath = "src/neuron/bezSplita2.txt";
        String outputFilePath = "src/neuron/soSplitom2.txt";

        List<String> lines = readLinesFromFile(inputFilePath);

        List<String> resultLines = processLines(lines);

        writeLinesToFile(resultLines, outputFilePath);
    }

    private static List<String> readLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private static List<String> processLines(List<String> lines) {
        List<String> resultLines = new ArrayList<>();

        for (String line : lines) {
            String processedLine = line.replaceAll(" ", ""); // Удаление пробелов из строки
            resultLines.add(processedLine);
        }

        return resultLines;
    }

    private static void writeLinesToFile(List<String> lines, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
