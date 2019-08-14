package gr.james.measures;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CosineTests {
    /**
     * Must be 1 with same inputs.
     */
    @Test
    public void identity() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        final Set<Integer> b = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Assert.assertEquals(1.0, new Cosine(a, b).value(), 1e-8);
    }

    /**
     * Must be 0 if intersection is 0.
     */
    @Test
    public void zero() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> b = new HashSet<>(Arrays.asList(3, 4));
        Assert.assertEquals(0.0, new Cosine(a, b).value(), 1e-8);
    }

    /**
     * Some intermediate intersection for sets.
     */
    @Test
    public void normalSet() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3));
        final Set<Integer> b = new HashSet<>(Arrays.asList(3, 4, 5));
        Assert.assertEquals(1.0 / 3.0, new Cosine(a, b).value(), 1e-8);
    }

    /**
     * Commutativity for sets.
     */
    @Test
    public void commutativitySet() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> b = new HashSet<>(Arrays.asList(2, 3, 4));
        Assert.assertEquals(new Cosine(b, a).value(), new Cosine(a, b).value(), 1e-8);
        Assert.assertEquals(1.0 / Math.sqrt(6), new Cosine(a, b).value(), 1e-8);
    }

    /**
     * Some intermediate intersection for vectors.
     */
    @Test
    public void normalList() {
        final List<Double> a = Arrays.asList(1.0, 2.0, 3.0);
        final List<Double> b = Arrays.asList(3.0, 4.0, 5.0);
        Assert.assertEquals((3.0 + 8.0 + 15.0) / Math.sqrt((1.0 + 4.0 + 9.0) * (9.0 + 16.0 + 25.0)),
                new Cosine(a, b).value(), 1e-8);
    }

    /**
     * Commutativity for vectors.
     */
    @Test
    public void commutativityList() {
        final List<Double> a = Arrays.asList(1.0, 2.0, 4.0);
        final List<Double> b = Arrays.asList(2.0, 3.0, 6.0);
        Assert.assertEquals(new Cosine(b, a).value(), new Cosine(a, b).value(), 1e-8);
        Assert.assertEquals((2.0 + 6.0 + 24.0) / Math.sqrt((1.0 + 4.0 + 16.0) * (4.0 + 9.0 + 36.0)),
                new Cosine(a, b).value(), 1e-8);
    }
}
