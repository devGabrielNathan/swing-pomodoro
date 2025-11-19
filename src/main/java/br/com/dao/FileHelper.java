package br.com.dao;

import br.com.exceptions.IntegrationException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class FileHelper {
    private static final Logger logger = Logger.getLogger(FileHelper.class.getName());

    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            logger.warning("File not found: " + filePath);
            throw new IntegrationException();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
        return lines;
    }

    public static <T> List<T> readObjects(String filePath, Function<String, T> parser) {
        List<String> lines = readLines(filePath);
        List<T> objects = new ArrayList<>();
        for (String line : lines) {
            try {
                T obj = parser.apply(line);
                if (obj != null) objects.add(obj);
            } catch (Exception e) {
                logger.warning("Parser failed for line: " + line + " -> " + e.getMessage());
            }
        }
        return objects;
    }

    // TODO: Usar DefaultTableModel ao invés de um arquivo temporário
    public static void writeLines(String filePath, List<String> lines, String tempFilename) {
        File inputFile = new File(filePath);
        File tempFile = new File(tempFilename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }

        if (!inputFile.delete()) {
            logger.warning("Could not delete original file: " + filePath);
            throw new IntegrationException();
        }
        if (!tempFile.renameTo(inputFile)) {
            logger.warning("Could not rename temp file to original file name: " + filePath);
            throw new IntegrationException();
        }
    }

    // TODO: Usar DefaultTableModel ao invés de um arquivo temporário
    public static <T> void writeObjects(String filePath, List<T> objects, Function<T, String> formatter, String tempFilename) {
        List<String> lines = new ArrayList<>();
        for (T obj : objects) {
            try {
                lines.add(formatter.apply(obj));
            } catch (Exception e) {
                logger.warning("Formatter failed for object: " + e.getMessage());
            }
        }
        writeLines(filePath, lines, tempFilename);
    }

    public static void appendLine(String filePath, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
    }

    public static long getNextId(String filePath, int expectedFields) {
        try {
            List<String> lines = readLines(filePath);
            long max = 0L;
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length < expectedFields) continue;
                long id = Long.parseLong(parts[0]);
                if (id > max) max = id;
            }
            return max + 1;
        } catch (Exception e) {
            throw new IntegrationException();
        }
    }
}
