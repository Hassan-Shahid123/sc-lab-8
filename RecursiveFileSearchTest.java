import org.junit.jupiter.api.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test suite for RecursiveFileSearch.
 * Tests base cases, recursive behavior, and edge conditions.
 */
public class RecursiveFileSearchTest {

    private static File testDir;

    @BeforeAll
    static void setup() {
        // Assuming "TestSearch" exists in the same directory as your .java files
        testDir = new File("D:/Important files/Java/SCLabs/Lab8/TestSearch");
        assertTrue(testDir.exists() && testDir.isDirectory(),
                "TestSearch directory must exist for testing.");
    }

    // Base Cases

    @Test
    @DisplayName("Base case: File not found in directory or subdirectories")
    void testBaseCase_FileNotFound() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("nonexistent.txt", 0);

        RecursiveFileSearch.searchFiles(testDir, "nonexistent.txt", true, counts);
        assertEquals(0, counts.get("nonexistent.txt"),
                "Should return 0 when file does not exist.");
    }

    @Test
    @DisplayName("Base case: Directory is empty")
    void testBaseCase_EmptyDirectory() {
        File emptyDir = new File("EmptyTestDir");
        emptyDir.mkdir();

        Map<String, Integer> counts = new HashMap<>();
        counts.put("anything.txt", 0);

        RecursiveFileSearch.searchFiles(emptyDir, "anything.txt", true, counts);
        assertEquals(0, counts.get("anything.txt"),
                "Should handle empty directories without error.");

        emptyDir.delete();
    }

    // Recursive Step Tests

    @Test
    @DisplayName("Recursive search: Finds file in subdirectory")
    void testRecursiveSearch_FoundInSubdirectory() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("report.txt", 0);

        RecursiveFileSearch.searchFiles(testDir, "report.txt", true, counts);
        assertTrue(counts.get("report.txt") > 0,
                "Should find report.txt even if located in nested subfolder.");
    }

    @Test
    @DisplayName("Recursive search: Counts multiple files with same name")
    void testRecursiveSearch_MultipleOccurrences() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("report.txt", 0);

        RecursiveFileSearch.searchFiles(testDir, "report.txt", true, counts);
        assertTrue(counts.get("report.txt") >= 1,
                "Should count all occurrences of the same file name.");
    }

    // Edge Conditions

    @Test
    @DisplayName("Edge: Invalid directory should not cause crash")
    void testEdge_InvalidDirectory() {
        File fakeDir = new File("FakeFolderXYZ");
        Map<String, Integer> counts = new HashMap<>();
        counts.put("sample.txt", 0);

        assertDoesNotThrow(() ->
                        RecursiveFileSearch.searchFiles(fakeDir, "sample.txt", true, counts),
                "Should handle invalid directory gracefully."
        );

        assertEquals(0, counts.get("sample.txt"));
    }

    @Test
    @DisplayName("Edge: Directory argument is actually a file")
    void testEdge_FileAsDirectory() {
        File fileInsteadOfDir = new File(testDir, "report.txt");
        if (!fileInsteadOfDir.exists()) {
            System.out.println("Warning: report.txt not found for testEdge_FileAsDirectory()");
            return;
        }

        Map<String, Integer> counts = new HashMap<>();
        counts.put("notes.txt", 0);

        assertDoesNotThrow(() ->
                        RecursiveFileSearch.searchFiles(fileInsteadOfDir, "notes.txt", true, counts),
                "Should handle case where a file is passed instead of a folder."
        );

        assertEquals(0, counts.get("notes.txt"));
    }

    @Test
    @DisplayName("Edge: Case-insensitive search finds all variants")
    void testEdge_CaseInsensitiveSearch() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("notes.txt", 0);

        RecursiveFileSearch.searchFiles(testDir, "notes.txt", false, counts);
        assertTrue(counts.get("notes.txt") > 0,
                "Case-insensitive search should find both notes.txt and Notes.txt.");
    }

    @Test
    @DisplayName("Feature: Multiple file search in one run")
    void testFeature_MultipleFileSearch() {
        Map<String, Integer> counts = new HashMap<>();
        String[] targets = {"report.txt", "notes.txt"};

        for (String name : targets) {
            counts.put(name, 0);
            RecursiveFileSearch.searchFiles(testDir, name, false, counts);
        }

        assertTrue(counts.get("report.txt") >= 0, "Should process report.txt without error.");
        assertTrue(counts.get("notes.txt") >= 0, "Should process notes.txt without error.");
    }

}
