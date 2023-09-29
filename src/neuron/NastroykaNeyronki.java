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

        double[] possibleWeights = {0.07, 0.072, 0.074, 0.076, 0.078, 0.080, 0.082, 0.084, 0.086, 0.088,0.090}; // todo попробовать дробить еще вдва за меньще фракцию
        //        double[] possibleWeights = {0.3};

        double[] possibleLearningRates = {0.19, 0.191, 0.192, 0.193, 0.194, 0.195, 0.196, 0.197, 0.198, 0.199, 0.200, 0.201, 0.202, 0.203, 0.204, 0.205, 0.206, 0.207, 0.208, 0.209,0.210};
        //        double[] possibleLearningRates = {0.1};

        //        int[] possibleNumTrainingCycles = {10,50,100,150,200,250,300,350,400,450,500,700,850,1000};
        int[] possibleNumTrainingCycles = {200};

        ///
        double[] iW2 = {0.5, 0.505, 0.510, 0.515, 0.520, 0.525, 0.530, 0.535, 0.540, 0.545, 0.550, 0.555,0.560};
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
                    else if(percent > 62.0){
                        System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj ура прорыв");
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
//todo ошибиться  выйгрышных тоесть когда не поставил на выйгрышн - так же можно сделать в 12 потоков - собирая результаты одновременно например с 01  ивсе остальные значения потом 02 и все остальные масивы 04 и т.д.   убрать из тренинга сплит