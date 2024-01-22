package pl.danwys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        File inputFile = setInputFile(args);
        int inputFileSedolColumnIndex = setInputFileSedolColumnIndex(args);

        File invalidSedolsFile = createOutputFile();
        Scanner inputFileScanner = new Scanner(inputFile);

        String inputFileLine;
        String sedol;
        while (inputFileScanner.hasNextLine()) {
            inputFileLine = inputFileScanner.nextLine();
            sedol = getSedolFromFileLine(inputFileLine, inputFileSedolColumnIndex);
            if (!SedolValidator.isValid(sedol)) {
                appendLineWithInvalidSedolToFile(inputFileLine, invalidSedolsFile);
            }
        }
    }
    private static File setInputFile(String[] args) {
        File inputFile;
        if (args.length != 2) { // no or invalid number of cmd args - default value
            inputFile = new File("sedols.csv");
        } else {
            inputFile = new File(args[0]);
        }
        return inputFile;
    }
    private static int setInputFileSedolColumnIndex(String[] args) {
        int inputFileSedolColumnIndex;
        if (args.length != 2) { // no or invalid number of cmd args - default value
            inputFileSedolColumnIndex = 0;
        } else {
            inputFileSedolColumnIndex = Integer.parseInt(args[1]);
        }
        return inputFileSedolColumnIndex;
    }

    private static File createOutputFile() throws IOException {
        String today = LocalDate.now().toString();
        Path outputFile = Path.of("invalid sedols " + today + ".csv");
        if (!Files.exists(outputFile)) {
            Files.createFile(outputFile);
        }
        return outputFile.toFile();
    }

    private static String getSedolFromFileLine(String fileLine, int inputFileSedolColumnIndex) {
        String[] fileLineFieldValues = fileLine.split(",");
        return fileLineFieldValues[inputFileSedolColumnIndex];
    }

    private static void appendLineWithInvalidSedolToFile(String inputFileLine, File invalidSedolsFile) throws IOException {
        inputFileLine = inputFileLine + "\n";
        Files.writeString(invalidSedolsFile.toPath(), inputFileLine, StandardOpenOption.APPEND);
    }


}
