package neuron;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeyronnaySet {


     List<Neuron> inputNeurons = new ArrayList<>();
     List<Neuron> hideNeurons = new ArrayList<>();
     Neuron outputNeuron = new Neuron();


    double initialWeight ; // Начальные веса связей между входными и скрытыми нейронами
    double initialWeight2 ; // Начальные веса связей между скрытыми и выходным нейронами //todo поправить для задания в обучалке
    double learningRate ; // Шаг обучения
    int numTrainingCycles ; // Количество циклов обучения





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


        setInputValues(inputValues);


        double res = calc();


       return res > 0.5 ? "Ставим" : "Отказываемся от ставки";
    }


    public void initializeNeuralNetwork(){

        Random rnd = new Random(123); // Зерно рандома
            inputNeurons = new ArrayList<>();
        // Создание входных нейронов
        for (int i = 0; i < 299; i++) {
            inputNeurons.add(new Neuron());
        }
            hideNeurons = new ArrayList<>();
        // Создание скрытых нейронов
        for (int i = 0; i < 2; i++) { // todo число средних нейронов пока что 2
            hideNeurons.add(new Neuron());
        }

        // Инициализация связей между входными и скрытыми нейронами
        for (Neuron inputNeuron : inputNeurons) {
            for (Neuron hideNeuron : hideNeurons) {
                inputNeuron.strelkaMap.put(hideNeuron, rnd.nextDouble(-initialWeight, initialWeight));
            }
        }

        // Инициализация связей между скрытыми и выходным нейронами
        for (Neuron hideNeuron : hideNeurons) {
            hideNeuron.strelkaMap.put(outputNeuron, rnd.nextDouble(-initialWeight2, initialWeight2));
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
            // Создайте отдельный список, чтобы не изменять исходный список строк
            List<String> linesCopy = new ArrayList<>(lines);
            for (String line : linesCopy) {
                strokaNomer++;
                String[] data = line.split(" ");
                int index = 0;
                for (int j = 0; j < inputNeurons.size(); j++) {
                    try {
                        inputNeurons.get(j).value = Double.valueOf(data[index++]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Строка " + strokaNomer + " I номер " + i + "index =" + index + " inputNeurons.size() = "+ inputNeurons.size());
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


//    void resolveWeights(double totalValue, double expectedValue) { //todo пробовать этот код для 299 скрытых
//        double error = totalValue - expectedValue;
//
//        // Обновление весов для связей между скрытыми и выходным нейронами
//        for (Neuron hideNeuron : hideNeurons) {
//            Double oldWeight = hideNeuron.strelkaMap.get(outputNeuron);
//            hideNeuron.strelkaMap.put(outputNeuron, oldWeight - hideNeuron.value * error * learningRate * initialWeight2);
//        }
//
//        // Обновление весов для связей между входными и скрытыми нейронами
//        for (Neuron hideNeuron : hideNeurons) {
//            double error2 = hideNeuron.strelkaMap.get(outputNeuron) * error;
//            double delta2 = error2 * (1 - error2) * learningRate;
//
//            for (Neuron inputNeuron : inputNeurons) {
//                Double oldWeight = inputNeuron.strelkaMap.get(hideNeuron);
//                inputNeuron.strelkaMap.put(hideNeuron, oldWeight - inputNeuron.value * delta2 * initialWeight);
//            }
//        }
//    }




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


//Вы считываете строки из файла.
//Затем для каждой строки разделяете ее на отдельные значения с помощью split(" "), предполагая, что значения разделены пробелами.
//Далее происходит присваивание значений входным нейронам (inputNeurons). Поскольку у вас есть массив с 300 байтами, вы выполняете цикл по размеру inputNeurons и присваиваете значения из data путем преобразования их в Double.
//Затем вы определяете ожидаемый результат (expectedResult) как последний элемент в data путем преобразования его в Double.
//После этого вы вызываете calc() для вычисления общего значения и принимаете решение на основе порогового значения 0.5.
//Если результат не соответствует ожидаемому результату, то вызывается resolveWeights для подстройки весов.
//Таким образом, ваш метод training кажется правильным для вашей задачи обучения нейронной сети с использованием данных из текстового файла.

//todo проверка на пустые строки в самом конце файлов!!!