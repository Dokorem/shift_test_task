package statistic;

import lombok.Builder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Builder
public class StatisticManager {

    private final boolean isFullStatisticRequired;
    private final boolean isShortStatisticRequired;

    private final List<String> strings;
    private final List<BigDecimal> numbers;
    private final List<BigDecimal> floats;

    public void showStatistic() {
        if (isFullStatisticRequired && isShortStatisticRequired) {
            chooseStatisticType();
        } else if (isFullStatisticRequired) {
            displayFullStatistic();
        } else if (isShortStatisticRequired) {
            displayShortStatistic();
        }
    }

    private void chooseStatisticType() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Выберите тип статистики (Full или Short): ");

            while (true) {
                String choice = scanner.nextLine().trim().toLowerCase();

                if (choice.startsWith("f")) {
                    displayFullStatistic();
                    return;
                } else if (choice.startsWith("sh")) {
                    displayShortStatistic();
                    return;
                } else {
                    System.out.println("Некорректный ввод. Пожалуйста, выберите 'Полную' или 'Краткую'.");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nВыбрано завершение программы, до свидания!");
        }
    }

    private void displayShortStatistic() {
        ShortStatisticEntity shortStatistic = ShortStatisticEntity.builder()
                .stringsCount(strings.size())
                .numbersCount(numbers.size())
                .floatsCount(floats.size())
                .build();

        System.out.println(shortStatistic);
    }

    private void displayFullStatistic() {
        List<BigDecimal> stringLengths = strings.stream()
                .map(String::length)
                .map(BigDecimal::new)
                .toList();

        FullStatisticEntity fullStatisticEntity = FullStatisticEntity.builder()
                .numbersCount(numbers.size())
                .minNumberValue(getMin(numbers))
                .numbersAverageValue(getAverage(numbers))
                .maxNumberValue(getMax(numbers))
                .floatsCount(floats.size())
                .minFloatsValue(getMin(floats))
                .floatsAverageValue(getAverage(floats))
                .maxFloatsValue(getMax(floats))
                .stringsCount(strings.size())
                .mostShortWordLength(getMin(stringLengths))
                .mostLongWordLength(getMax(stringLengths))
                .build();

        System.out.println(fullStatisticEntity);
    }

    private BigDecimal getMin(List<BigDecimal> numbers) {
        return numbers.stream()
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getAverage(List<BigDecimal> numbers) {
        if (numbers.isEmpty()) return BigDecimal.ZERO;

        BigDecimal sum = numbers.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(BigDecimal.valueOf(numbers.size()), RoundingMode.HALF_UP);
    }

    private BigDecimal getMax(List<BigDecimal> numbers) {
        return numbers.stream()
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }
}
