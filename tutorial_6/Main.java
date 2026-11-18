import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static ArrayList<String> loadTasks(Path file) throws IOException {
        ArrayList<String> tasks = new ArrayList<>();
        if (!Files.exists(file)) return tasks;
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) tasks.add(line);
            }
        }
        return tasks;
    }

    public static void saveTasks(Path file, List<String> tasks) throws IOException {
        Path destination = file.toAbsolutePath();
        Path temporary = Files.createTempFile(destination.getParent(), "tasks-", ".tmp");
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(temporary, StandardCharsets.UTF_8)) {
                for (String task : tasks) {
                    writer.write(task);
                    writer.newLine();
                }
            }
            // Replace the old list only after the entire new list has been written.
            try {
                Files.move(temporary, destination, StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(temporary, destination, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    private static void displayTasks(List<String> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public static void main(String[] args) {
        Path file = Paths.get(args.length == 0 ? "task.txt" : args[0]);
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            ArrayList<String> tasks = loadTasks(file);
            if (!tasks.isEmpty()) {
                System.out.println("===== PREVIOUS TASKS =====");
                displayTasks(tasks);
            }

            System.out.println("===== ADD TASKS =====");
            for (int i = 1; i <= 3; i++) {
                System.out.print("Enter Task " + i + ": ");
                if (!scanner.hasNextLine()) {
                    System.out.println("\nInput ended. No changes were saved.");
                    return;
                }
                String task = scanner.nextLine().trim();
                if (task.isEmpty()) {
                    System.out.println("Task cannot be blank. Please try again.");
                    i--;
                    continue;
                }
                tasks.add(task);
            }

            System.out.println("\n===== TASK LIST =====");
            displayTasks(tasks);
            saveTasks(file, tasks);
            System.out.println("\nTasks saved successfully.");
            System.out.println("\n===== TASKS LOADED FROM FILE =====");
            displayTasks(loadTasks(file));
        } catch (IOException ex) {
            System.err.println("Unable to load or save tasks: " + ex.getMessage());
            System.exit(1);
        }
    }
}
