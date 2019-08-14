package gr.james.measures;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleFunction;

/**
 * Pearson correlation coefficient implementation.
 */
public class Pearson {
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
     * @param <T>   the type of elements in the inputs
     * @throws NullPointerException     if either {@code a}, {@code b} or {@code world} is {@code null}
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     */
    public <T> Pearson(Set<T> a, Set<T> b, Set<T> world) {
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
     * @throws NullPointerException     if either {@code a} or {@code b} is {@code null}
     * @throws NullPointerException     if any element inside {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if either {@code a} or {@code b} is empty
     * @throws IllegalArgumentException if {@code a} and {@code b} are of different size
     * @throws IllegalArgumentException if some property of {@code a} or {@code b} prevents their average values to be
     *                                  computed
     */
    public Pearson(List<Double> a, List<Double> b) {
        if (a.isEmpty() || b.isEmpty()) {
            throw new IllegalArgumentException("Inputs cannot be empty");
        }

        final double averageA = a.stream().mapToDouble(x -> x).average()
                .orElseThrow(() -> new IllegalArgumentException("Can't get the average of a"));
        final double averageB = b.stream().mapToDouble(x -> x).average()
                .orElseThrow(() -> new IllegalArgumentException("Can't get the average of b"));

        double cov = 0;
        double varA = 0;
        double varB = 0;
        final Iterator<Double> aIterator = a.iterator();
        final Iterator<Double> bIterator = b.iterator();
        while (aIterator.hasNext() && bIterator.hasNext()) {
            final Double aNext = aIterator.next() - averageA;
            final Double bNext = bIterator.next() - averageB;
            cov += aNext * bNext;
            varA += Math.pow(aNext, 2);
            varB += Math.pow(bNext, 2);
        }
        if (aIterator.hasNext() || bIterator.hasNext()) {
            throw new IllegalArgumentException("Inputs must have the same size");
        }

        cov /= a.size();
        varA /= a.size();
        varB /= b.size();

        varA = Math.sqrt(varA);
        varB = Math.sqrt(varB);

        this.value = cov / (varA * varB);
        assert this.value >= -1 && this.value <= 1;
    }

    /**
     * Create a new {@link Pearson} from the given arguments.
     *
     * @param population the population set
     * @param mapping1   a function representing one input
     * @param mapping2   a function representing one input
     * @param <T>        the type of elements in the inputs
     * @throws NullPointerException     if any input is {@code null}
     * @throws NullPointerException     if any value returned from either {@code mapping1} or {@code mapping2} is
     *                                  {@code null}
     * @throws IllegalArgumentException if {@code population} is empty
     * @throws IllegalArgumentException if some property of {@code mapping1} or {@code mapping2} prevents their average
     *                                  values to be computed
     * @throws RuntimeException         as propagated from the {@link ToDoubleFunction#applyAsDouble(Object)} method
     */
    public <T> Pearson(Set<T> population, ToDoubleFunction<T> mapping1, ToDoubleFunction<T> mapping2) {
        if (population.isEmpty()) {
            throw new IllegalArgumentException("Inputs cannot be empty");
        }

        final double averageA = population.stream().mapToDouble(mapping1).average()
                .orElseThrow(() -> new IllegalArgumentException("Can't get the average of a"));
        final double averageB = population.stream().mapToDouble(mapping2).average()
                .orElseThrow(() -> new IllegalArgumentException("Can't get the average of b"));

        double cov = 0;
        double varA = 0;
        double varB = 0;
        for (T t : population) {
            cov += (mapping1.applyAsDouble(t) - averageA) * (mapping2.applyAsDouble(t) - averageB);
            varA += Math.pow(mapping1.applyAsDouble(t) - averageA, 2);
            varB += Math.pow(mapping2.applyAsDouble(t) - averageB, 2);
        }

        cov /= population.size();
        varA /= population.size();
        varB /= population.size();

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
