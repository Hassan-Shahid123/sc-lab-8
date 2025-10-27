import java.util.*;

/**
 * Generates all permutations of a given string using recursion
 * (and also a non-recursive version for comparison).
 */
public class RecursiveStringPermutations {

    /**
     * Main method — takes input from user and prints results.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = sc.nextLine();

        if (input.isEmpty()) {
            System.out.println("Error: Input string cannot be empty.");
            return;
        }

        System.out.print("Include duplicate permutations? (true/false): ");
        boolean includeDuplicates = sc.nextBoolean();

        // Recursive Approach
        long startRec = System.nanoTime();
        List<String> recursivePerms = generatePermutations(input, includeDuplicates);
        long endRec = System.nanoTime();

        System.out.println("\nRecursive permutations (" + recursivePerms.size() + "):");
        System.out.println(recursivePerms);

        // Iterative Approach
        long startIter = System.nanoTime();
        List<String> iterativePerms = generatePermutationsIterative(input, includeDuplicates);
        long endIter = System.nanoTime();

        System.out.println("\nIterative permutations (" + iterativePerms.size() + "):");
        System.out.println(iterativePerms);

        // Performance Comparison
        System.out.printf("%nRecursive time: %.4f ms%n", (endRec - startRec) / 1_000_000.0);
        System.out.printf("Iterative time: %.4f ms%n", (endIter - startIter) / 1_000_000.0);
    }

    /**
     * Recursively generates all permutations of a string.
     * @param str Input string
     * @param includeDuplicates Whether to include duplicate permutations
     * @return List of all permutations
     */
    public static List<String> generatePermutations(String str, boolean includeDuplicates) {
        List<String> result = new ArrayList<>();
        if (str == null || str.isEmpty()) return result;

        permute("", str, result);

        if (!includeDuplicates) {
            return new ArrayList<>(new LinkedHashSet<>(result)); // removes duplicates, keeps order
        }
        return result;
    }

    // Helper recursive method
    private static void permute(String prefix, String remaining, List<String> result) {
        if (remaining.isEmpty()) {
            result.add(prefix);
            return;
        }

        for (int i = 0; i < remaining.length(); i++) {
            char ch = remaining.charAt(i);
            String next = remaining.substring(0, i) + remaining.substring(i + 1);
            permute(prefix + ch, next, result); // recursive call
        }
    }

    /**
     * Non-recursive (iterative) method using Heap’s Algorithm.
     * @param str Input string
     * @param includeDuplicates Whether to include duplicate permutations
     * @return List of all permutations
     */
    public static List<String> generatePermutationsIterative(String str, boolean includeDuplicates) {
        List<String> result = new ArrayList<>();
        if (str == null || str.isEmpty()) return result;

        char[] arr = str.toCharArray();
        int n = arr.length;
        int[] c = new int[n];
        result.add(new String(arr));

        int i = 0;
        while (i < n) {
            if (c[i] < i) {
                if (i % 2 == 0) swap(arr, 0, i);
                else swap(arr, c[i], i);
                result.add(new String(arr));
                c[i]++;
                i = 0;
            } else {
                c[i] = 0;
                i++;
            }
        }

        if (!includeDuplicates) {
            return new ArrayList<>(new LinkedHashSet<>(result));
        }
        return result;
    }

    // Utility method to swap two characters in a char array
    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
