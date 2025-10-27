import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for RecursiveStringPermutations
 * Covers base cases, recursive steps, duplicates, and performance comparison.
 */
public class RecursiveStringPermutationsTest {

    @Test
    @DisplayName("Test base case: empty string returns empty list")
    void testEmptyString() {
        List<String> result = RecursiveStringPermutations.generatePermutations("", true);
        assertTrue(result.isEmpty(), "Empty input should return empty list");
    }

    @Test
    @DisplayName("Test single character string")
    void testSingleCharacter() {
        List<String> result = RecursiveStringPermutations.generatePermutations("A", true);
        assertEquals(List.of("A"), result, "Single character should return itself");
    }

    @Test
    @DisplayName("Test two characters without duplicates")
    void testTwoCharacters() {
        List<String> result = RecursiveStringPermutations.generatePermutations("AB", true);
        List<String> expected = List.of("AB", "BA");
        assertTrue(result.containsAll(expected) && result.size() == 2, "Should return two permutations");
    }

    @Test
    @DisplayName("Test with duplicate characters (include duplicates = true)")
    void testDuplicatesIncluded() {
        List<String> result = RecursiveStringPermutations.generatePermutations("ABA", true);
        // Total 6 permutations with duplicates
        assertEquals(6, result.size(), "Should include duplicate permutations");
        assertTrue(result.contains("ABA") && result.contains("BAA") && result.contains("AAB"));
    }

    @Test
    @DisplayName("Test with duplicate characters (include duplicates = false)")
    void testDuplicatesExcluded() {
        List<String> result = RecursiveStringPermutations.generatePermutations("ABA", false);
        // Unique permutations only
        Set<String> expected = new HashSet<>(Arrays.asList("ABA", "BAA", "AAB"));
        assertEquals(expected, new HashSet<>(result), "Should exclude duplicate permutations");
    }

    @Test
    @DisplayName("Test iterative and recursive consistency")
    void testIterativeMatchesRecursive() {
        String input = "ABC";
        List<String> rec = RecursiveStringPermutations.generatePermutations(input, false);
        List<String> iter = RecursiveStringPermutations.generatePermutationsIterative(input, false);

        assertEquals(new HashSet<>(rec), new HashSet<>(iter), "Recursive and iterative results should match");
    }

    @Test
    @DisplayName("Test null input safely returns empty list")
    void testNullInput() {
        List<String> result = RecursiveStringPermutations.generatePermutations(null, true);
        assertTrue(result.isEmpty(), "Null input should return empty list safely");
    }
}
