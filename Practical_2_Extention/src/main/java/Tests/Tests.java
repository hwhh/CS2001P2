package Tests;

import Entity.common.AbstractFactoryClient;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * A classes used to execute all other test classes
 */
@RunWith(Suite.class)
@SuiteClasses({ ShopTest.class})
public class Tests extends AbstractFactoryClient {

}