package gr.james.measures;

import java.util.Set;

/**
 * Sorensen-Dice coefficient implementation.
 */
public class Sorensen<T> {
    private final double value;

    /**
     * Create a new {@link Sorensen} from the given sets.
     *
     * @param a one set
     * @param b the other set
     * @throws NullPointerException     if either {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     */
    public Sorensen(Set<T> a, Set<T> b) {
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
        this.value = (double) intersection * 2.0 / (double) (small.size() + big.size());
        assert this.value >= 0 && this.value <= 1;
    }

    /**
     * Returns the Sorensen-Dice coefficient of the two sets that this instance was created from.
     *
     * @return the Sorensen-Dice coefficient of the two sets that this instance was created from
     */
    public double value() {
        return this.value;
    }
}
