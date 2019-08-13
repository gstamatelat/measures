package gr.james.measures;

import java.util.Set;

/**
 * Overlap coefficient implementation.
 */
public class Overlap {
    private final double value;

    /**
     * Create a new {@link Overlap} from the given sets.
     *
     * @param a   one set
     * @param b   the other set
     * @param <T> the type of elements in the inputs
     * @throws NullPointerException     if either {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     */
    public <T> Overlap(Set<T> a, Set<T> b) {
        if (a.isEmpty() || b.isEmpty()) {
            throw new IllegalArgumentException("Inputs cannot be empty");
        }
        Set<T> big;
        Set<T> small;
        if (a.size() > b.size()) {
            big = a;
            small = b;
        } else {
            big = b;
            small = a;
        }
        int intersection = 0;
        for (T t : small) {
            if (big.contains(t)) {
                intersection++;
            }
        }
        this.value = (double) intersection / (double) small.size();
        assert this.value >= 0 && this.value <= 1;
    }

    /**
     * Returns the Overlap coefficient of the two sets that this instance was created from.
     *
     * @return the Overlap coefficient of the two sets that this instance was created from
     */
    public double value() {
        return this.value;
    }
}
