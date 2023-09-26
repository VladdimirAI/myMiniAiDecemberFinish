package neuron;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapperDlyaTesta2 {
    public static void main(String[] args) {
        String inputFilePath = "src/neuron/output.txt";
        String outputFilePath = "src/neuron/outputResult.txt";

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


        int schetchikSovpadenyi = 0;
        int temp = 0;
        int prevTemp = 0;


        for (int i = 0; i < lines.size(); i++) {
            System.out.println(schetchikSovpadenyi);
            String currentLine = lines.get(i);

            int firstIndex = currentLine.indexOf('1');
            int secondIndex = currentLine.indexOf('1', firstIndex + 1);

            temp = secondIndex - firstIndex;

            if (temp == prevTemp) {
                schetchikSovpadenyi++;

            }else{
                schetchikSovpadenyi = 0;
            }

            prevTemp = temp;


            if(schetchikSovpadenyi <= 1){
                resultLines.add(currentLine);
            }


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
