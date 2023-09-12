package neuron;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainResult1 {
    static List<Neuron> startovieNeronyList = List.of(new Neuron(), new Neuron());
    static List<Neuron> centrovieNeronyList = List.of(new Neuron(), new Neuron());
    static Neuron FinishnuyNeiron = new Neuron();
    public static void main(String[] args) throws IOException {
        Random rnd = new Random();
        Scanner scn = new Scanner(System.in);

        for (Neuron vhodyashyiNeiron : startovieNeronyList) {
            for (Neuron hideNeuron : centrovieNeronyList) {
                vhodyashyiNeiron.strelkaMap.put(hideNeuron, rnd.nextDouble(-0.5, 0.5));
            }
        }

        for (Neuron promeghutochyiNeiron : centrovieNeronyList) {
            promeghutochyiNeiron.strelkaMap.put(FinishnuyNeiron, rnd.nextDouble(-0.5, 0.5));
        }
        training("src/neuron/training1.txt");

        System.out.print("Есть ли оружие? (введите да/нет): ");
        String val1 = scn.next();
        System.out.print("Разница уровней меньше 2? (введите да/нет): ");
        String val2 = scn.next();
        startovieNeronyList.get(0).value = val1.equalsIgnoreCase("да")?1:0;
        startovieNeronyList.get(1).value = val2.equalsIgnoreCase("да")?1:0;


        double res = calc();

        System.out.println("res = "+res);
        System.out.println(res>0.5?"Атакуем":"Бежим");


    }
    //Наличие оружия
    //Разница уровней меньше 2
    static void training(String textovyiDocument) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(textovyiDocument));
        for (int i = 0; i < 100; i++) {


            for (String line : lines) {
                String[] data = line.split(" ");
                int index = 0;
                for (int j = 0; j < startovieNeronyList.size(); j++) {
                    startovieNeronyList.get(j).value = Double.valueOf(data[index++]); // для одного стратового нейрона ставиться первое значение из строки для второговторей значение
                }
                double ogidaemyiResultat = Double.valueOf(data[index]); // результат под индексом 3
                double obshieZnachenie = calc();
                double result = obshieZnachenie > 0.5 ? 1 : 0;
                if (result != ogidaemyiResultat) {
                    raschitatVesa(obshieZnachenie, ogidaemyiResultat); // Если результат не соответствует ожидаемому, вызывается метод raschitatVesa, который корректирует веса связей между нейронами на основе ошибки.
                }
            }
        }
    }




    static void raschitatVesa(double obshieZnachenie, double ogidaemoeZnachenie){
        double error = obshieZnachenie-ogidaemoeZnachenie;
        double delta = error*(1-error);
        for (Neuron hideNeuron : centrovieNeronyList) {
            Double staryiVes = hideNeuron.strelkaMap.get(FinishnuyNeiron);
            hideNeuron.strelkaMap.put(FinishnuyNeiron, staryiVes - hideNeuron.value*delta*0.3);
        }
        for (Neuron promeghutochnyiNeiron : centrovieNeronyList) {
            double error2 = promeghutochnyiNeiron.strelkaMap.get(FinishnuyNeiron) * delta;
            double delta2 = error2*(1-error2);
            for (Neuron vhodyashiyNeiron : startovieNeronyList) {
                Double staryiVes = vhodyashiyNeiron.strelkaMap.get(promeghutochnyiNeiron);
                vhodyashiyNeiron.strelkaMap.put(promeghutochnyiNeiron, staryiVes - vhodyashiyNeiron.value*delta2*0.3);
            }
        }

    }

    static double calc(){

        for (Neuron promeghutochnyiNeiron : centrovieNeronyList) {
            double sum = 0;
            for (Neuron vhodyashyiNeiron : startovieNeronyList) {
                sum+=vhodyashyiNeiron.value*vhodyashyiNeiron.strelkaMap.get(promeghutochnyiNeiron);
            }
            promeghutochnyiNeiron.value = sigmaFuncion(sum);
        }
        double sum = 0;
        for (Neuron promeghutocnyiNeiron : centrovieNeronyList) {
            sum+=promeghutocnyiNeiron.value*promeghutocnyiNeiron.strelkaMap.get(FinishnuyNeiron);
        }
        FinishnuyNeiron.value = sigmaFuncion(sum);
        return FinishnuyNeiron.value;
    }

    static double sigmaFuncion(double obshieZnachenie){
        return 1/(1+Math.pow(Math.E, - obshieZnachenie));
    }



}

///////////////////////////////////////////////////////


//package neuron;
//
//        import java.io.IOException;
//        import java.nio.file.Files;
//        import java.nio.file.Paths;
//        import java.util.List;
//        import java.util.Random;
//        import java.util.Scanner;
//
//public class MainResult1 {
//    static List<Neuron> inputNeurons = List.of(new Neuron(), new Neuron());
//    static List<Neuron> hideNeurons = List.of(new Neuron(), new Neuron());
//    static Neuron outputNeuron = new Neuron();
//    public static void main(String[] args) throws IOException {
//        Random rnd = new Random();
//        Scanner scn = new Scanner(System.in);
//
//        for (Neuron inputNeuron : inputNeurons) {
//            for (Neuron hideNeuron : hideNeurons) {
//                inputNeuron.axons.put(hideNeuron, rnd.nextDouble(-0.5, 0.5));
//            }
//        }
//
//        for (Neuron hideNeuron : hideNeurons) {
//            hideNeuron.axons.put(outputNeuron, rnd.nextDouble(-0.5, 0.5));
//        }
//        training("src/neuron/training1.txt");
//
//        System.out.print("Есть ли оружие? (введите да/нет): ");
//        String val1 = scn.next();
//        System.out.print("Разница уровней меньше 2? (введите да/нет): ");
//        String val2 = scn.next();
//        inputNeurons.get(0).value = val1.equalsIgnoreCase("да")?1:0;
//        inputNeurons.get(1).value = val2.equalsIgnoreCase("да")?1:0;
//
//
//        double res = calc();
//
//        System.out.println("res = "+res);
//        System.out.println(res>0.5?"Атакуем":"Бежим");
//
//
//    }
//    //Наличие оружия
//    //Разница уровней меньше 2
//    static void training(String trainingFilePath) throws IOException {
//        List<String> lines = Files.readAllLines(Paths.get(trainingFilePath));
//        for (int i = 0; i < 100; i++) {
//
//
//            for (String line : lines) {
//                String[] data = line.split(" ");
//                int index = 0;
//                for (int j = 0; j < inputNeurons.size(); j++) {
//                    inputNeurons.get(j).value = Double.valueOf(data[index++]);
//                }
//                double expectedResult = Double.valueOf(data[index]);
//                double totalValue = calc();
//                double result = totalValue > 0.5 ? 1 : 0;
//                if (result != expectedResult) {
//                    resolveWeights(totalValue, expectedResult);
//                }
//            }
//        }
//    }
//
//
//
//
//    static void resolveWeights(double totalValue, double expectedValue){
//        double error = totalValue-expectedValue;
//        double delta = error*(1-error);
//        for (Neuron hideNeuron : hideNeurons) {
//            Double oldWeight = hideNeuron.axons.get(outputNeuron);
//            hideNeuron.axons.put(outputNeuron, oldWeight - hideNeuron.value*delta*0.3);
//        }
//        for (Neuron hideNeuron : hideNeurons) {
//            double error2 = hideNeuron.axons.get(outputNeuron) * delta;
//            double delta2 = error2*(1-error2);
//            for (Neuron inputNeuron : inputNeurons) {
//                Double oldWeight = inputNeuron.axons.get(hideNeuron);
//                inputNeuron.axons.put(hideNeuron, oldWeight - inputNeuron.value*delta2*0.3);
//            }
//        }
//
//    }
//
//    static double calc(){
//
//        for (Neuron hideNeuron : hideNeurons) {
//            double sum = 0;
//            for (Neuron inputNeuron : inputNeurons) {
//                sum+=inputNeuron.value*inputNeuron.axons.get(hideNeuron);
//            }
//            hideNeuron.value = sigma(sum);
//        }
//        double sum = 0;
//        for (Neuron hideNeuron : hideNeurons) {
//            sum+=hideNeuron.value*hideNeuron.axons.get(outputNeuron);
//        }
//        outputNeuron.value = sigma(sum);
//        return outputNeuron.value;
//    }
//
//    static double sigma(double totalWeight){
//        return 1/(1+Math.pow(Math.E, -totalWeight));
//    }
//
//
//
//}


