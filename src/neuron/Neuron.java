package neuron;

import java.util.HashMap;
import java.util.Map;

public class Neuron {
    double value; // текущее значение нейрона и может быть использовано для передачи и обработки данных в контексте нейронной сети.
    Map<Neuron, Double> strelkaMap = new HashMap<>(); // Каждая запись в мапе axons представляет связь между текущим нейроном и другим нейроном, где ключом является нейрон, на который ссылается текущий нейрон, а значением - вес или сила этой связи.

}
