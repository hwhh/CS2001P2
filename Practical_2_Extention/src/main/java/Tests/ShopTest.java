package Tests;

import Entity.common.*;
import Entity.impl.*;
import Entity.interfaces.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShopTest extends AbstractFactoryClient {


    private IShop iShop;
    private IProduct iProduct1, iProduct2, iProduct3;
    private ICustomer iCustomer1, iCustomer2, iCustomer3;
    private ICustomerOrder iCustomerOrder1;

    /**
     * Executed before every test and creates the objects to be used in the tests
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        iShop = getFactory().makeShop();
        iProduct1 = getFactory().makeProduct("123", "toast");
        iProduct2 = getFactory().makeProduct("234", "bread");
        iProduct3 = getFactory().makeProduct("345", "raw toast");
        iCustomer1 = getFactory().makeCustomer("Bill");
        iCustomer2 = getFactory().makeCustomer("Ben");
        iCustomer3 = getFactory().makeCustomer("Jill");
        iCustomerOrder1 = null;

    }

    /**
     * Executed after every test and resets all the static ID variables the appropriate classes
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        Customer.resetID();
        CustomerOrder.resetID();
        OrderLine.resetID();
        StockRecord.resetID();
        Product.resetID();
    }

    /**
     * This test checks that a product is not already in the database, it
     * then registers the product and checks that the product now appears in the database
     * @throws Exception
     */
    @Test
    public void registerProduct() throws Exception {
        IProduct temp = ((Shop) iShop).entityManager.find(Product.class, iProduct1.getProductID());
        assertNull(temp);
        iShop.registerProduct(iProduct1);
        temp = ((Shop) iShop).entityManager.find(Product.class, iProduct1.getProductID());
        Assert.assertEquals(iProduct1, temp);
    }

    /**
     * This test attempts to register the same product twice
     *  and is expected to throw "ProductIDAlreadyInUseException" exception
     * @throws Exception
     */
    @Test(expected = ProductIDAlreadyInUseException.class)
    public void registerProductException() throws Exception {
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct1);
    }

    /**
     * This test registers a product then checks the product appears in the database
     * It then unregisters the product and checks the product no longer appears in the database
     * @throws Exception
     */
    @Test
    public void unregisterProduct() throws Exception {
        iShop.registerProduct(iProduct1);
        IProduct temp = ((Shop) iShop).entityManager.find(Product.class, iProduct1.getProductID());
        assertEquals(iProduct1, temp);
        iShop.unregisterProduct(iProduct1);
        temp = ((Shop) iShop).entityManager.find(Product.class, iProduct1.getProductID());
        assertNull(temp);
    }

    /**
     * This test attempts to unregister a product that hasn't been registered
     *  and is expected to throw "ProductNotRegisteredException" exception
     * @throws Exception
     */
    @Test(expected = ProductNotRegisteredException.class)
    public void unregisterProductException() throws Exception {
        iShop.unregisterProduct(iProduct1);
    }

    /**
     * This test registers a product then checks the number of sales for the product is 0,
     * next it adds 100 to the stock and checks the database has updates appropriately
     * @throws Exception
     */
    @Test
    public void addStock() throws Exception {
        iShop.registerProduct(iProduct1);
        IStockRecord iStockRecord1 = ((Shop) iShop).entityManager.find(StockRecord.class, iProduct1.getProductID());
        assertEquals(iStockRecord1.getStockCount(), 0);
        iShop.addStock(iProduct1.getProductID(), 100);
        assertEquals(iStockRecord1.getStockCount(), 100);
    }

    /**
     * This test attempts to update the stock for product that hasn't been registered
     *  and is expected to throw "ProductNotRegisteredException" exception
     * @throws Exception
     */
    @Test(expected = ProductNotRegisteredException.class)
    public void addProductNotRegisteredException() throws Exception {
        iShop.addStock(iProduct1.getProductID(), 100);
    }

    /**
     * This test registers a product then checks the number of sales for the product is 0,
     * next it adds 100 to the stock and then adds 50 to the number of sold. Finally it will
     * check the database has updates appropriately
     * @throws Exception
     */
    @Test
    public void buyProduct() throws Exception {
        iShop.registerProduct(iProduct1);
        IStockRecord iStockRecord1 = ((Shop) iShop).entityManager.find(StockRecord.class, iProduct1.getProductID());
        assertEquals(iStockRecord1.getNumberOfSales(), 0);
        iShop.addStock(iProduct1.getProductID(), 100);
        iShop.buyProduct(iProduct1.getProductID(), 50);
        assertEquals(iStockRecord1.getNumberOfSales(), 50);
    }

    /**
     * This test updates the stock of a product to 100 then attempts to purchase
     * 101 of the product and is expected to throw "StockUnavailableException" exception
     * @throws Exception
     */
    @Test(expected = StockUnavailableException.class)
    public void buyProductStockUnavailableException() throws Exception {
        iShop.registerProduct(iProduct1);
        iShop.addStock(iProduct1.getProductID(), 100);
        iShop.buyProduct(iProduct1.getProductID(), 101);
    }

    /**
     * This test attempts to buy a product that hasn't been registered
     *  and is expected to throw "ProductNotRegisteredException" exception
     * @throws Exception
     */
    @Test(expected = ProductNotRegisteredException.class)
    public void buyProductProductNotRegisteredException() throws Exception {
        iShop.buyProduct(iProduct1.getProductID(), 100);
    }

    /**
     * This test first checks no products are in the database, it then adds 1 product to
     * the database and checks it's there and finally registers 2 more products
     * and checks the database all 3 products and SQL query return the right values
     * @throws Exception
     */
    @Test
    public void getNumberOfProducts() throws Exception {
        assertEquals(iShop.getNumberOfProducts(), 0);
        iShop.registerProduct(iProduct1);
        assertEquals(iShop.getNumberOfProducts(), 1);
        iShop.registerProduct(iProduct2);
        iShop.registerProduct(iProduct3);
        assertEquals(iShop.getNumberOfProducts(), 3);
    }

    /**
     * This test registers a product, updates it's stock by 10 and checks the
     * database has updated appropriately and SQL query return the right values.
     * It then registers another product and updates the second products stock by 20 and again
     * checks the database has updated appropriately and SQL query return the right values
     * @throws Exception
     */
    @Test
    public void getTotalStockCount() throws Exception {
        iShop.registerProduct(iProduct1);
        iShop.addStock(iProduct1.getProductID(), 10);
        assertEquals(iShop.getTotalStockCount(), 10);
        iShop.registerProduct(iProduct2);
        iShop.addStock(iProduct2.getProductID(), 20);
        assertEquals(iShop.getTotalStockCount(), 30);
    }

    /**
     * This test registers a product, updates the stock  by 10.
     * Finally it checks the database has updated appropriately and SQL query return the right values
     * @throws Exception
     */
    @Test
    public void getStockCount() throws Exception {
        iShop.registerProduct(iProduct1);
        iShop.addStock(iProduct1.getProductID(), 10);
        assertEquals(iShop.getStockCount(iProduct1.getProductID()), 10);
    }

    /**
     * This test registers a product, updates the stock  by 100 and then updates the number sold by 50.
     * Finally it checks the database has updated appropriately and SQL query return the right values
     * @throws Exception
     */
    @Test
    public void getNumberOfSales() throws Exception {
        iShop.registerProduct(iProduct1);
        assertEquals(iShop.getNumberOfSales(iProduct1.getProductID()), 0);
        iShop.addStock(iProduct1.getProductID(), 100);
        iShop.buyProduct(iProduct1.getProductID(), 50);
        assertEquals(iShop.getNumberOfSales(iProduct1.getProductID()), 50);
    }

    /**
     * This test will register 3 products, update the stock of all the products,
     * then update the number sold of all 3 products with different values
     * Product 3 has the most number of products sold and therefore is the most popular.
     * This test checks the database has updated appropriately and SQL query return the right values
      * @throws Exception
     */
    @Test
    public void getMostPopular() throws Exception {
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        iShop.registerProduct(iProduct3);
        iShop.addStock(iProduct1.getProductID(), 100);
        iShop.addStock(iProduct2.getProductID(), 100);
        iShop.addStock(iProduct3.getProductID(), 100);
        iShop.buyProduct(iProduct1.getProductID(), 50);
        iShop.buyProduct(iProduct2.getProductID(), 75);
        iShop.buyProduct(iProduct3.getProductID(), 100);
        assertEquals(iShop.getMostPopular(), iProduct3);
    }

    /**
     * This test check that a customer is not already in the database it
     * then registers the same customer and checks that the customer now appears in the database
     * @throws Exception
     */
    @Test
    public void registerCustomer() throws Exception {
        ICustomer temp = ((Shop) iShop).entityManager.find(Customer.class, iCustomer1.getCustomerID());
        assertNull(temp);
        iShop.registerCustomer(iCustomer1);
        temp = ((Shop) iShop).entityManager.find(Customer.class, iCustomer1.getCustomerID());
        assertEquals(iCustomer1, temp);
    }

    /**
     * This test attempts to register the same customer twice
     *  and is expected to throw "CustomerAlreadyRegistered" exception
     * @throws Exception
     */
    @Test(expected = CustomerAlreadyRegistered.class)
    public void registerCustomerException() throws Exception {
        iShop.registerCustomer(iCustomer1);
        iShop.registerCustomer(iCustomer1);
    }

    /**
     * This test registers a customer then checks the customer appears in the database
     * It then unregisters the customer and checks the customer no longer appears in the database
     * @throws Exception
     */
    @Test
    public void unregisterCustomer() throws Exception {
        iShop.registerCustomer(iCustomer1);
        ICustomer temp = ((Shop) iShop).entityManager.find(Customer.class, iCustomer1.getCustomerID());
        assertEquals(iCustomer1, temp);
        iShop.unregisterCustomer(iCustomer1);
        temp = ((Shop) iShop).entityManager.find(Customer.class, iCustomer1.getCustomerID());
        assertNull(temp);
    }

    /**
     * This test attempts to unregister a customer that hasn't been registered
     *  and is expected to throw "CustomerNotRegistered" exception
     * @throws Exception
     */
    @Test(expected = CustomerNotRegistered.class)
    public void unregisterCustomerException() throws Exception {
        iShop.unregisterCustomer(iCustomer1);
    }

    /**
     * Because the makeOrder method is only called by the finalise order method, this method sets up
     * a new customer order which can be used by both the methods makeOrder() and finalizeOrder()
     * @throws Exception
     */
    private void orderSetup() throws Exception{
        iShop.registerProduct(iProduct1);
        iShop.registerProduct(iProduct2);
        iShop.registerProduct(iProduct3);
        iShop.addStock(iProduct1.getProductID(), 100);
        iShop.addStock(iProduct2.getProductID(), 100);
        iShop.addStock(iProduct3.getProductID(), 100);
        iShop.registerCustomer(iCustomer1);
        List<Product> products = new ArrayList<>();
        products.add((Product) iProduct1);
        products.add((Product) iProduct2);
        products.add((Product) iProduct3);
        iShop.finalizeOrder(products, iCustomer1, "27/09/2016");
        iCustomerOrder1 = ((Shop) iShop).entityManager.find(CustomerOrder.class, 1);
    }

    /**
     *This test checks that the linking table between orders and products contains the correct values
     * First of all it will check that the correct number of entity's have been added to the correct table
     * Then it will check that the entity's contain the correct ID, products and customer(s)
     * @throws Exception
     */
    @Test
    public void makeOrder() throws Exception {
        orderSetup();
        IOrderLine orderLine1 = ((Shop) iShop).entityManager.find(OrderLine.class, 1);
        IOrderLine orderLine2 = ((Shop) iShop).entityManager.find(OrderLine.class, 2);
        IOrderLine orderLine3 = ((Shop) iShop).entityManager.find(OrderLine.class, 3);
        IOrderLine orderLine4 = ((Shop) iShop).entityManager.find(OrderLine.class, 4);
        assertEquals(orderLine1.getProduct(), iProduct1);
        assertEquals(orderLine2.getProduct(), iProduct2);
        assertEquals(orderLine3.getProduct(), iProduct3);
        assertEquals(orderLine1.getOrderID(), 1);
        assertEquals(orderLine2.getOrderID(), 2);
        assertEquals(orderLine3.getOrderID(), 3);
        assertEquals(orderLine1.getCustomerOrder(), iCustomerOrder1);
        assertEquals(orderLine2.getCustomerOrder(), iCustomerOrder1);
        assertEquals(orderLine3.getCustomerOrder(), iCustomerOrder1);
        assertNull(orderLine4);
    }

    /**
     *This test checks the finalise order successfully adds a customer order to the database
     *and the entity added to the database has the correct customer, date and order total
     * @throws Exception
     */
    @Test
    public void finalizeOrder() throws Exception {
        orderSetup();
        assertEquals(iCustomerOrder1.getCustomer(), iCustomer1);
        assertEquals(iCustomerOrder1.getDate(), "27/09/2016");
    }
}

