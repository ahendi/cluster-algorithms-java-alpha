/**
 * 
 */
package tests;

import org.junit.Test;

import util.CalculationUtil;

import static org.junit.Assert.*;
/**
 * @author Markus
 *
 */
public class UtilTest {
	
	@Test
	public void testGetIndexOfMinElement(){
		Float[] testArray = { 3.23f,0.0f,1.2f};
		assertEquals (1, CalculationUtil.getIndexOfMinElement(testArray));
		Float[] testArray2 = { 3.23f,0.01f,1.2f,0.0f};
		assertEquals (3, CalculationUtil.getIndexOfMinElement(testArray2));
		Float[] testArray3 = {0.0f, 3.23f,0.01f,1.2f,3.0f};
		assertEquals (0, CalculationUtil.getIndexOfMinElement(testArray3));
		
	}

}
