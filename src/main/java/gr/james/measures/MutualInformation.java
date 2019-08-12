package gr.james.measures;

import java.util.Set;

/**
 * Normalized mutual information implementation.
 */
public class MutualInformation<T> {
    private final double normalizedMutualInformation;
    private final double mutualInformation;

    /**
     * Create a new {@link MutualInformation} from the given sets.
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
    public MutualInformation(Set<T> a, Set<T> b, Set<T> world) {
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

        final double N11 = intersection;
        final double N00 = world.size() - union;
        final double N10 = a.size() - intersection;
        final double N01 = b.size() - intersection;
        final double N0X = world.size() - a.size();
        final double NX0 = world.size() - b.size();
        final double NX1 = b.size();
        final double N1X = a.size();
        final double N = world.size();

        final double pmi1 = (1.0 * N11 / N) * Math.log((1.0 * N * N11) / (1.0 * N1X * NX1)) / Math.log(2);
        final double pmi2 = (1.0 * N01 / N) * Math.log((1.0 * N * N01) / (1.0 * N0X * NX1)) / Math.log(2);
        final double pmi3 = (1.0 * N10 / N) * Math.log((1.0 * N * N10) / (1.0 * N1X * NX0)) / Math.log(2);
        final double pmi4 = (1.0 * N00 / N) * Math.log((1.0 * N * N00) / (1.0 * N0X * NX0)) / Math.log(2);

        final double entropy1 = (1.0 * N0X / N) * Math.log(1.0 * N0X / N) / Math.log(2) +
                (1.0 * N1X / N) * Math.log(1.0 * N1X / N) / Math.log(2);

        final double entropy2 = (1.0 * NX0 / N) * Math.log(1.0 * NX0 / N) / Math.log(2) +
                (1.0 * NX1 / N) * Math.log(1.0 * NX1 / N) / Math.log(2);

        final double mi = (Double.isNaN(pmi1) ? 0.0 : pmi1) +
                (Double.isNaN(pmi2) ? 0.0 : pmi2) +
                (Double.isNaN(pmi3) ? 0.0 : pmi3) +
                (Double.isNaN(pmi4) ? 0.0 : pmi4);

        this.mutualInformation = mi;
        this.normalizedMutualInformation = -(2 * mi) / (entropy1 + entropy2);

        assert this.normalizedMutualInformation >= 0 - 1e-4 && this.normalizedMutualInformation <= 1 + 1e-4;
        assert this.mutualInformation >= 0;
    }

    /**
     * Returns the Normalized mutual information of the inputs that this instance was created from.
     *
     * @return the Normalized mutual information of the inputs that this instance was created from
     */
    public double normalizedMutualInformation() {
        return this.normalizedMutualInformation;
    }

    /**
     * Returns the Mutual information of the inputs that this instance was created from.
     *
     * @return the Mutual information of the inputs that this instance was created from
     */
    public double mutualInformation() {
        return this.mutualInformation;
    }
}
