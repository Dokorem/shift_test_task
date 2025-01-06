package files;

import args.GivenArgsProperties;
import statistic.StatisticManager;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private final List<String> inputFileNames = new ArrayList<>();

    private final List<String> strings = new ArrayList<>();
    private final List<BigDecimal> numbers = new ArrayList<>();
    private final List<BigDecimal> floats = new ArrayList<>();

    private final String outputFileRootPath;
    private final boolean supplementTheOutputFiles;

    private final StatisticManager statisticManager;

    public FileManager(GivenArgsProperties argumentsProperties, List<String> providedFilesNames) {
        inputFileNames.addAll(providedFilesNames);

        outputFileRootPath = argumentsProperties.getOutputFilePath() + File.separator
                + argumentsProperties.getOutputFilePrefix();
        supplementTheOutputFiles = argumentsProperties.isSupplementTheOutputFiles();
        statisticManager = StatisticManager.builder()
                .isFullStatisticRequired(argumentsProperties.isFullStatisticRequired())
                .isShortStatisticRequired(argumentsProperties.isShortStatisticRequired())
                .strings(strings)
                .numbers(numbers)
                .floats(floats)
                .build();
    }

    public void processFiles() {
        if (!inputFileNames.isEmpty()) {
            readDataFromFilesToLists();
        }
        if (!supplementTheOutputFiles) {
            createOutputFiles();
        }
        writeDataToOutputFiles();
        statisticManager.showStatistic();
    }

    private void readDataFromFilesToLists() {
        for (String fileName : inputFileNames) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    classifyData(line);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void classifyData(String line) {
        if (line.matches("-?\\d+")) {
            numbers.add(new BigDecimal(line));
        } else if (line.matches("-?\\d+(\\.\\d+)?([eE][-+]?\\d+)?")) {
            floats.add(new BigDecimal(line));
        } else {
            strings.add(line);
        }
    }

    private void createOutputFiles() {
        if (!strings.isEmpty()) {
            createDataFile(DataFilesConstants.STRING_FILE_NAME);
        }
        if (!numbers.isEmpty()) {
            createDataFile(DataFilesConstants.NUMBERS_FILE_NAME);
        }
        if (!floats.isEmpty()) {
            createDataFile(DataFilesConstants.FLOAT_NUMBERS_FILE_NAME);
        }
    }

    private void createDataFile(String outputFileName) {
        try {
            Path stringsOutputFilePath = Path.of(outputFileRootPath + outputFileName);
            Files.deleteIfExists(stringsOutputFilePath);
            Files.createFile(stringsOutputFilePath);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void writeDataToOutputFiles() {
        if (!strings.isEmpty()) {
            copyLinesToDataFile(DataFilesConstants.STRING_FILE_NAME, strings);
        }
        if (!numbers.isEmpty()) {
            copyLinesToDataFile(DataFilesConstants.NUMBERS_FILE_NAME, numbers);
        }
        if (!floats.isEmpty()) {
            copyLinesToDataFile(DataFilesConstants.FLOAT_NUMBERS_FILE_NAME, floats);
        }
    }

    private <T> void copyLinesToDataFile(String outputFileName, List<T> dataArray) {
        Path outputFilePath = Path.of(outputFileRootPath + outputFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            for (T value : dataArray) {
                writer.write(value.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.printf("Файл с таким путем не найден, информацию записать невозможно! \"Path + %s\"%n", e.getMessage());
        }
    }

}
