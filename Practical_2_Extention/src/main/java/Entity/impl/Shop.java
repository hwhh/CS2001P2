package Entity.impl;

import Entity.common.*;
import Entity.interfaces.*;
import ORM.Properties;
import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * This class represents a simple shop which can stok and sell products.
 */
public class Shop extends AbstractFactoryClient implements IShop {

    public EntityManager entityManager;

    public Shop() {
        Properties.setup();
        // This uses getPropertiesForTableAutoCreation: to create the tables for the first time
        Map<String, String> properties = Properties.getPropertiesForTableAutoCreation();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ShopService", properties);
        entityManager = factory.createEntityManager();
        // All additions/removals from the database are done between the 'begin()' and 'commit()' calls of a transaction
    }

    /**
     * @param product the product to register
     * @throws ProductIDAlreadyInUseException
     */
    @Override
    public void registerProduct(IProduct product) throws ProductIDAlreadyInUseException {
        Product productCheck = entityManager.find(Product.class, product.getProductID());
        if(productCheck == null) { //Check is product already exists in database
            //Add the product to the database
            entityManager.getTransaction().begin();
            IStockRecord iStockRecord = getFactory().makeStockRecord(product);
            entityManager.persist(product);
            entityManager.persist(iStockRecord);
            entityManager.getTransaction().commit();
        } else {
            throw new ProductIDAlreadyInUseException();
        }
    }

    /**
     * @param product the product to remove
     * @throws ProductNotRegisteredException
     */
    @Override
    public void unregisterProduct(IProduct product) throws ProductNotRegisteredException {
        IStockRecord stockRecordCheck = entityManager.find(StockRecord.class, product.getProductID());
        Product productCheck = entityManager.find(Product.class, product.getProductID());
        if(productCheck == null) { //Check is product already exists in database
            throw new ProductNotRegisteredException();
        } else {
            //Remove the product from database
            entityManager.getTransaction().begin();
            entityManager.remove(stockRecordCheck);
            entityManager.remove(productCheck);
            entityManager.getTransaction().commit();
        }
    }

    /**
     *
     * @param productID the bar code of the product
     * @throws ProductNotRegisteredException
     */
    @Override
    public void addStock(int productID, int amount) throws ProductNotRegisteredException {
        //find product from Stock Record and add more
        Product productCheck = entityManager.find(Product.class, productID);
        if (productCheck == null) { //Check is product already exists in database
            throw new ProductNotRegisteredException();
        } else {
            //Find the correct stock record row in the database and update it
            entityManager.getTransaction().begin();
            IStockRecord stockRecordCheck = entityManager.find(StockRecord.class, productCheck.getProductID());
            stockRecordCheck.addStock(amount);
            entityManager.merge(stockRecordCheck);
            entityManager.getTransaction().commit();
        }
    }

    /**
     *
     * @param productID the bar code of the product to be bought
     * @throws StockUnavailableException
     * @throws ProductNotRegisteredException
     */
    @Override
    public void buyProduct(int productID, int amount) throws StockUnavailableException, ProductNotRegisteredException {
        // minus x amount from products quantity
        Product productCheck = entityManager.find(Product.class, productID);
        if (productCheck == null) {//Check is product already exists in database
            throw new ProductNotRegisteredException();
        } else {
            IStockRecord stockRecordCheck = entityManager.find(StockRecord.class, productCheck.getProductID());
            if (stockRecordCheck.getStockCount() <= 0) { //Check if there is enough stock
                throw new StockUnavailableException();
            } else {
                //Find the correct stock record row in the database and update it
                entityManager.getTransaction().begin();
                stockRecordCheck.buyProduct(amount);
                entityManager.merge(stockRecordCheck);
                entityManager.getTransaction().commit();
            }
        }
    }


    @Override
    public int getNumberOfProducts() {
        //return size of list containing all products
        return  Integer.parseInt(String.valueOf(entityManager.createQuery("select count(*) from Product").getSingleResult()));
    }

    @Override
    public int getTotalStockCount() {
        //return the sum off all the products quantity
        return Integer.parseInt(String.valueOf(entityManager.createQuery("select sum(stockCount) from StockRecord ").getSingleResult()));
    }

    /**
     *
     * @param productID the bar code of the product
     * @return int - quantity of stock for given product
     * @throws ProductNotRegisteredException
     */
    @Override
    public int getStockCount(int productID) throws ProductNotRegisteredException {
       // entityManager.getTransaction().begin();
        return entityManager.find(StockRecord.class, productID).getStockCount();
    }

    /**
     *
     * @param productID the bar code of the product
     * @return int - quantity of sales for given product
     * @throws ProductNotRegisteredException
     */
    @Override
    public int getNumberOfSales(int productID) throws ProductNotRegisteredException {
        return entityManager.find(StockRecord.class, productID).getNumberOfSales();
    }

    /**
     *
     * @return IProduct - the product with the most sales
     * @throws ProductNotRegisteredException
     */
    @Override
    public IProduct getMostPopular() throws ProductNotRegisteredException  {
        //Find the stock record row with the most sales
        Query query = entityManager.createQuery("SELECT s FROM  StockRecord s " +
                "WHERE noOfSales = (SELECT MAX(noOfSales) FROM StockRecord)");
        StockRecord stockRecord = (StockRecord) query.getSingleResult();
        //Find the object that the found stock record is tied with
        IProduct iProduct = stockRecord.getProduct();
        return entityManager.find(Product.class, iProduct.getProductID());
    }

    /**
     * Add a new customer to database
     * @param iCustomer the customer to be registered
     * @throws CustomerAlreadyRegistered
     */
    @Override
    public void registerCustomer(ICustomer iCustomer) throws CustomerAlreadyRegistered {
        Customer customerCheck = entityManager.find(Customer.class, iCustomer.getCustomerID());
        if(customerCheck == null) {//Check if the customer already exists in database
            //Add the new customer to the database
            entityManager.getTransaction().begin();
            entityManager.persist(iCustomer);
            entityManager.getTransaction().commit();
        } else {
            throw new CustomerAlreadyRegistered();
        }
    }

    /**
     * Remove a customer from database
     * @param iCustomer the customer to be unregistered
     * @throws CustomerNotRegistered
     */
    @Override
    public void unregisterCustomer(ICustomer iCustomer) throws CustomerNotRegistered {
        Customer customerCheck = entityManager.find(Customer.class, iCustomer.getCustomerID());
        if(customerCheck == null) { //Check if the customer already exists in database
            throw new CustomerNotRegistered();
        } else {
            //Remove the customer from the database
            entityManager.getTransaction().begin();
            entityManager.remove(iCustomer);
            entityManager.getTransaction().commit();
        }
    }

    /**
     * Records which products have been purchased and what order the purchased products belong to
     * @param product the ordered product
     * @param order the order the product belongs too
     */
    @Override
    public void makeOrder(Product product, CustomerOrder order) {
        try{
            //Update the stock records for the given product
            buyProduct(product.getProductID(), 1);
            IOrderLine iOrderLine = getFactory().makeOrderLine(product, order);
            //Add the new orderline to the database
            entityManager.getTransaction().begin();
            entityManager.persist(iOrderLine);
            entityManager.getTransaction().commit();
        } catch (StockUnavailableException e) {
            e.printStackTrace();
        } catch (ProductNotRegisteredException e) {
            e.printStackTrace();
        }
    }

    /**
     *Add a record of the order to the database
     * @param products all the products in the order
     * @param customer the customer whom made the order
     * @param date the order was made
     * */
    @Override
    public void finalizeOrder(List<Product> products, ICustomer customer, String date) {
        //Add the new customer order to the database
        ICustomerOrder customerOrder = getFactory().makeCustomerOrder(customer, date);
        entityManager.getTransaction().begin();
        entityManager.persist(customerOrder);
        entityManager.getTransaction().commit();
        //Add all the order lines to the database
        for (Product product : products) {
            makeOrder(product, (CustomerOrder)customerOrder);
        }
    }

}
