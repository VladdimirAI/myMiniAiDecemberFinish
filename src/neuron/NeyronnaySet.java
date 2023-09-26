package neuron;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class NeyronnaySet {
    static List<Neuron> inputNeurons = new ArrayList<>();
    static List<Neuron> hideNeurons = new ArrayList<>();
    static Neuron outputNeuron = new Neuron();


    double initialWeight; // Начальные веса нейронов
    double learningRate; // Шаг обучения
    int numTrainingCycles; // Количество циклов обучения

    public static void main(String[] args) throws IOException {


        NeyronnaySet neyronnaySet = new NeyronnaySet();
        neyronnaySet.run();

    }


    public void run() throws IOException {
        Random rnd = new Random();

        // Создание входных нейронов
        for (int i = 0; i < 229; i++) {
            inputNeurons.add(new Neuron());
        }

        // Создание скрытых нейронов
        for (int i = 0; i < 2; i++) {
            hideNeurons.add(new Neuron());
        }

        // Инициализация связей между входными и скрытыми нейронами
        for (Neuron inputNeuron : inputNeurons) {
            for (Neuron hideNeuron : hideNeurons) {
                inputNeuron.strelkaMap.put(hideNeuron, rnd.nextDouble(-0.3, 0.3));
            }
        }

        // Инициализация связей между скрытыми и выходным нейронами
        for (Neuron hideNeuron : hideNeurons) {
            hideNeuron.strelkaMap.put(outputNeuron, rnd.nextDouble(-0.3, 0.3));
        }

        training("src/neuron/outputResult.txt"); //todo тренировка файл
//
//        byte[] inputValues = {1, 0, 1, 0, 1}; // Здесь пример значений для всех 229 входных нейронов


        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку значений для всех 299 входных нейронов: ");
        String inputString = scanner.nextLine();

// Преобразование введенной строки в массив байтов
        byte[] inputValues = new byte[299];
        for (int i = 0; i < inputString.length(); i++) {
            System.out.println(i);
            inputValues[i] = Byte.parseByte(String.valueOf(inputString.charAt(i)));
        }

        setInputValues(inputValues);


        double res = calc();

        System.out.println("res = " + res);
        System.out.println(res > 0.5 ? "Ставим" : "Отказываемся от ставки");
    }

    void setInputValues(byte[] values) {
        if (values.length != 299) {
            throw new IllegalArgumentException("Количество значений не соответствует количеству входных нейронов");
        }

        for (int i = 0; i < inputNeurons.size(); i++) {
            inputNeurons.get(i).value = values[i] != 0 ? 1 : 0;
        }
    }


    void training(String trainingFilePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(trainingFilePath));
        for (int i = 0; i < 200; i++) {
            for (String line : lines) {
                String[] data = line.split(" ");
                int index = 0;
                for (int j = 0; j < inputNeurons.size(); j++) {
                    inputNeurons.get(j).value = Double.valueOf(data[index++]);
                }
                double expectedResult = Double.valueOf(data[index]);
                double totalValue = calc();
                double result = totalValue > 0.5 ? 1 : 0;
                if (result != expectedResult) {
                    resolveWeights(totalValue, expectedResult);
                }
            }
        }
    }

    void resolveWeights(double totalValue, double expectedValue) {
        double error = totalValue - expectedValue;
        double delta = error * (1 - error);
        for (Neuron hideNeuron : hideNeurons) {
            Double oldWeight = hideNeuron.strelkaMap.get(outputNeuron);
            hideNeuron.strelkaMap.put(outputNeuron, oldWeight - hideNeuron.value * delta * 0.3);
        }
        for (Neuron hideNeuron : hideNeurons) {
            double error2 = hideNeuron.strelkaMap.get(outputNeuron) * delta;
            double delta2 = error2 * (1 - error2);
            for (Neuron inputNeuron : inputNeurons) {
                Double oldWeight = inputNeuron.strelkaMap.get(hideNeuron);
                inputNeuron.strelkaMap.put(hideNeuron, oldWeight - inputNeuron.value * delta2 * 0.3);
            }
        }
    }

    double calc() {
        for (Neuron hideNeuron : hideNeurons) {
            double sum = 0;
            int index = 0;
            for (Neuron inputNeuron : inputNeurons) {
                sum += inputNeuron.value * inputNeuron.strelkaMap.get(hideNeuron);
                index++;
            }
            hideNeuron.value = sigma(sum);
        }

        double sum = 0;
        for (Neuron hideNeuron : hideNeurons) {
            sum += hideNeuron.value * hideNeuron.strelkaMap.get(outputNeuron);
        }
        outputNeuron.value = sigma(sum);

        return outputNeuron.value;
    }

    double sigma(double totalWeight) {
        return 1 / (1 + Math.pow(Math.E, -totalWeight));
    }
}