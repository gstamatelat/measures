package gr.james.measures;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MutualInformationTests {
    /**
     * Must be 1 with same inputs.
     */
    @Test
    public void identity() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        final Set<Integer> b = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        final Set<Integer> world = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
        Assert.assertEquals(1.0, new MutualInformation<>(a, b, world).normalizedMutualInformation(), 1e-8);
    }

    /**
     * Commutativity.
     */
    @Test
    public void commutativity() {
        final Set<Integer> a = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> b = new HashSet<>(Arrays.asList(2, 3, 4));
        final Set<Integer> world = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Assert.assertEquals(new MutualInformation<>(b, a, world).normalizedMutualInformation(), new MutualInformation<>(a, b, world).normalizedMutualInformation(), 1e-8);
    }
}
