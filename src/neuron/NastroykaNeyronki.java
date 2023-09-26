//package neuron;
//
//public class NastroykaNeyronki {
//    public static void main(String[] args) {
//        NeuralNetwork neuralNetwork = new NeuralNetwork();
//
//        for (double weight : possibleWeights) {
//            for (double learningRate : possibleLearningRates) {
//                for (int cycles : possibleNumTrainingCycles) {
//                    // Обновление значений параметров
//                    neuralNetwork.setInitialWeight(weight);
//                    neuralNetwork.setLearningRate(learningRate);
//                    neuralNetwork.setNumTrainingCycles(cycles);
//
//                    // Повторное обучение нейронной сети
//                    neuralNetwork.train(trainingData);
//
//                    // Оценка точности на тех же данных
//                    double accuracy = neuralNetwork.evaluate(testData);
//
//                    if (accuracy >= 0.7) {
//                        System.out.println("Параметры, которые достигли желаемой точности:");
//                        System.out.println("Начальный вес: " + weight);
//                        System.out.println("Шаг подстройки: " + learningRate);
//                        System.out.println("Количество циклов обучения: " + cycles);
//                        return;
//                    }
//                }
//            }
//        }
//
//        System.out.println("Ни одна комбинация параметров не достигла желаемой точности.");
//
//    }
//}
