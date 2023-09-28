package neuron;

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

        for (double number = start; number <= end; number += step) {
            System.out.print(number);
            if (number + step <= end) {
                System.out.print(", ");
            }
        }
    }
}
