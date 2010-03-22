package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tests.validationIndices.CIndexTest;
import tests.validationIndices.GoodmanKruskalTest;
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
  EuclideanDistanceTest.class,
  InputReaderTest.class,
  leaderAlgorithmTest.class,
  CIndexTest.class,
  GoodmanKruskalTest.class
  
  
})
public class AllTests {
    // the class remains completely empty, 
    // being used only as a holder for the above annotations
}
