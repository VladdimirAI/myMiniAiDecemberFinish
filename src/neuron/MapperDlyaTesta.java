package neuron;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapperDlyaTesta {
    public static void main(String[] args) {
        String inputFilePath = "src/neuron/input.txt";
        String outputFilePath = "src/neuron/output.txt";

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

        boolean deleteNextNineLines = false;
        boolean deleteCurrentLine = false;
        boolean removeStrokiVishe=false;
        int igraStr = 0;


        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);

//            if (deleteCurrentLine) {
                deleteCurrentLine = false;
//                continue;
//            }

            if (currentLine.contains("жен")) {
                deleteCurrentLine = true;
                deleteNextNineLines = true;
                igraStr = 0;
            } else if (i%10 == 0) {
                deleteCurrentLine = true;
                igraStr= 0;
            } else if (currentLine.startsWith("1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1") || i%10==7 || i%10==8 || i%10==9) {
                deleteCurrentLine = true;
            }
            if (currentLine.startsWith("1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1") && i%10 == 9){

                removeStrokiVishe = true;
            }

            if (!deleteCurrentLine) {
                resultLines.add(currentLine);
                igraStr++;
            }

            if (deleteNextNineLines) {
                deleteNextNineLines = false;
                i += 8;
            }
            if(removeStrokiVishe){
                removeStrokiVishe = false;
                int size = resultLines.size();
                resultLines.subList(size - igraStr, size).clear();

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
