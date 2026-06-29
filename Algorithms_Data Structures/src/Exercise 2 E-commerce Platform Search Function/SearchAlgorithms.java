package search;

public class SearchAlgorithms {

    /**
     * Performs a linear search on the products array for a product with the specified productId.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param products  the array of products (does not need to be sorted)
     * @param targetId  the productId to search for
     * @return the index of the product if found, or -1 if not found
     */
    public static int linearSearch(Product[] products, String targetId) {
        if (products == null || targetId == null) {
            return -1;
        }
        for (int i = 0; i < products.length; i++) {
            if (products[i] != null && targetId.equals(products[i].getProductId())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Performs a binary search on the sorted products array for a product with the specified productId.
     * Note: The array must be sorted in ascending order of productId.
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     *
     * @param products  the sorted array of products
     * @param targetId  the productId to search for
     * @return the index of the product if found, or -1 if not found
     */
    public static int binarySearch(Product[] products, String targetId) {
        if (products == null || targetId == null) {
            return -1;
        }
        int low = 0;
        int high = products.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = products[mid].getProductId().compareTo(targetId);

            if (comparison == 0) {
                return mid; // Found
            } else if (comparison < 0) {
                low = mid + 1; // Target is in the right half
            } else {
                high = mid - 1; // Target is in the left half
            }
        }
        return -1; // Not found
    }
}
