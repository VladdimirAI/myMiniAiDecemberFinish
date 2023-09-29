package neuron;

import java.math.BigDecimal;
import java.util.Scanner;

public class DelatDuubleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите начальное число: ");
        double start = scanner.nextDouble();

        System.out.print("Введите конечное число: ");
        double end = scanner.nextDouble();

        System.out.print("Введите шаг: ");
        double step = scanner.nextDouble();

        BigDecimal number = BigDecimal.valueOf(start);
        BigDecimal endBigDecimal = BigDecimal.valueOf(end);
        BigDecimal stepBigDecimal = BigDecimal.valueOf(step);

        while (number.compareTo(endBigDecimal) <= 0) {
            System.out.print(number);

            if (number.add(stepBigDecimal).compareTo(endBigDecimal) < 0) {
                System.out.print(", ");
            }

            number = number.add(stepBigDecimal);
        }
    }
}
