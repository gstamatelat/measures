package gr.james.measures;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SpearmanTests {
    /**
     * Must be 1 with same inputs.
     */
    @Test
    public void identity() {
        final List<Integer> a = Arrays.asList(8, 6, 9, 7);
        final List<Integer> b = Arrays.asList(8, 6, 9, 7);
        Assert.assertEquals(1.0, new Spearman(a, b).value(), 1e-8);
    }

    /**
     * Must be -1 if reverse inputs.
     */
    @Test
    public void zero() {
        final List<Integer> a = Arrays.asList(8, 6, 9, 7);
        final List<Integer> b = Arrays.asList(7, 9, 6, 8);
        Assert.assertEquals(-1.0, new Spearman(a, b).value(), 1e-8);
    }

    /**
     * Some intermediate value.
     */
    @Test
    public void normal() {
        final List<Integer> a = Arrays.asList(1, 2, 3, 4);
        final List<Integer> b = Arrays.asList(1, 2, 4, 3);
        Assert.assertEquals(0.8, new Spearman(a, b).value(), 1e-8);
    }

    /**
     * Commutativity.
     */
    @Test
    public void commutativity() {
        final List<Integer> a = Arrays.asList(1, 2, 3, 4);
        final List<Integer> b = Arrays.asList(1, 2, 4, 3);
        Assert.assertEquals(new Spearman(b, a).value(), new Spearman(a, b).value(), 1e-8);
    }
}
