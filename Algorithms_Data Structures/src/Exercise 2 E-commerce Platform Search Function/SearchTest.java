package search;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class SearchTest {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("        EXERCISE 2: E-COMMERCE PRODUCT SEARCH     ");
        System.out.println("==================================================");

        // 1. Correctness Testing
        System.out.println("\n--- Part 1: Correctness Verification ---");
        Product[] testProducts = {
            new Product("P005", "Wireless Mouse", "Electronics"),
            new Product("P001", "Laptop", "Electronics"),
            new Product("P003", "Mechanical Keyboard", "Electronics"),
            new Product("P002", "Coffee Mug", "Kitchenware"),
            new Product("P004", "Running Shoes", "Apparel")
        };

        System.out.println("Unsorted Products (for Linear Search):");
        for (Product p : testProducts) {
            System.out.println("  " + p);
        }

        // Test Linear Search
        String searchTarget1 = "P003";
        String searchTarget2 = "P999"; // Non-existent

        int linearIdx1 = SearchAlgorithms.linearSearch(testProducts, searchTarget1);
        int linearIdx2 = SearchAlgorithms.linearSearch(testProducts, searchTarget2);

        System.out.println("\nLinear Search Results:");
        System.out.println("  Searching for '" + searchTarget1 + "': " + (linearIdx1 != -1 ? "Found at index " + linearIdx1 + " (" + testProducts[linearIdx1] + ")" : "Not Found"));
        System.out.println("  Searching for '" + searchTarget2 + "': " + (linearIdx2 != -1 ? "Found at index " + linearIdx2 : "Not Found"));

        // Sort products for Binary Search
        Product[] sortedProducts = testProducts.clone();
        Arrays.sort(sortedProducts);

        System.out.println("\nSorted Products (for Binary Search):");
        for (Product p : sortedProducts) {
            System.out.println("  " + p);
        }

        // Test Binary Search
        int binaryIdx1 = SearchAlgorithms.binarySearch(sortedProducts, searchTarget1);
        int binaryIdx2 = SearchAlgorithms.binarySearch(sortedProducts, searchTarget2);

        System.out.println("\nBinary Search Results:");
        System.out.println("  Searching for '" + searchTarget1 + "': " + (binaryIdx1 != -1 ? "Found at index " + binaryIdx1 + " (" + sortedProducts[binaryIdx1] + ")" : "Not Found"));
        System.out.println("  Searching for '" + searchTarget2 + "': " + (binaryIdx2 != -1 ? "Found at index " + binaryIdx2 : "Not Found"));


        // 2. Performance Comparison (Benchmarking)
        System.out.println("\n--- Part 2: Benchmarking / Performance Analysis ---");
        int databaseSize = 10000;
        int lookupIterations = 50000;
        System.out.println("Generating " + databaseSize + " products...");

        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < databaseSize; i++) {
            // Generate IDs like P00001, P00002, etc. (padded to sort correctly lexicographically)
            String id = String.format("P%05d", i);
            productList.add(new Product(id, "Product " + i, "Category " + (i % 5)));
        }

        // Create an unsorted array by shuffling
        List<Product> shuffledList = new ArrayList<>(productList);
        Collections.shuffle(shuffledList);
        Product[] unsortedArray = shuffledList.toArray(new Product[0]);

        // Create a sorted array
        Product[] sortedArray = productList.toArray(new Product[0]);

        // Generate target IDs to search for (mix of existing and non-existing)
        String[] targets = new String[lookupIterations];
        for (int i = 0; i < lookupIterations; i++) {
            if (i % 10 == 0) {
                targets[i] = "P99999"; // Non-existent target (worst-case search)
            } else {
                int randomId = (int) (Math.random() * databaseSize);
                targets[i] = String.format("P%05d", randomId);
            }
        }

        System.out.println("Running " + lookupIterations + " search iterations...");

        // Benchmark Linear Search
        long startTime = System.nanoTime();
        long linearFoundCount = 0;
        for (String target : targets) {
            int idx = SearchAlgorithms.linearSearch(unsortedArray, target);
            if (idx != -1) {
                linearFoundCount++;
            }
        }
        long linearDuration = System.nanoTime() - startTime;

        // Benchmark Binary Search
        startTime = System.nanoTime();
        long binaryFoundCount = 0;
        for (String target : targets) {
            int idx = SearchAlgorithms.binarySearch(sortedArray, target);
            if (idx != -1) {
                binaryFoundCount++;
            }
        }
        long binaryDuration = System.nanoTime() - startTime;

        // Display results
        System.out.println("\nPerformance Summary:");
        System.out.printf("  %-15s : %.3f ms (Total items searched: %d)\n", "Linear Search", (linearDuration / 1_000_000.0), linearFoundCount);
        System.out.printf("  %-15s : %.3f ms (Total items searched: %d)\n", "Binary Search", (binaryDuration / 1_000_000.0), binaryFoundCount);
        System.out.printf("  Speedup Factor  : %.1fx faster with Binary Search\n", (double) linearDuration / binaryDuration);
        System.out.println("==================================================");
    }
}
