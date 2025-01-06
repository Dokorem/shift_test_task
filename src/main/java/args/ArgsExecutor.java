package args;

import com.beust.jcommander.JCommander;
import files.FileManager;

import java.util.ArrayList;
import java.util.List;

public class ArgsExecutor {

    private final List<String> inputFileNames;

    private final GivenArgsProperties providedArgumentsProperties;

    public ArgsExecutor() {
        inputFileNames = new ArrayList<>();
        providedArgumentsProperties = new GivenArgsProperties();
    }

    public void executeProvidedArgs(String[] args) {
        initArgumentsProperties(args);
        filterFileNames(providedArgumentsProperties.getFiles());
        processFiles();
    }

    private void initArgumentsProperties(String[] args) {
        JCommander.newBuilder()
                .addObject(providedArgumentsProperties)
                .build()
                .parse(args);
    }

    private void filterFileNames(List<String> fileNames) {
        List<String> validFileNames = fileNames.stream()
                .filter(this::isValidTxtFile)
                .toList();

        inputFileNames.addAll(validFileNames);
    }

    private boolean isValidTxtFile(String fileName) {
        if (!fileName.endsWith(".txt")) {
            printInvalidFileMessage(fileName);
            return false;
        }
        return true;
    }

    private void printInvalidFileMessage(String fileName) {
        System.err.printf("Передан несуществующий аргумент, либо файл с расширением не .txt! - %s%n", fileName);
    }

    private void processFiles() {
        FileManager fileManager = new FileManager(providedArgumentsProperties, inputFileNames);
        fileManager.processFiles();
    }

}
