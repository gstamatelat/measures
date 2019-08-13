package gr.james.measures;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OverlapTests {
    /**
     * Must be 1 with same inputs.
     */
    @Test
    public void identity() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        final Set<Integer> b = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Assert.assertEquals(1.0, new Overlap(a, b).value(), 1e-8);
    }

    /**
     * Must be 0 if intersection is 0.
     */
    @Test
    public void zero() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> b = new HashSet<>(Arrays.asList(3, 4));
        Assert.assertEquals(0.0, new Overlap(a, b).value(), 1e-8);
    }

    /**
     * Some intermediate intersection.
     */
    @Test
    public void normal() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3));
        final Set<Integer> b = new HashSet<>(Arrays.asList(3, 4, 5));
        Assert.assertEquals(1.0 / 3.0, new Overlap(a, b).value(), 1e-8);
    }

    /**
     * Commutativity.
     */
    @Test
    public void commutativity() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> b = new HashSet<>(Arrays.asList(2, 3, 4));
        Assert.assertEquals(new Overlap(b, a).value(), new Overlap(a, b).value(), 1e-8);
        Assert.assertEquals(1.0 / 2.0, new Overlap(a, b).value(), 1e-8);
    }
}
