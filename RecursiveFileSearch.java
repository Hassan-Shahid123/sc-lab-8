import java.io.File;
import java.util.*;

/**
 * This program recursively searches for one or more files
 * within a specified directory and its subdirectories.
 *
 * Usage Example:
 * java RecursiveFileSearch "C:\\Users\\Hassan\\Documents" report.txt notes.txt false
 */
public class RecursiveFileSearch {

    /**
     * Main method to parse command-line arguments and start the search.
     * @param args Command-line arguments:
     *             args[0] = directory path
     *             args[1..n-2] = filenames to search
     *             args[n-1] = case sensitivity flag ("true" or "false")
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java RecursiveFileSearch <directory> <file1> [<file2> ...] <caseSensitive>");
            return;
        }

        String dirPath = args[0];
        boolean caseSensitive = Boolean.parseBoolean(args[args.length - 1]);

        // Extract file names (all args except first and last)
        List<String> fileNames = new ArrayList<>();
        for (int i = 1; i < args.length - 1; i++) {
            fileNames.add(args[i]);
        }

        File directory = new File(dirPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Error: Invalid directory path - " + dirPath);
            return;
        }

        // Map to track counts of each found file
        Map<String, Integer> fileCounts = new HashMap<>();

        // Perform recursive search for each file
        for (String fileName : fileNames) {
            fileCounts.put(fileName, 0);
            searchFiles(directory, fileName, caseSensitive, fileCounts);
        }

        // Display total results
        System.out.println("\nSearch Summary");
        for (String fileName : fileNames) {
            int count = fileCounts.get(fileName);
            System.out.println(fileName + " found " + count + " time(s).");
        }
    }

    /**
     * Recursively searches for the given file within the specified directory.
     *
     * @param dir            The directory to search in.
     * @param targetFileName The name of the file to search for.
     * @param caseSensitive  Whether the search should be case-sensitive.
     * @param fileCounts     Map storing counts of found files.
     *
     */
    public static void searchFiles(File dir, String targetFileName, boolean caseSensitive, Map<String, Integer> fileCounts) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isFile()) {
                boolean match = caseSensitive
                        ? file.getName().equals(targetFileName)
                        : file.getName().equalsIgnoreCase(targetFileName);

                if (match) {
                    System.out.println("Found: " + file.getAbsolutePath());
                    fileCounts.put(targetFileName, fileCounts.get(targetFileName) + 1);
                }
            } else if (file.isDirectory()) {
                // Recursive call
                searchFiles(file, targetFileName, caseSensitive, fileCounts);
            }
        }
    }
}
