package Entity.interfaces;

import Entity.common.*;
import Entity.impl.CustomerOrder;
import Entity.impl.OrderLine;
import Entity.impl.Product;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Interface for a simple shop ADT.
 * 
 */
public interface IShop {

    /**
     * Registers the specified product for sale in the shop.
     * @param product the product to register
     * @throws ProductIDAlreadyInUseException if a product is already registered in the shop with matching bar code
     */
    void registerProduct(IProduct product) throws ProductIDAlreadyInUseException;


    /**
     * Unregisters the specified product from the shop.
     * @param product the product to remove
     * @throws ProductNotRegisteredException if the product has not been registered in the shop
     */
    void unregisterProduct(IProduct product) throws ProductNotRegisteredException;


    /**
     * Adds stock for the product with given bar code.
     * @param productID the bar code of the product
     * @param  amount of stock to add
     * @throws ProductNotRegisteredException if the product does not exist in the shop
     */
    void addStock(int productID, int amount) throws ProductNotRegisteredException;


    /**
     * Buys the product with given bar code from the shop.
     * @param productID the bar code of the product to be bought
     * @throws StockUnavailableException if the product is not currently in stock
     * @throws ProductNotRegisteredException if the product does not exist in the shop
     */
    void buyProduct(int productID, int amount) throws StockUnavailableException, ProductNotRegisteredException;


    /**
     * Gets the number of different products currently available in the shop. Multiple copies of the same product
     * only count once.
     * @return the number of different products available in the shop
     */
    int getNumberOfProducts();


    /**
     * Gets the total count of stock over all products in the shop. For example, returns 3 if
     * the shop has 2 cans of baked beans and 1 loaf of bread in stock.
     * @return the total stock count over all products
     */
    int getTotalStockCount();


    /**
     * Gets the stock count for a particular product.
     * @param productID the bar code of the product
     * @return the stock count for a particular product
     * @throws ProductNotRegisteredException if the product does not exist in the shop
     */
    int getStockCount(int productID) throws ProductNotRegisteredException;


    /**
     * Gets the total number of times that a given product was bought.
     * @param productID the bar code of the product
     * @return the total number of times that the product has been bought
     * @throws ProductNotRegisteredException if the product does not exist in the shop
     */
    int getNumberOfSales(int productID) throws ProductNotRegisteredException;


    /**
     * Gets the product that has been bought the greatest number of times. The behaviour
     * is undefined if there is not a single most popular product.
     * @return the product that has been bought the greatest number of times
     * @throws ProductNotRegisteredException if no products are registered in the shop
     */
    IProduct getMostPopular() throws ProductNotRegisteredException;

    /**
     * Add a new customer to database
     * @param iCustomer the customer to be registered
     * @throws CustomerAlreadyRegistered
     */
    void registerCustomer(ICustomer iCustomer) throws CustomerAlreadyRegistered;

    /**
     * Remove a customer from database
     * @param iCustomer the customer to be unregistered
     * @throws CustomerNotRegistered
     */
    void unregisterCustomer(ICustomer iCustomer) throws CustomerNotRegistered;

    /**
     * Records which products have been purchased and what order the purchased products belong to
     * @param product the ordered product
     * @param order the order the product belongs too
     */
    void makeOrder(Product product, CustomerOrder order);

    /**
     *Add a record of the order to the database
     * @param products all the products in the order
     * @param customer the customer whom made the order
     * @param date the order was made
     * */
    void finalizeOrder(List<Product> products, ICustomer customer, String date);

}
