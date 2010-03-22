package tests;


import input.Dataset;
import input.InputReader;
import static org.junit.Assert.*;
import org.junit.Test;

public class InputReaderTest {

	@Test
	public void testReadFromfile() {
		InputReader inputReader = new InputReader();
		Dataset testset =inputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\90.valid");
		assertEquals(90,testset.get(0).getDimension());
		assertEquals(5.34285,testset.get(0).getFeatures()[0],0.00001);
		assertEquals(750,testset.size());
		
	}

}
