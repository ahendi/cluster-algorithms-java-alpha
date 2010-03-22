/**
 * 
 */
package tests.validationIndices;

import input.Dataset;
import input.InputReader;

import org.junit.Before;
import org.junit.Test;

import validationIndices.BPIndex;
import validationIndices.CIndex;
import validationIndices.DBIndex;
import validationIndices.DunnIndex;
import validationIndices.FowlkesMallowsIndex;
import validationIndices.GoodmanKruskal;
import validationIndices.JaccardIndex;
import validationIndices.RandIndex;
import validationIndices.SilhouetteIndex;

import algorithms.Leader;
import static org.junit.Assert.*;

/**
 * @author Markus
 *
 */
public class CorrectnessTest {

	private Dataset resultSet;
	@Before
	public void setUp(){
	
			this.resultSet = InputReader.readFromfile("C:\\Users\\Markus\\Documents\\Masterarbeit\\Workspace\\Clusterer\\src\\easyFullyCorrect.result",true);
			
	}
	
	@Test
	public void testIndices(){
		CIndex cIndex = new CIndex();
		DBIndex  dbIndex = new DBIndex();
		DunnIndex dunnIndex = new DunnIndex();
		GoodmanKruskal gkIndex = new GoodmanKruskal();
		JaccardIndex jIndex = new JaccardIndex();
		FowlkesMallowsIndex fwIndex = new FowlkesMallowsIndex();
		RandIndex rIndex = new RandIndex();
		BPIndex bpIndex = new BPIndex();
		SilhouetteIndex si = new SilhouetteIndex();
		
		assertEquals(1,rIndex.calculateIndex(resultSet));
		assertEquals(1,fwIndex.calculateIndex(resultSet));
		assertEquals(1,jIndex.calculateIndex(resultSet));
		assertEquals(1,bpIndex.calculateIndex(resultSet));
		
		assertEquals(0,cIndex.calculateIndex(resultSet));
		assertEquals(1,gkIndex.calculateIndex(resultSet));
		assertEquals(0.17,dbIndex.calculateIndex(resultSet));
		assertEquals(1.11,dunnIndex.calculateIndex(resultSet));
		assertEquals(0.761,si.calculateIndex(resultSet));

		
		
	}
	
}
