import junit.framework.Test;
import junit.framework.TestSuite;


public class RingBufferTest extends TestSuite{

	public static Test suite() {
		TestSuite suite = new TestSuite(RingBufferTestCoverage.class);
		suite.addTestSuite(RingBufferIteratorStatesTest.class);
		suite.addTestSuite(RingBufferStatesTest.class);
		suite.addTestSuite(RingBufferTestMutation.class);
		//$JUnit-END$
		return suite;
	}

}
