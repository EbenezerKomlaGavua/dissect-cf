package com.ljmu.andre.SimulationHelpers.Packets;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class FirstTest {

	int[] preload = { 1, 2, 3, 4, 10, 20, -1, 5 };

	// Tests if both constructors are there
	// This is just here so we get compile time errors before the fixes are done
	@Test
	public void testForConstruction() {
		BaseCalculator calcBase = new BaseCalculator();
		BaseCalculator calcExtended = new BaseCalculator(1,2);
		Assert.assertNotNull("Default constructor does not work - a miracle..", calcBase);
		Assert.assertNotNull("Extended constructor does not work, again a miracle", calcExtended);
	}

	// Tests if the construction is successful with two initial numbers
	@Test
	public void testGetters() {
		int[] result = new int[preload.length];
		for (int i = 0; i < preload.length; i += 2) {
			BaseCalculator calc = new BaseCalculator(preload[i], preload[i + 1]);
			result[i] = calc.getFirst();
			result[i + 1] = calc.getSecond();
		}
		Assert.assertArrayEquals("Preloaded values are not available as expected when using extended constructor",
				preload, result);
	}

	// Tests if the setters are working 
	@Test
	public void testSetters() {
		int[] result = new int[preload.length];
		for (int i = 0; i < preload.length; i += 2) {
			BaseCalculator calc = new BaseCalculator();
			calc.setFirst(preload[i]);
			calc.setSecond(preload[i + 1]);
			result[i] = calc.getFirst();
			result[i + 1] = calc.getSecond();
		}
		Assert.assertArrayEquals("Preloaded values are not available as expected when using setters", preload, result);
	}

	// Tests if the default operation works as expected (ie. adds)
	@Test
	public void getsDefaultAdditionResult() {
		int[] expected = new int[preload.length / 2];
		int[] result = new int[preload.length / 2];
		BaseCalculator calc = new BaseCalculator();
		for (int i = 0; i < preload.length; i += 2) {
			calc.setFirst(preload[i]);
			calc.setSecond(preload[i + 1]);
			result[i / 2] = calc.solution();
			expected[i / 2] = preload[i] + preload[i + 1];
		}
		Assert.assertArrayEquals("Calculated values are not correct for default operation", expected, result);
	}

	// Tests if the default operation is addition
	@Test
	public void testDefaultOperation() {
		BaseCalculator calc = new BaseCalculator();
		Assert.assertEquals("Default operation is not addition", BaseCalculator.OPERATION.ADDITION,
				calc.getOperation());
	}

	// Tests if the default operation is addition
	@Test
	public void testOperationChange() {
		BaseCalculator calc = new BaseCalculator();
		calc.setOperation(BaseCalculator.OPERATION.MULTIPLY);
		Assert.assertEquals("Changed operation is not multiplication", BaseCalculator.OPERATION.MULTIPLY,
				calc.getOperation());
	}

	// Tests if the altering the operation changes the results as expected (ie.
	// multiplies)
	@Test
	public void testAlteredOperation() {
		BaseCalculator calc = new BaseCalculator();
		calc.setOperation(BaseCalculator.OPERATION.MULTIPLY);
		int[] expected = new int[preload.length / 2];
		int[] result = new int[preload.length / 2];
		for (int i = 0; i < preload.length; i += 2) {
			calc.setFirst(preload[i]);
			calc.setSecond(preload[i + 1]);
			result[i / 2] = calc.solution();
			expected[i / 2] = preload[i] * preload[i + 1];
		}
		Assert.assertArrayEquals("Calculated values are not correct for multiply operation", expected, result);
	}

}
