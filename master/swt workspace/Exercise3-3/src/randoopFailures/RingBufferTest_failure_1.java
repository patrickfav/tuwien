package randoopFailures;

import junit.framework.*;

public class RingBufferTest_failure_1 extends TestCase {

  public static boolean debug = false;

  public void test1() throws Throwable {

    if (debug) System.out.printf("%nRingBufferTest_failure_1.test1");


    randoop.RingBuffer var1 = new randoop.RingBuffer(10);
    var1.enqueue((java.lang.Object)'#');

  }

}
