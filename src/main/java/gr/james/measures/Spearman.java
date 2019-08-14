package gr.james.measures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spearman's rank correlation coefficient implementation.
 */
public class Spearman {
    private final double value;

    /**
     * Create a new {@link Spearman} from the given ranks.
     *
     * @param a   the one rank vector
     * @param b   the other rank vector
     * @param <T> the type of elements in the inputs
     * @throws NullPointerException     if either {@code a} or {@code b} is {@code null}
     * @throws NullPointerException     if either {@code a} or {@code b} contain {@code null} values
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     * @throws IllegalArgumentException if {@code a} and {@code b} contain duplicate elements
     * @throws IllegalArgumentException if {@code a} and {@code b} do not contain exactly the same elements
     */
    public <T> Spearman(List<T> a, List<T> b) {
        if (a.isEmpty() || b.isEmpty()) {
            throw new IllegalArgumentException("Inputs cannot be empty");
        }

        final Map<T, Integer[]> reverseIndex = new HashMap<>();
        int indexCount;

        indexCount = 1;
        for (T t : a) {
            final Integer[] previous = reverseIndex.put(t, new Integer[]{indexCount, null});
            if (previous != null) {
                throw new IllegalArgumentException("List contains duplicate elements");
            }
            indexCount++;
        }

        indexCount = 1;
        for (T t : b) {
            final Integer[] previous = reverseIndex.get(t);
            if (previous == null) {
                throw new IllegalArgumentException("Inputs must have exactly the same elements");
            }
            if (previous[1] != null) {
                throw new IllegalArgumentException("List contains duplicate elements");
            }
            previous[1] = indexCount;
            indexCount++;
        }

        this.value = new Pearson(reverseIndex.keySet(),
                v -> reverseIndex.get(v)[0],
                v -> reverseIndex.get(v)[1]
        ).value();
    }

    /**
     * Returns the Spearman's rank correlation coefficient of the inputs that this instance was created from.
     *
     * @return the Spearman's rank correlation coefficient of the inputs that this instance was created from
     */
    public double value() {
        return this.value;
    }
}
