package gr.james.measures;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Cosine similarity implementation.
 */
public class Cosine<T> {
    private final double value;

    /**
     * Create a new {@link Cosine} from the given sets.
     * <p>
     * This form of Cosine similarity is identical to the Otsuka-Ochiai coefficient.
     *
     * @param a one set
     * @param b the other set
     * @throws NullPointerException     if either {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     */
    public Cosine(Set<T> a, Set<T> b) {
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
        this.value = (double) intersection / Math.sqrt((double) small.size() * (double) big.size());
        assert this.value >= 0 && this.value <= 1;
    }

    /**
     * Create a new {@link Cosine} from the given {@link Double} vectors.
     *
     * @param a the one vector
     * @param b the other vector
     * @throws NullPointerException     if either {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     * @throws IllegalArgumentException if {@code a} and {@code b} are of different size
     */
    public Cosine(List<Double> a, List<Double> b) {
        this(a.iterator(), b.iterator());
    }

    /**
     * Create a new {@link Cosine} from the given {@link Double} {@link Iterator iterators}.
     * <p>
     * This constructor will exhaust the iterators.
     *
     * @param a the one iterator
     * @param b the other iterator
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     * @throws IllegalArgumentException if {@code a} and {@code b} are of different size
     */
    public Cosine(Iterator<Double> a, Iterator<Double> b) {
        if (!a.hasNext() || !b.hasNext()) {
            throw new IllegalArgumentException("Inputs cannot be empty");
        }

        double numerator = 0;
        double denominatorA = 0;
        double denominatorB = 0;
        while (a.hasNext() && b.hasNext()) {
            double aNext = a.next();
            double bNext = b.next();
            numerator += aNext * bNext;
            denominatorA += Math.pow(aNext, 2);
            denominatorB += Math.pow(bNext, 2);
        }

        if (a.hasNext() || b.hasNext()) {
            throw new IllegalArgumentException("Inputs must have the same size");
        }

        this.value = numerator / (Math.sqrt(denominatorA) * Math.sqrt(denominatorB));
        assert this.value >= 0 && this.value <= 1;
    }

    /**
     * Returns the Cosine similarity of the inputs that this instance was created from.
     *
     * @return the Cosine similarity of the inputs that this instance was created from
     */
    public double value() {
        return this.value;
    }
}
