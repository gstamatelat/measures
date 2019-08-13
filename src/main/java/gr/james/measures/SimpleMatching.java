package gr.james.measures;

import java.util.Set;

/**
 * Simple Matching coefficient implementation.
 */
public class SimpleMatching {
    private final double value;

    /**
     * Create a new {@link SimpleMatching} from the given sets.
     * <p>
     * The {@code world} set must be a superset of {@code a} and {@code b} and this constructor will make no checks to
     * ensure that.
     *
     * @param a     one set
     * @param b     the other set
     * @param world the world set
     * @param <T>   the type of elements in the inputs
     * @throws NullPointerException     if any input is {@code null}
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     */
    public <T> SimpleMatching(Set<T> a, Set<T> b, Set<T> world) {
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
        int union = big.size();
        for (T t : small) {
            if (big.contains(t)) {
                intersection++;
            } else {
                union++;
            }
        }
        this.value = (double) (world.size() - union + intersection) / (double) world.size();
        assert this.value >= 0 && this.value <= 1;
    }

    /**
     * Returns the Simple Matching coefficient of the two sets that this instance was created from.
     *
     * @return the Simple Matching coefficient of the two sets that this instance was created from
     */
    public double value() {
        return this.value;
    }
}
