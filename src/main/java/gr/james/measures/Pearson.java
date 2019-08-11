package gr.james.measures;

import java.util.List;
import java.util.Set;

/**
 * Pearson correlation coefficient implementation.
 */
public class Pearson<T> {
    private final double value;

    /**
     * Create a new {@link Pearson} from the given sets.
     * <p>
     * This form of Pearson correlation coefficient is identical to the Phi coefficient.
     * <p>
     * The {@code world} set must be a superset of {@code a} and {@code b} and this constructor will make no checks to
     * ensure that.
     *
     * @param a     one set
     * @param b     the other set
     * @param world the world set
     * @throws NullPointerException     if either {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     */
    public Pearson(Set<T> a, Set<T> b, Set<T> world) {
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
        final double n00 = world.size() - union;
        final double n10 = a.size() - intersection;
        final double n01 = b.size() - intersection;
        final double n0_ = world.size() - a.size();
        final double n_0 = world.size() - a.size();
        this.value = ((double) intersection * n00 - n10 * n01) /
                Math.sqrt((double) a.size() * (double) b.size() * n0_ * n_0);
        assert Double.isNaN(this.value) || (this.value >= -1 && this.value <= 1);
    }

    /**
     * Create a new {@link Pearson} from the given {@link Double} vectors.
     *
     * @param a the one vector
     * @param b the other vector
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     * @throws IllegalArgumentException if {@code a} and {@code b} are of differet size
     * @throws IllegalArgumentException if some property of {@code a} or {@code b} prevents their average values to be
     *                                  computed
     */
    public Pearson(List<Double> a, List<Double> b) {
        if (a.isEmpty() || b.isEmpty()) {
            throw new IllegalArgumentException("Inputs cannot be empty");
        }
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Inputs must have the same size");
        }

        final double averageA = a.stream().mapToDouble(x -> x).average()
                .orElseThrow(() -> new IllegalArgumentException("Can't get the average of a"));
        final double averageB = b.stream().mapToDouble(x -> x).average()
                .orElseThrow(() -> new IllegalArgumentException("Can't get the average of b"));

        double cov = 0;
        for (int i = 0; i < a.size(); i++) {
            cov += (a.get(i) - averageA) * (b.get(i) - averageB);
        }
        cov /= a.size();

        double varA = 0;
        double varB = 0;
        for (int i = 0; i < a.size(); i++) {
            varA += Math.pow(a.get(i) - averageA, 2);
            varB += Math.pow(b.get(i) - averageB, 2);
        }
        varA /= a.size();
        varB /= b.size();
        varA = Math.sqrt(varA);
        varB = Math.sqrt(varB);

        this.value = cov / (varA * varB);
        assert this.value >= -1 && this.value <= 1;
    }

    /**
     * Returns the Pearson correlation coefficient of the inputs that this instance was created from.
     *
     * @return the Pearson correlation coefficient of the inputs that this instance was created from
     */
    public double value() {
        return this.value;
    }
}
