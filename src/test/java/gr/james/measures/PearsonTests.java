package gr.james.measures;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PearsonTests {
    /**
     * Must be 1 with same inputs.
     */
    @Test
    public void identity() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        final Set<Integer> b = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        final Set<Integer> world = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Assert.assertEquals(1.0, new Pearson(a, b, world).value(), 1e-8);
    }

    /**
     * NaN when inputs are the world.
     */
    @Test
    public void nan() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        final Set<Integer> b = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        final Set<Integer> world = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Assert.assertTrue(Double.isNaN(new Pearson(a, b, world).value()));
    }

    /**
     * Must be 0 if intersection is 0.
     */
    @Test
    public void zero() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> b = new HashSet<>(Arrays.asList(3, 4));
        final Set<Integer> world = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Assert.assertEquals(-1.0, new Pearson(a, b, world).value(), 1e-8);
    }

    /**
     * Some intermediate intersection for sets.
     */
    @Test
    public void normalSet() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3));
        final Set<Integer> b = new HashSet<>(Arrays.asList(3, 4, 5));
        final Set<Integer> world = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Assert.assertEquals(-1.0 / 3.0, new Pearson(a, b, world).value(), 1e-8);
    }

    /**
     * Commutativity for sets.
     */
    @Test
    public void commutativitySet() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> b = new HashSet<>(Arrays.asList(2, 3, 4));
        final Set<Integer> world = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Assert.assertEquals(new Pearson(b, a, world).value(), new Pearson(a, b, world).value(), 1e-8);
        Assert.assertEquals(0.0, new Pearson(a, b, world).value(), 1e-8);
    }

    /**
     * Some intermediate intersection for vectors.
     */
    @Test
    public void normalList() {
        final List<Double> a = Arrays.asList(1.0, 2.0, 3.0);
        final List<Double> b = Arrays.asList(3.0, 4.0, 5.0);
        Assert.assertEquals(1.0, new Pearson(a, b).value(), 1e-8);
    }

    /**
     * Commutativity for vectors.
     */
    @Test
    public void commutativityList() {
        final List<Double> a = Arrays.asList(1.0, 2.0, 4.0);
        final List<Double> b = Arrays.asList(2.0, 4.0, 4.0);
        Assert.assertEquals(new Pearson(b, a).value(), new Pearson(a, b).value(), 1e-8);
        Assert.assertEquals(2 / Math.sqrt(7), new Pearson(a, b).value(), 1e-8);
    }
}
