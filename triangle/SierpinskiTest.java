package triangle;

import static org.junit.Assert.*;

import org.junit.Test;

public class SierpinskiTest {

	/**
	 * test to make sure the findMidpoints method
	 * inside of my SierpinskiPanel returns the right
	 * array. Wrote a private method that tests if the 
	 * inside of the two arrays are equal since assertEquals
	 * does not work for int[]
	 */
	@Test
	public void findMidpointsTest() {
		SierpinskiPanel s = new SierpinskiPanel(new Sierpinski());
		assertIntArrayEquals(new int[]{3, 7, 5}, s.findMidpoints(new int[]{1, 5, 10}));
		assertIntArrayEquals(new int[]{150,450,300}, s.findMidpoints(new int[]{0, 300, 600}));
	}

	/**
	 * takes in two int arrays and tests if the insides are
	 * equal
	 * @param is
	 * @param findMidpoints
	 */
	private void assertIntArrayEquals(int[] is, int[] findMidpoints) {
		if (is.length != findMidpoints.length) {
			fail("Not the same length");
		}
		for (int i = 0; i < findMidpoints.length; i++) {
			if (is[i] != findMidpoints[i]) {
				fail("Expected: " + findMidpoints[i] + " at index " + i + " but got: " + is[i]);
			}
		}
	}

}
