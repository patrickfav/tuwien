/**
 Copyright (C) 2007 Mark Utting
 This file is part of the CZT project.

 The CZT project contains free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License as published
 by the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 The CZT project is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with CZT; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package nz.ac.waikato.modeljunit.examples;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;
import nz.ac.waikato.modeljunit.coverage.CoverageMetric;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;

/**
 * A model of a set with two elements: s1 and s2.
 * 
 * This model includes the adaptor/harness code that interacts with a system
 * under test (SUT), which can be any implementation of Set<String>. The SUT
 * must be passed to the constructor of this class.
 * 
 * After each transition, we call checkSUT(), which uses JUnit Assert methods to
 * check that the SUT is in the expected state. (To see a test fail, either pass
 * an instance of StringSetBuggy to the constructor, or change the "false" in
 * removeS2 to "true").
 * 
 * Note: We could have added this adaptor code by inheriting from the SimpleSet
 * model, but in this example the model code and the adaptor code are written in
 * the same class, so that you can see them side-by-side.
 */
public class RingBufferWithAdaptor implements FsmModel {

	private static final int RB_CAPACITY = 3;
	protected RingBuffer<String> rb;
	protected int counter, max;

	// our test data for the SUT
	protected String str1 = "some string";
	protected String str2 = ""; // empty string

	/** Tests a StringSet implementation. */
	public RingBufferWithAdaptor() {
		rb = new RingBuffer<String>(RB_CAPACITY);
		//counter = 0;
		max = RB_CAPACITY;
	}
	
	@Override
	public Object getState() {
		if (rb.isEmpty())
			return "Empty";

		if (rb.size() == max)
			return "Full";

		if (rb.size() > 0)
			return "Filled";

		return "invalid";
	}
	
	@Override
	public void reset(boolean testing) {
		rb = new RingBuffer<String>(RB_CAPACITY);
	}

	@Action
	public void enqueue() {
		if(rb.size() == max) {
			try {
			 rb.enqueue("element "+rb.size());
			 Assert.fail();
			} catch(RuntimeException e) {
				
			}
		} else  {
			rb.enqueue("element "+rb.size());
		}
	}
	
	
	@Action
	public void dequeue() {
		if(rb.isEmpty()) {
			try {
			 rb.dequeue();
			 Assert.fail();
			} catch(RuntimeException e) {
				
			}
		} else  {
			Assert.assertNotNull(rb.dequeue());
		}
	}

	/** An example of generating tests from this model. */
	public static void main(String[] args) {
		Tester tester = new GreedyTester(new RingBufferWithAdaptor());
		// tester.buildGraph(); // to get better statistics
		tester.addListener(new VerboseListener());

		// uncoment this line if you want to stop when the first error is found.
		// tester.addListener(new StopOnFailureListener());

		tester.addCoverageMetric(new TransitionCoverage());
		tester.generate(50);
		tester.printCoverage();
	}
}
