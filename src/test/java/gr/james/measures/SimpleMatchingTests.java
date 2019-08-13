package gr.james.measures;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SimpleMatchingTests {
    /**
     * Must be 1 with same inputs that cover the world.
     */
    @Test
    public void identity() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        final Set<Integer> b = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        final Set<Integer> world = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Assert.assertEquals(1.0, new SimpleMatching(a, b, world).value(), 1e-8);
    }

    /**
     * Must be 0 if intersection is 0 and union is the world.
     */
    @Test
    public void zero() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> b = new HashSet<>(Arrays.asList(3, 4));
        final Set<Integer> world = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Assert.assertEquals(0.0, new SimpleMatching(a, b, world).value(), 1e-8);
    }

    /**
     * Some intermediate intersection.
     */
    @Test
    public void normal() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3));
        final Set<Integer> b = new HashSet<>(Arrays.asList(3, 4, 5));
        final Set<Integer> world = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Assert.assertEquals(2.0 / 6.0, new SimpleMatching(a, b, world).value(), 1e-8);
    }

    /**
     * Commutativity.
     */
    @Test
    public void commutativity() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> b = new HashSet<>(Arrays.asList(2, 3, 4));
        final Set<Integer> world = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Assert.assertEquals(new SimpleMatching(b, a, world).value(), new SimpleMatching(a, b, world).value(), 1e-8);
        Assert.assertEquals(2.0 / 5.0, new SimpleMatching(a, b, world).value(), 1e-8);
    }
}
