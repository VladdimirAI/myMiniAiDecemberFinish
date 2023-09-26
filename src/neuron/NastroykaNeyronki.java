package neuron;

import org.w3c.dom.ls.LSOutput;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class NastroykaNeyronki {

    {
        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream("C:/ResultatyObuchaneironki.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.setOut(out);}

    public static void main(String[] args) throws IOException {
        NeyronnaySet neyronnayaSet = new NeyronnaySet();
        List<String> lines = Files.readAllLines(Paths.get("src/neuron/outputResultDlyaPorverok.txt")); // без пробелов прогрузить
        int collStrok = lines.size();

//        double[] possibleWeights = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
        double[] possibleWeights = {0.3};
        double[] possibleLearningRates = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
        int[] possibleNumTrainingCycles = {50,100,150,200,300,400,500,600,700,800,900,1000,2000,3000};



        for (double weight : possibleWeights) {
            for (double learningRate : possibleLearningRates) {
                for (int cycles : possibleNumTrainingCycles) {
                    // Обновление значений параметров
                    neyronnayaSet.setInitialWeight(weight);
                    neyronnayaSet.setLearningRate(learningRate);
                    neyronnayaSet.setNumTrainingCycles(cycles);

                    int schetchik = 0;


                    for (String line : lines) {

                        byte[] inputValues = new byte[299];
                        for (int i = 0; i < line.length()-1; i++) {
//                            System.out.println(line.length());
//                            System.out.println(i);
                            inputValues[i] = Byte.parseByte(String.valueOf(line.charAt(i)));
                        }
                        byte resultikTxt = line.getBytes()[line.length() - 1];
                        // Повторное обучение нейронной сети
                        String ressSety = neyronnayaSet.run(inputValues);

                        // Оценка точности на тех же данных одной строки

                        if (ressSety.equals("Ставим") && resultikTxt == 1 || ressSety.equals("Отказываемся от ставки") && resultikTxt == 0) {
                            schetchik++;
                        }


                    }
                    double percent = (schetchik / (double) collStrok) * 100;

                    System.out.println("Параметры, которые достигли желаемой точности: зерно 123");
                    System.out.println("Процент правельных реешений " + percent);
                    System.out.println("Начальный вес: " + weight);
                    System.out.println("Шаг подстройки: " + learningRate);
                    System.out.println("Количество циклов обучения: " + cycles);

                }
            }
        }


//        System.out.println("Ни одна комбинация параметров не достигла желаемой точности.");

    }
}
//"Ставим" : "Отказываемся от ставки";

//    List<String> lines = Files.readAllLines(Paths.get(trainingFilePath));
//    String[] data = line.split(" ");