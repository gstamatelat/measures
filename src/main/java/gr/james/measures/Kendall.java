package gr.james.measures;

import java.util.List;

/**
 * Kendall rank correlation coefficient implementation.
 */
public class Kendall {
    private final double value;

    /**
     * Create a new {@link Kendall} from the given vectors.
     *
     * @param a   the one vector
     * @param b   the other vector
     * @param <T> the type of elements in the inputs
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     * @throws IllegalArgumentException if {@code a} and {@code b} are of different size
     */
    public <T extends Comparable<T>> Kendall(List<T> a, List<T> b) {
        if (a.isEmpty() || b.isEmpty()) {
            throw new IllegalArgumentException("Inputs cannot be empty");
        }
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Inputs must have the same size");
        }

        long num = 0;
        long tiesA = 0;
        long tiesB = 0;
        for (int i = 0; i < a.size() - 1; i++) {
            for (int j = i + 1; j < a.size(); j++) {
                int signA = Integer.signum(a.get(i).compareTo(a.get(j)));
                int signB = Integer.signum(b.get(i).compareTo(b.get(j)));
                if (signA == 0) {
                    tiesA++;
                }
                if (signB == 0) {
                    tiesB++;
                }
                num += signA * signB;
            }
        }

        long n = ((long) a.size() * ((long) a.size() - 1)) / 2;

        this.value = num / (Math.sqrt(n - tiesA) * Math.sqrt(n - tiesB));
        assert this.value >= -1 - 1e-4 && this.value <= 1 + 1e-4;
    }

    /**
     * Returns the Kendall rank correlation coefficient of the inputs that this instance was created from.
     *
     * @return the Kendall rank correlation coefficient of the inputs that this instance was created from
     */
    public double value() {
        return this.value;
    }
}
