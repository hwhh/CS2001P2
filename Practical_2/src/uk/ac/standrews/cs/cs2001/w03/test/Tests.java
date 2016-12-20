package uk.ac.standrews.cs.cs2001.w03.test;

import uk.ac.standrews.cs.cs2001.w03.common.AbstractFactoryClient;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * A classes used to execute all other test classes.
 */
@RunWith(Suite.class)
@SuiteClasses({FactoryTest.class, ProductTest.class, ShopTest.class, StockRecordTest.class})
public class Tests extends AbstractFactoryClient {

}
