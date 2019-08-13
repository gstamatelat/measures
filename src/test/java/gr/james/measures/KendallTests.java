package gr.james.measures;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class KendallTests {
    /**
     * Must be 1 with same inputs.
     */
    @Test
    public void identity() {
        final List<Integer> a = Arrays.asList(1, 2, 3, 4);
        final List<Integer> b = Arrays.asList(1, 2, 3, 4);
        Assert.assertEquals(1.0, new Kendall(a, b).value(), 1e-8);
    }

    /**
     * Must be -1 if reverse inputs.
     */
    @Test
    public void zero() {
        final List<Integer> a = Arrays.asList(1, 2, 3, 4);
        final List<Integer> b = Arrays.asList(4, 3, 2, 1);
        Assert.assertEquals(-1.0, new Kendall(a, b).value(), 1e-8);
    }

    /**
     * Some intermediate intersection.
     */
    @Test
    public void normal() {
        final List<Integer> a = Arrays.asList(1, 2, 3, 4);
        final List<Integer> b = Arrays.asList(1, 2, 4, 3);
        Assert.assertEquals(2.0 / 3.0, new Kendall(a, b).value(), 1e-8);
    }

    /**
     * Commutativity.
     */
    @Test
    public void commutativity() {
        final List<Integer> a = Arrays.asList(1, 2, 3, 4);
        final List<Integer> b = Arrays.asList(1, 2, 4, 3);
        Assert.assertEquals(new Kendall(b, a).value(), new Kendall(a, b).value(), 1e-8);
    }

    /**
     * Test with ties.
     */
    @Test
    public void tauB() {
        final List<Integer> a = Arrays.asList(1, 2, 2, 3, 2);
        final List<Integer> b = Arrays.asList(4, 1, 3, 2, 1);
        Assert.assertEquals(-3.0 / Math.sqrt(7 * 9), new Kendall(a, b).value(), 1e-8);
    }
}
