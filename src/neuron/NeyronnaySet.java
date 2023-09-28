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


    double initialWeight ; // Начальные веса связей между входными и скрытыми нейронами
//    double initialWeight2 = 0.3; // Начальные веса связей между скрытыми и выходным нейронами
    double initialWeight2 = initialWeight; // Начальные веса связей между скрытыми и выходным нейронами
    double learningRate ; // Шаг обучения
    int numTrainingCycles ; // Количество циклов обучения

//    neuralNetwork.setInitialWeight(0.3); // Задайте желаемые начальные веса
//    neuralNetwork.setLearningRate(0.1); // Задайте желаемый шаг обучения
//    neuralNetwork.setNumTrainingCycles(200); // Задайте желаемое количество циклов обучения


    public static void main(String[] args) throws IOException {


//        NeyronnaySet neyronnaySet = new NeyronnaySet();
//        neyronnaySet.run();

    }




    public void setInitialWeight(double initialWeight) {
        this.initialWeight = initialWeight;
    }


    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }


    public void setNumTrainingCycles(int numTrainingCycles) {
        this.numTrainingCycles = numTrainingCycles;
    }











    public String run(byte[] inputValues) throws IOException {

//
//        Random rnd = new Random(123); // Зерно рандома
//
//        // Создание входных нейронов
//        for (int i = 0; i < 299; i++) {
//            inputNeurons.add(new Neuron());
//        }
//
//        // Создание скрытых нейронов
//        for (int i = 0; i < 2; i++) {
//            hideNeurons.add(new Neuron());
//        }
//
//        // Инициализация связей между входными и скрытыми нейронами
//        for (Neuron inputNeuron : inputNeurons) {
//            for (Neuron hideNeuron : hideNeurons) {
//                inputNeuron.strelkaMap.put(hideNeuron, rnd.nextDouble(-0.3, 0.3));
//            }
//        }
//
//        // Инициализация связей между скрытыми и выходным нейронами
//        for (Neuron hideNeuron : hideNeurons) {
//            hideNeuron.strelkaMap.put(outputNeuron, rnd.nextDouble(-0.3, 0.3));
//        }

//        training("src/neuron/outputResult.txt"); //todo тренировка файл





        setInputValues(inputValues);


        double res = calc();

//        System.out.println("res = " + res); // todo отладка
       return res > 0.5 ? "Ставим" : "Отказываемся от ставки";
    }


    public void initializeNeuralNetwork(){

        Random rnd = new Random(123); // Зерно рандома

        // Создание входных нейронов
        for (int i = 0; i < 299; i++) {
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
        for (int i = 0; i < numTrainingCycles; i++) {
            int strokaNomer = 0;
            for (String line : lines) {
              strokaNomer++;
                String[] data = line.split(" ");
                int index = 0;
                for (int j = 0; j < inputNeurons.size(); j++) {

                    try {
                        inputNeurons.get(j).value = Double.valueOf(data[index++]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Строка "+ strokaNomer + " I nomer " + i);
                        throw new RuntimeException(e);
                    }
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
    //Вы считываете строки из файла.
    //Затем для каждой строки разделяете ее на отдельные значения с помощью split(" "), предполагая, что значения разделены пробелами.
    //Далее происходит присваивание значений входным нейронам (inputNeurons). Поскольку у вас есть массив с 300 байтами, вы выполняете цикл по размеру inputNeurons и присваиваете значения из data путем преобразования их в Double.
    //Затем вы определяете ожидаемый результат (expectedResult) как последний элемент в data путем преобразования его в Double.
    //После этого вы вызываете calc() для вычисления общего значения и принимаете решение на основе порогового значения 0.5.
    //Если результат не соответствует ожидаемому результату, то вызывается resolveWeights для подстройки весов.
    //Таким образом, ваш метод training кажется правильным для вашей задачи обучения нейронной сети с использованием данных из текстового файла.

    void resolveWeights(double totalValue, double expectedValue) {
        double error = totalValue - expectedValue;
        double delta = error * (1 - error) * learningRate;
        for (Neuron hideNeuron : hideNeurons) {
            Double oldWeight = hideNeuron.strelkaMap.get(outputNeuron);
            hideNeuron.strelkaMap.put(outputNeuron, oldWeight - hideNeuron.value * delta * initialWeight2);
        }
        for (Neuron hideNeuron : hideNeurons) {
            double error2 = hideNeuron.strelkaMap.get(outputNeuron) * delta;
            double delta2 = error2 * (1 - error2) * learningRate;
            for (Neuron inputNeuron : inputNeurons) {
                Double oldWeight = inputNeuron.strelkaMap.get(hideNeuron);
                inputNeuron.strelkaMap.put(hideNeuron, oldWeight - inputNeuron.value * delta2 * initialWeight);
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

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Введите строку значений для всех 299 входных нейронов: ");
//        String inputString = scanner.nextLine();
//
//// Преобразование введенной строки в массив байтов
//        byte[] inputValues = new byte[299];
//        for (int i = 0; i < inputString.length(); i++) {
//            System.out.println(i);
//            inputValues[i] = Byte.parseByte(String.valueOf(inputString.charAt(i)));
//        }

//todo проверка на пустые строки в самом конце файлов!!!