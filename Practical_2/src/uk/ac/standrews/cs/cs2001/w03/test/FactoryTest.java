package uk.ac.standrews.cs.cs2001.w03.test;

import uk.ac.standrews.cs.cs2001.w03.common.AbstractFactoryClient;
import uk.ac.standrews.cs.cs2001.w03.impl.Factory;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IShop;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class that tests the Factory class.
 */
public class FactoryTest extends AbstractFactoryClient {

    /**
     * Tests the getInstance() method returns the correct instance of the factory object.
     * @throws Exception Exception Exception
     */
    @Test
    public void getInstance() throws Exception {
        assertEquals(AbstractFactoryClient.getFactory(), Factory.getInstance());
    }

    /**
     * Tests that product objects are correctly been created.
     * @throws Exception Exception
     */
    @Test
    public void makeProduct() throws Exception {
        IProduct iProduct1 = getFactory().makeProduct("123", "bread");
        IProduct iProduct2 = getFactory().makeProduct("123", "bread");
        IProduct iProduct3 = iProduct2;
        assertNotEquals(iProduct1, iProduct2); //Two objects with the same parameters, not equal
        assertEquals(iProduct2, iProduct3); //One object set equal to another, equal
        assertEquals(iProduct1.getBarCode(), "123");
        assertEquals(iProduct1.getDescription(), "bread");
    }

    /**
     * Test stockRecords objects are being correctly created.
     * @throws Exception Exception
     */
    @Test
    public void makeStockRecord() throws Exception {
        IProduct iProduct1 = getFactory().makeProduct("123", "bread");
        IStockRecord iStockRecord1 = getFactory().makeStockRecord(iProduct1);
        IStockRecord iStockRecord2 = getFactory().makeStockRecord(iProduct1);
        IStockRecord iStockRecord3 = iStockRecord1;
        assertNotEquals(iStockRecord1, iStockRecord2); //Two objects with the same parameters, not equal
        assertEquals(iStockRecord1, iStockRecord3); //One object set equal to another, equal
        assertEquals(iStockRecord1.getProduct(), iStockRecord2.getProduct());
        assertEquals(iStockRecord1.getProduct(), iStockRecord3.getProduct());
    }

    /**
     * Tests shop objects are correctly being created.
     * @throws Exception Exception
     */
    @Test
    public void makeShop() throws Exception {
        IShop iShop1 = getFactory().makeShop();
        IShop iShop2 = iShop1;
        assertEquals(iShop1, iShop2); //One object set equal to another, equal
        IProduct iProduct1 = getFactory().makeProduct("123", "bread");
        iShop1.registerProduct(iProduct1);
        assertEquals(iShop1, iShop2); //Attribute of one object changed, second object still equal
    }

}
