package uk.ac.standrews.cs.cs2001.w03.test;

import uk.ac.standrews.cs.cs2001.w03.common.AbstractFactoryClient;
import uk.ac.standrews.cs.cs2001.w03.common.BarCodeAlreadyInUseException;
import uk.ac.standrews.cs.cs2001.w03.common.ProductNotRegisteredException;
import uk.ac.standrews.cs.cs2001.w03.common.StockUnavailableException;
import uk.ac.standrews.cs.cs2001.w03.impl.Product;
import uk.ac.standrews.cs.cs2001.w03.impl.Shop;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IShop;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class that tests the Shop class.
 */
public class ShopTest extends AbstractFactoryClient {

    private static final int TWENTY_FIVE = 25;
    private static final int FIFTY = 50;

    private static final int SEVENTY_FIVE = 75;
    private static final int ONE_HUNDRED = 100;
    private static final int ONE_HUNDRED_AND_TWENTY_FIVE = 125;
    private static final int ONE_HUNDRED_AND_FIFTY = 150;
    private static final int ONE_THOUSAND = 1000;

    /**
     * Shop global variable.
     */
    private IShop iShop;
    /**
     * Product global variables.
     */
    private IProduct iProduct1, iProduct2, iProduct3;

    /**
     * Executed before every test and creates the objects to be used in the tests.
     * @throws Exception exception
     */
    @Before
    public void setUp() throws Exception {
        iShop  =  getFactory().makeShop();
        iProduct1  =  getFactory().makeProduct("123", "bread");
        iProduct2  =  getFactory().makeProduct("234", "toast");
        iProduct3  =  getFactory().makeProduct("345", "cooked bread");
    }

    /**
     * Ths test checks that the lists used in the shop class are operating as expected.
      * @throws Exception exception
     */
    @Test
    public void listManipulation() throws Exception {
        //Add product and check its in the list
        iShop.registerProduct(iProduct1);
        Assert.assertEquals(iProduct1.getBarCode(), ((Shop) iShop).stockRecords.get(0).getProduct().getBarCode());
        //Add second product and remove the first product then check the list only contains the second product
        iShop.registerProduct(iProduct2);
        ((Shop) iShop).products.remove(iProduct1);
        assertFalse(((Shop) iShop).products.contains(iProduct1));
        assertTrue(((Shop) iShop).products.contains(iProduct2));
        assertEquals(1, ((Shop) iShop).products.size());
        assertEquals(((Shop) iShop).stockRecords.get(0).getProduct(), iProduct1);
        assertEquals(((Shop) iShop).stockRecords.get(1).getProduct(), iProduct2);
        //Check to see if the stock records list has the right objects in it
        IStockRecord testIStockRecord1  =  ((Shop) iShop).stockRecords.get(0);
        IStockRecord testIStockRecord2  =  ((Shop) iShop).stockRecords.get(1);
        //Remove the stock record in position 0 of array list and check it has been successfully removed
        ((Shop) iShop).stockRecords.remove(0);
        assertFalse(((Shop) iShop).stockRecords.contains(testIStockRecord1));
        assertTrue(((Shop) iShop).stockRecords.contains(testIStockRecord2));
        assertEquals(1, ((Shop) iShop).stockRecords.size());
    }

    /**
     *This test attempts to access objects index which is greater than the list size
     * and is expected to throw "IndexOutOfBoundsException" exception.
     * @throws Exception exception
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void listManipulationIndexOutOfBounds() throws Exception {
        iShop.registerProduct(iProduct1);
        assertEquals(((Shop) iShop).products.size(), 1);
        assertEquals(((Shop) iShop).stockRecords.size(), 1);
        IProduct iProduct  =  ((Shop) iShop).products.get(1);
        IStockRecord stockRecord  =  ((Shop) iShop).stockRecords.get(1);
    }


    /**
     * This test tries to register a product which is equal to null
     * and is expected to throw a null pointer exception.
     * @throws Exception exception
     */
    @Test(expected = NullPointerException.class)
    public void productIsNull() throws Exception {
        IProduct nullIProduct  =  null;
        iShop.registerProduct(nullIProduct);
    }

    /* registerProduct method tests,
     *
     * TESTS INCLUDE:
     * Normal cases, with expected inputs.
     * Corner cases, such as empty collections, duplicate bar codes for different products, no stock.
     * Exceptional cases, such as dealing with nulls.
     *
     */

    /**
     * This test checks that a product/stock record is not already in the lists, it
     * then registers the product(s) and checks that the product now appears in the list.
     * @throws Exception exception
     */
    @Test
    public void registerProduct() throws Exception {
        assertEquals(((Shop) iShop).products.size(), 0);
        assertEquals(((Shop) iShop).stockRecords.size(), 0);
        iShop.registerProduct(iProduct1);
        assertEquals(((Shop) iShop).products.size(), 1);
        assertEquals(((Shop) iShop).stockRecords.size(), 1);
        iShop.registerProduct(iProduct2);
        assertEquals(((Shop) iShop).products.get(0), iProduct1);
        assertEquals(((Shop) iShop).products.get(1), iProduct2);
        assertNotEquals(((Shop) iShop).products.get(0), iProduct2);
        assertNotEquals(((Shop) iShop).products.get(1), iProduct1);
        assertEquals(((Shop) iShop).products.size(), 2);
        assertEquals(((Shop) iShop).stockRecords.size(), 2);
    }

    /**
     * This test attempts to register products with bad barcode's
     *  and is expected to throw "BarCodeAlreadyInUseException" exception.
     * @throws Exception exception
     */
    @Test(expected = BarCodeAlreadyInUseException.class)
    public void registerInvalidProduct() throws Exception {
        iShop.registerProduct(new Product("", ""));
        iShop.registerProduct(new Product(null, null));
    }
    /**
     * This test attempts to register the same product twice
     *  and is expected to throw "BarCodeAlreadyInUseException" exception.
     * @throws Exception exception
     */
    @Test(expected = BarCodeAlreadyInUseException.class)
    public void productAlreadyRegistered() throws Exception {
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        iShop.registerProduct(iProduct1);
    }

    /* unregisterProduct method tests,
     *
     * TESTS INCLUDE:
     * Normal cases, with expected inputs.
     * Corner cases, such as empty collections, duplicate bar codes for different products, no stock.
     * Exceptional cases, such as dealing with nulls.
     *
     * @throws Exception exception
     */

    /**
     * This checks that that item are correctly added and removed from the list(s).
     * @throws Exception exception
     */
    @Test
    public void unregisterProduct() throws Exception {
        //Register product and check it appears in the list
        iShop.registerProduct(iProduct1);
        assertEquals(((Shop) iShop).products.get(0), iProduct1);
        assertEquals(((Shop) iShop).products.size(), 1);
        assertEquals(((Shop) iShop).stockRecords.size(), 1);
        assertEquals(((Shop) iShop).stockRecords.get(0).getProduct(), iProduct1);
        //Remove the product and check it has been removed from the list
        iShop.unregisterProduct(iProduct1);
        assertEquals(((Shop) iShop).products.size(), 0);
        assertEquals(((Shop) iShop).stockRecords.size(), 0);
        //register 2 products and check they both appear in the list
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        assertEquals(((Shop) iShop).products.get(0), iProduct1);
        assertEquals(((Shop) iShop).products.size(), 2);
        assertEquals(((Shop) iShop).stockRecords.get(0).getProduct(), iProduct1);
        assertEquals(((Shop) iShop).stockRecords.size(), 2);
        //Unregister the first product and check only the second product remain in the list
        iShop.unregisterProduct(iProduct1);
        assertEquals(((Shop) iShop).products.get(0), iProduct2);
        assertEquals(((Shop) iShop).products.size(), 1);
        assertEquals(((Shop) iShop).stockRecords.size(), 1);
        assertEquals(((Shop) iShop).stockRecords.get(0).getProduct(), iProduct2);
    }

    /**
     * This test attempts to unregister a product that hasn't been registered
     *  and is expected to throw "ProductNotRegisteredException" exception.
     * @throws Exception exception
     */
    @Test (expected = ProductNotRegisteredException.class)
    public void unregisterUnregisteredProduct() throws Exception {
        iShop.registerProduct(iProduct1);
        iShop.unregisterProduct(iProduct1);
        iShop.unregisterProduct(iProduct1);
    }

    /**
     * This test registers a product then checks the number of sales for the product is 0,
     * next it adds ONE_HUNDRED to the stock and checks the stock record has updates appropriately.
     * @throws Exception exception
     */
    @Test
    public void addStock() throws Exception {
        iShop.registerProduct(iProduct1);
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(), 0);
        iShop.addStock(iProduct1.getBarCode());
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(), 1);
        for (int i  =  0; i < ONE_HUNDRED; i++) {
            iShop.addStock(iProduct1.getBarCode());
        }
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(), 101);
    }

    /**
     * This test attempts to update the stock for product that hasn't been registered
     *  and is expected to throw "ProductNotRegisteredException" exception.
     * @throws Exception exception
     */
    @Test(expected = ProductNotRegisteredException.class)
    public void addStockUnregistered() throws Exception {
        iShop.addStock("");
        iShop.addStock(null);
        iShop.addStock("THIS IS NOT A BARCODE...");
    }

    /**
     * This test registers 2 products then checks the number of sales for both products is 0,
     * then it uses the buyProduct methods and checks the number of sales for the products is being correctly updated.
     * @throws Exception exception
     */
    @Test
    public void buyProduct() throws Exception {
        //register 2 products and add 1 to the stock count of product 1
        //Check that the number of sales for both products is 0
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        iShop.addStock(iProduct1.getBarCode());
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(), 1);
        assertEquals(((Shop) iShop).stockRecords.get(0).getNumberOfSales(), 0);
        assertEquals(((Shop) iShop).stockRecords.get(1).getStockCount(), 0);
        assertEquals(((Shop) iShop).stockRecords.get(1).getNumberOfSales(), 0);
        //Use the byproduct method with product 1 and check only product 1 has had stock count and number of sales updated
        iShop.buyProduct(iProduct1.getBarCode());
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(), 0);
        assertEquals(((Shop) iShop).stockRecords.get(0).getNumberOfSales(), 1);
        assertEquals(((Shop) iShop).stockRecords.get(1).getStockCount(), 0);
        assertEquals(((Shop) iShop).stockRecords.get(1).getNumberOfSales(), 0);
        //Add 2 to products 2 stock count and then use byproduct method with product 2 and check only product 2 has had stock count and number of sales updated
        iShop.addStock(iProduct2.getBarCode());
        iShop.addStock(iProduct2.getBarCode());
        iShop.buyProduct(iProduct2.getBarCode());
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(), 0);
        assertEquals(((Shop) iShop).stockRecords.get(0).getNumberOfSales(), 1);
        assertEquals(((Shop) iShop).stockRecords.get(1).getStockCount(), 1);
        assertEquals(((Shop) iShop).stockRecords.get(1).getNumberOfSales(), 1);
    }

    /**
     * This test updates the stock of a product to 1 then attempts to purchase
     * 2 of the product and is expected to throw "StockUnavailableException" exception.
     * @throws Exception exception
     */
    @Test(expected = StockUnavailableException.class)
    public void buyProductInsufficientStock() throws Exception {
        iShop.registerProduct(iProduct1);
        iShop.addStock(iProduct1.getBarCode());
        assertEquals(((Shop) iShop).stockRecords.get(0).getStockCount(), 1);
        assertEquals(((Shop) iShop).stockRecords.get(0).getNumberOfSales(), 0);
        iShop.buyProduct(iProduct1.getBarCode());
        iShop.buyProduct(iProduct1.getBarCode());
    }

    /**
     * This test attempts to buy a product that hasn't been registered
     *  and is expected to throw "ProductNotRegisteredException" exception.
     * @throws Exception exception
     */
    @Test(expected = ProductNotRegisteredException.class)
    public void buyProductInvalidBarcode() throws Exception {
        iShop.buyProduct("");
        iShop.buyProduct(null);
        iShop.buyProduct("THIS IS NOT A BARCODE...");
    }

    /**
     * This test first checks no products are in the list, it then adds 1 product to
     * the list and checks it's there, it then registers another product and check both are there.
     * Finally it removes the first and then the second product.
     * @throws Exception exception
     */
    @Test
    public void getNumberOfProducts() throws Exception {
        assertEquals(((Shop) iShop).stockRecords.size(), 0);
        iShop.registerProduct(iProduct1);
        assertEquals(((Shop) iShop).stockRecords.size(), 1);
        iShop.registerProduct(iProduct2);
        assertEquals(((Shop) iShop).stockRecords.size(), 2);
        iShop.unregisterProduct(iProduct1);
        assertEquals(((Shop) iShop).stockRecords.size(), 1);
        iShop.unregisterProduct(iProduct2);
        assertEquals(((Shop) iShop).stockRecords.size(), 0);
    }

    /**
     * This test registers a products, updates their stock attributes by various amounts
     * and checks the attributes have updated appropriately and the method works.
     * @throws Exception exception
     */
    @Test
    public void getTotalStockCount() throws Exception {
        //Register 2 products
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        //Update product1 stock by ONE_HUNDRED and check total
        assertEquals(iShop.getTotalStockCount(), 0);
        for (int i  =  0; i < ONE_HUNDRED; i++) {
            iShop.addStock(iProduct1.getBarCode());
        }
        assertEquals(iShop.getTotalStockCount(), ONE_HUNDRED);
        //Update product1 stock by 50 and check total
        for (int i  =  0; i < FIFTY; i++) {
            iShop.addStock(iProduct2.getBarCode());
        }
        assertEquals(iShop.getTotalStockCount(), ONE_HUNDRED_AND_FIFTY);
        //Update product2 stock by -TWENTY_FIVE and check
        for (int i  =  0; i < TWENTY_FIVE; i++) {
            iShop.buyProduct(iProduct2.getBarCode());
        }
        assertEquals(iShop.getTotalStockCount(), ONE_HUNDRED_AND_TWENTY_FIVE);
        //Update product1 stock by -ONE_HUNDRED and check
        for (int i  =  0; i < ONE_HUNDRED; i++) {
            iShop.buyProduct(iProduct1.getBarCode());
        }
        assertEquals(iShop.getTotalStockCount(), TWENTY_FIVE);
        //Update product2 stock by -TWENTY_FIVE and check
        for (int i  =  0; i < TWENTY_FIVE; i++) {
            iShop.buyProduct(iProduct2.getBarCode());
        }
        assertEquals(iShop.getTotalStockCount(), 0);
    }

    /**
     * This test registers products, updates their stock  by various amounts
     * and checks the attributes have updated appropriately and the method works.
     * @throws Exception exception
     */
    @Test
    public void getStockCount() throws Exception {
        //Register 2 products and check their stock count equals 0
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        assertEquals(iShop.getStockCount(iProduct1.getBarCode()), 0);
        assertEquals(iShop.getStockCount(iProduct2.getBarCode()), 0);
        //Add ONE_HUNDRED to products 1 stock count and check only product 1 has been updated
        for (int i  =  0; i < ONE_HUNDRED; i++) {
            iShop.addStock(iProduct1.getBarCode());
        }
        assertEquals(iShop.getStockCount(iProduct1.getBarCode()), ONE_HUNDRED);
        assertEquals(iShop.getStockCount(iProduct2.getBarCode()), 0);
        //Add 50 to products 2 stock count and check only product 2 has been updated
        for (int i  =  0; i < FIFTY; i++) {
            iShop.addStock(iProduct2.getBarCode());
        }
        assertEquals(iShop.getStockCount(iProduct1.getBarCode()), ONE_HUNDRED);
        assertEquals(iShop.getStockCount(iProduct2.getBarCode()), FIFTY);
        //Reduce product 2s stock count by TWENTY_FIVE and check only product 2 has been updated
        for (int i  =  0; i < TWENTY_FIVE; i++) {
            iShop.buyProduct(iProduct2.getBarCode());
        }
        assertEquals(iShop.getStockCount(iProduct1.getBarCode()), ONE_HUNDRED);
        assertEquals(iShop.getStockCount(iProduct2.getBarCode()), TWENTY_FIVE);
        //Reduce product 1s stock count by ONE_HUNDRED and check only product 1 has been updated
        for (int i  =  0; i < ONE_HUNDRED; i++) {
            iShop.buyProduct(iProduct1.getBarCode());
        }
        assertEquals(iShop.getStockCount(iProduct1.getBarCode()), 0);
        assertEquals(iShop.getStockCount(iProduct2.getBarCode()), TWENTY_FIVE);
        //Reduce product 2s stock count by TWENTY_FIVE and check only product 2 has been updated
        for (int i  =  0; i < TWENTY_FIVE; i++) {
            iShop.buyProduct(iProduct2.getBarCode());
        }
        assertEquals(iShop.getStockCount(iProduct1.getBarCode()), 0);
        assertEquals(iShop.getStockCount(iProduct2.getBarCode()), 0);
    }

    /**
     * This test attempts to get the stock count for a product that hasn't been registered
     *  and is expected to throw "ProductNotRegisteredException" exception.
     * @throws Exception exception
     */
    @Test(expected = ProductNotRegisteredException.class)
    public void getStockCountInvalidBarcode() throws Exception {
        iShop.buyProduct("");
        iShop.buyProduct(null);
        iShop.buyProduct("THIS IS NOT A BARCODE...");
    }

    /**
     * This test registers products, updates their stock  by various amounts
     * and checks the attributes have updated appropriately and the method works.
     * @throws Exception exception
     */
    @Test
    public void getNumberOfSales() throws Exception {
        //Register 2 products and check their number od sales equals 0
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        assertEquals(iShop.getNumberOfSales(iProduct1.getBarCode()), 0);
        assertEquals(iShop.getNumberOfSales(iProduct2.getBarCode()), 0);
        //Add ONE_HUNDRED to product 1s stock count and then reduce it by 50 and check stock count and number of sales have been updates correctly
        for (int i  =  0; i < ONE_HUNDRED; i++) {
            iShop.addStock(iProduct1.getBarCode());
        }
        for (int i  =  0; i < FIFTY; i++) {
            iShop.buyProduct(iProduct1.getBarCode());
        }
        assertEquals(iShop.getNumberOfSales(iProduct1.getBarCode()), FIFTY);
        assertEquals(iShop.getStockCount(iProduct1.getBarCode()), FIFTY);
        assertEquals(iShop.getNumberOfSales(iProduct2.getBarCode()), 0);
        assertEquals(iShop.getStockCount(iProduct2.getBarCode()), 0);
        //Add 50 to product 2s stock count and then reduce it by TWENTY_FIVE and check stock count and number of sales have been updates correctly
        for (int i  =  0; i < FIFTY; i++) {
            iShop.addStock(iProduct2.getBarCode());
        }
        for (int i  =  0; i < TWENTY_FIVE; i++) {
            iShop.buyProduct(iProduct2.getBarCode());
            iShop.buyProduct(iProduct1.getBarCode());
        }
        assertEquals(iShop.getNumberOfSales(iProduct1.getBarCode()), SEVENTY_FIVE);
        assertEquals(iShop.getStockCount(iProduct1.getBarCode()), TWENTY_FIVE);
        assertEquals(iShop.getNumberOfSales(iProduct2.getBarCode()), TWENTY_FIVE);
        assertEquals(iShop.getStockCount(iProduct2.getBarCode()), TWENTY_FIVE);
    }

    /**
     * This test attempts to get the stock count for a product that hasn't been registered
     *  and is expected to throw "ProductNotRegisteredException" exception.
     * @throws Exception exception
     */
    @Test(expected = ProductNotRegisteredException.class)
    public void getNumberOfSalesInvalidBarcode() throws Exception {
        iShop.buyProduct("");
        iShop.buyProduct(null);
        iShop.buyProduct("THIS IS NOT A BARCODE...");
    }


    /**
     * This test will register 3 products, update the stock of all the products,
     * then update the number sold of all 3 products with different values
     * Product 2 has the most number of products sold and therefore is the most popular.
     * @throws Exception exception
     */
    @Test
    public void getMostPopular() throws Exception {
        //Register all 3 products and check that product 1 (first in list) comes out as most popular
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        iShop.registerProduct(iProduct3);
        iShop.getMostPopular();
        assertEquals(iShop.getMostPopular(), iProduct1);
        //Add stock to all 3 products
        for (int i  =  0; i < ONE_HUNDRED; i++) {
            iShop.addStock(iProduct1.getBarCode());
        }
        for (int i  =  0; i < ONE_HUNDRED_AND_FIFTY; i++) {
            iShop.addStock(iProduct2.getBarCode());
        }
        for (int i  =  0; i < 200; i++) {
            iShop.addStock(iProduct3.getBarCode());
        }
        //reduce stock of all 3 products
        for (int i  =  0; i < FIFTY; i++) {
            iShop.buyProduct(iProduct2.getBarCode());
        }
        assertEquals(iShop.getMostPopular(), iProduct2);
        for (int i  =  0; i < SEVENTY_FIVE; i++) {
            iShop.buyProduct(iProduct3.getBarCode());
        }
        assertEquals(iShop.getMostPopular(), iProduct3);
        for (int i  =  0; i < ONE_HUNDRED; i++) {
            iShop.buyProduct(iProduct3.getBarCode());
        }
        //Leave product 2 with the most sales
        assertEquals(iShop.getMostPopular(), iProduct3);
        for (int i  =  0; i < ONE_THOUSAND; i++) {
            iShop.addStock(iProduct2.getBarCode());
            iShop.buyProduct(iProduct2.getBarCode());
        }
        assertEquals(iShop.getMostPopular(), iProduct2);
    }

    /**
     * This test attempts to get the most popular product when no products are registered
     * and is expected to throw "ProductNotRegisteredException" exception.
     * @throws Exception exception
     */
    @Test(expected = ProductNotRegisteredException.class)
    public void getMostPopularNoProducts() throws Exception {
        iShop.getMostPopular();
    }

}
