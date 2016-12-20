package uk.ac.standrews.cs.cs2001.w03.test;

import uk.ac.standrews.cs.cs2001.w03.common.AbstractFactoryClient;
import uk.ac.standrews.cs.cs2001.w03.impl.Shop;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IShop;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class that tests the Stock record class.
 */
public class StockRecordTest extends AbstractFactoryClient {

    private IShop iShop;
    private IProduct iProduct1,  iProduct2,  iProduct3;

    /**
     * Executed before every test and creates the objects to be used in the tests.
     * @throws Exception exception
     */
    @Before
    public void setUp() throws Exception {
        iShop = getFactory().makeShop();
        iProduct1 = getFactory().makeProduct("123", "bread");
        iProduct2 = getFactory().makeProduct("234", "toast");
        iProduct3 = getFactory().makeProduct("345", "cooked bread");
    }

    /**
     * Test if the correct product is returned with the correct attributes.
     * @throws Exception exception
     */
    @Test
    public void getProduct() throws Exception {
        IProduct product = getFactory().makeProduct("123", "raw toast");
        IStockRecord stockRecord = getFactory().makeStockRecord(product);
        assertEquals(stockRecord.getProduct(),  product);
    }

    /**
     * Checks that when a null product is made it is actually null.
     * @throws Exception exception
     */
    @Test
    public void productIsNull() throws Exception {
        IProduct nullIProduct = null;
        IStockRecord stockRecord = getFactory().makeStockRecord(nullIProduct);
        assertEquals(stockRecord.getProduct(),  null);
    }

    /**
     * This test registers products,  updates their stock  by various amounts
     * and checks the attributes have updated appropriately and the method works.
     * @throws Exception exception
     */
    @Test
    public void getStockCount() throws Exception {
        //Register 3 products and add 200 to product 3s stock count,  check only product 3 has been correctly updated
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        iShop.registerProduct(iProduct3);
        for (int i = 0; i < 200; i++) {
            iShop.addStock(iProduct3.getBarCode());
        }
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(),  0);
        assertEquals(((Shop) iShop).stockRecords.get(1).getStockCount(),  0);
        assertEquals(((Shop) iShop).stockRecords.get(2).getStockCount(),  200);
        //Add 100 to product 1s stock count,  check only product 1 has been correctly updated
        for (int i = 0; i < 100; i++) {
            iShop.addStock(iProduct1.getBarCode());
        }
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(),  100);
        assertEquals(((Shop) iShop).stockRecords.get(1).getStockCount(),  0);
        assertEquals(((Shop) iShop).stockRecords.get(2).getStockCount(),  200);
        //Add 50 to product 2s stock count,  and reduce 50 from product 3s stock count check only product 2 and 3 have been correctly updated
        for (int i = 0; i < 50; i++) {
            iShop.buyProduct(iProduct3.getBarCode());
            iShop.addStock(iProduct2.getBarCode());
        }
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(),  100);
        assertEquals(((Shop) iShop).stockRecords.get(1).getStockCount(),  50);
        assertEquals(((Shop) iShop).stockRecords.get(2).getStockCount(),  150);
    }

    /**
     * This test registers products,  updates their stock  by various amounts
     * and checks the attributes have updated appropriately and the method works.
     * @throws Exception exception
     */
    @Test
    public void getNumberOfSales() throws Exception {
        //Register 3 products and add 200 to product 3s stock count and then reduce it by 50,  check only product 3 has been correctly updated
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        iShop.registerProduct(iProduct3);
        for (int i = 0; i < 200; i++) {
            iShop.addStock(iProduct3.getBarCode());
        }
        for (int i = 0; i < 50; i++) {
            iShop.buyProduct(iProduct3.getBarCode());
        }
        assertEquals(((Shop) iShop).stockRecords.get(0).getNumberOfSales(),  0);
        assertEquals(((Shop) iShop).stockRecords.get(1).getNumberOfSales(),  0);
        assertEquals(((Shop) iShop).stockRecords.get(2).getNumberOfSales(),  50);
        //Add 100 to product 1s stock count and then reduce it by 75,  check only product 1 has been correctly updated
        for (int i = 0; i < 100; i++) {
            iShop.addStock(iProduct1.getBarCode());
        }
        for (int i = 0; i < 75; i++) {
            iShop.buyProduct(iProduct1.getBarCode());
        }
        assertEquals(((Shop) iShop).stockRecords.get(0).getNumberOfSales(),  75);
        assertEquals(((Shop) iShop).stockRecords.get(1).getNumberOfSales(),  0);
        assertEquals(((Shop) iShop).stockRecords.get(2).getNumberOfSales(),  50);
        //Add 50 to product 2s stock count and then reduce it by 49,  check only product 2 has been correctly updated
        for (int i = 0; i < 50; i++) {
            iShop.addStock(iProduct2.getBarCode());
        }
        for (int i = 0; i < 49; i++) {
            iShop.buyProduct(iProduct2.getBarCode());
        }
        assertEquals(((Shop) iShop).stockRecords.get(0).getNumberOfSales(),  75);
        assertEquals(((Shop) iShop).stockRecords.get(1).getNumberOfSales(),  49);
        assertEquals(((Shop) iShop).stockRecords.get(2).getNumberOfSales(),  50);
    }

    /**
     * This test registers products,  updates their stock  by various amounts
     * and checks the attributes have updated appropriately and the method works.
     * @throws Exception exception
     */
    @Test
    public void addStock() throws Exception {
        //Register 3 products and check their initial stock count equals 0
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        iShop.registerProduct(iProduct3);
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(),  0);
        assertEquals(((Shop) iShop).stockRecords.get(1).getStockCount(),  0);
        assertEquals(((Shop) iShop).stockRecords.get(2).getStockCount(),  0);
        //Add stock to each of the products and check each product has been updated correctly
        for (int i = 0; i < 200; i++) {
            iShop.addStock(iProduct1.getBarCode());
        }
        for (int i = 0; i < 150; i++) {
            iShop.addStock(iProduct2.getBarCode());
        }
        for (int i = 0; i < 100; i++) {
            iShop.addStock(iProduct3.getBarCode());
        }
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(),  200);
        assertEquals(((Shop) iShop).stockRecords.get(1).getStockCount(),  150);
        assertEquals(((Shop) iShop).stockRecords.get(2).getStockCount(),  100);
    }

    /**
     * This test registers products,  updates their stock  by various amounts
     * and checks the attributes have updated appropriately and the method works.
     * @throws Exception exception
     */
    @Test
    public void buyProduct() throws Exception {
        //Register 3 products and check their initial number of sales equals 0
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        iShop.registerProduct(iProduct3);
        assertEquals(((Shop) iShop).stockRecords.get(0).getNumberOfSales(),  0);
        assertEquals(((Shop) iShop).stockRecords.get(1).getNumberOfSales(),  0);
        assertEquals(((Shop) iShop).stockRecords.get(2).getNumberOfSales(),  0);
        //Add stock to product 1 then remove it and check only product 1 has been correctly updated
        for (int i = 0; i < 200; i++) {
            iShop.addStock(iProduct1.getBarCode());
        }
        for (int i = 0; i < 150; i++) {
            iShop.buyProduct(iProduct1.getBarCode());
        }
        assertEquals(((Shop) iShop).stockRecords.get(0).getNumberOfSales(),  150);
        assertEquals(((Shop) iShop).stockRecords.get(1).getNumberOfSales(),  0);
        assertEquals(((Shop) iShop).stockRecords.get(2).getNumberOfSales(),  0);
    }

}
