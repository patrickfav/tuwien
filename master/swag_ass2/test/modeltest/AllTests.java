package modeltest;


import modeltest.testcases.BaseSquareTest;
import modeltest.testcases.BaseTest;
import modeltest.testcases.BuildingCostsTest;
import modeltest.testcases.BuildingTest;
import modeltest.testcases.BuildingTypeTest;
import modeltest.testcases.MapSquareTest;
import modeltest.testcases.MapTest;
import modeltest.testcases.PlayerTest;
import modeltest.testcases.ResourceTest;
import modeltest.testcases.TroopTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ResourceTest.class,MapTest.class,MapSquareTest.class,PlayerTest.class,
		BaseSquareTest.class,BuildingTest.class,BuildingTypeTest.class,TroopTest.class,BaseTest.class,BuildingCostsTest.class,})
public class AllTests {

}
