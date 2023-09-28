package neuron;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class NastroykaNeyronki {



    public static void main(String[] args) throws IOException {

        {PrintStream out = new PrintStream(new FileOutputStream("C:/ResultatPodboraVesa.txt",true));
            System.setOut(out);}

        List<Double> listPushek = new ArrayList<>();

        NeyronnaySet neyronnayaSet = new NeyronnaySet();
//        double iW2 = 0.3;
//        neyronnayaSet.initialWeight2 = iW2; //todo жесткая привязка чтоб не делать цикл в цикле 4ре раза за место трех-лучще запустить просто потом с другим параметром
//        System.out.println("initialWeight2 Начальные веса связей между скрытыми и выходным нейронами = " + iW2 );


        List<String> lines = Files.readAllLines(Paths.get("src/neuron/outputResultDlyaPorverok.txt")); // без пробелов прогрузить партия для сверки результатов- других игр
        int collStrok = lines.size();

        double[] possibleWeights = {0.1,0.12,0.14,0.16,0.18,0.20,0.22,0.24,0.26,0.28,0.3}; // todo попробовать дробить еще вдва за меньще фракцию
        //        double[] possibleWeights = {0.3};

        double[] possibleLearningRates = {0.1,0.12,0.14,0.16,0.18,0.20,0.22,0.24,0.26,0.28,0.3};
        //        double[] possibleLearningRates = {0.1};

        //        int[] possibleNumTrainingCycles = {10,50,100,150,200,250,300,350,400,450,500,700,850,1000};
        int[] possibleNumTrainingCycles = {200};

        ///
        double[] iW2 = {0.5,0.52,0.54,0.56,0.58,0.6,0.62,0.64,066,0.68,0.7};
        ////

        long startTime = System.currentTimeMillis();//todo просто замеры времени

        for (double weight : possibleWeights) {
            for (double learningRate : possibleLearningRates) {
//                for (int cycles : possibleNumTrainingCycles) {
                    for (double iw2Count : iW2) {

                    // Обновление значений параметров
                    neyronnayaSet.setInitialWeight(weight);
                    neyronnayaSet.setLearningRate(learningRate);
//                    neyronnayaSet.setNumTrainingCycles(cycles);
                    neyronnayaSet.setNumTrainingCycles(200);
                    neyronnayaSet.initialWeight2 = iw2Count;

                    neyronnayaSet.initializeNeuralNetwork();
                    neyronnayaSet.training("src/neuron/outputResult.txt"); //todo тренировка файл

                    int schetchik = 0;


                    for (String line : lines) {

                        byte[] inputValues = new byte[299];
                        for (int i = 0; i < line.length()-1; i++) {

                            inputValues[i] = Byte.parseByte(String.valueOf(line.charAt(i)));
                        }
                        byte resultikTxt = line.getBytes()[line.length() - 1];
                        // Повторное обучение нейронной сети
                        String ressSety = neyronnayaSet.run(inputValues); // проходит лишнее обучение несколько раз

                        // Оценка точности на тех же данных одной строки


                        if ((ressSety.equals("Ставим") && resultikTxt == 49 )|| (ressSety.equals("Отказываемся от ставки") && resultikTxt == 48)) { // 49 и 48 это значения 0 и 1 в байтовом виде - можно было бы сделать чар но байт легче по памяти
                            schetchik++;
                        }


                    }
                    double percent = (schetchik / (double) collStrok) * 100;

                    System.out.println("Параметры, которые достигли желаемой точности: зерно 123");
                    System.out.println("Процент правельных реешений " + percent);
                    System.out.println("Начальный вес: " + weight);
                    System.out.println("Шаг подстройки: " + learningRate);
//                    System.out.println("Количество циклов обучения: " + cycles);

                    System.out.println("initialWeight2 Начальные веса связей между скрытыми и выходным нейронами = " + iw2Count );

                    if(percent > 60.0){
                        System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii выше выйграл " + percent);
                        listPushek.add(percent);
                    }
                    else if(percent > 55.0){
                        System.out.println("Неплохооооооооооооооооооооооооооооооооо ");
                    }

                }
            }
        }

        Collections.sort(listPushek);
        System.out.println(listPushek);


        long endTime = System.currentTimeMillis(); //todo просто замеры времени
        long executionTimeMillis = endTime - startTime;

// Преобразование времени выполнения в минуты и секунды //todo просто замеры времени
        long minutes = (executionTimeMillis / 1000) / 60;
        long seconds = (executionTimeMillis / 1000) % 60;

        System.out.println("Время выполнения: " + minutes + " минут " + seconds + " секунд"); //todo просто замеры времени

    }
}
