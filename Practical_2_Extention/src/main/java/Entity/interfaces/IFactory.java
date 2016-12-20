package Entity.interfaces;


import Entity.impl.Customer;
import Entity.impl.CustomerOrder;

/**
 * Interface for a factory allowing the other Practical_2.interfaces to be instantiated without knowing the implementation classes.
 * 
 */
public interface IFactory {



    /**
     * Creates an instance of {@link IProduct}.
     * @param barCode the bar code of the product
     * @param description the description of the product
     * @return the product
     */
    IProduct makeProduct(String barCode, String description);


    /**
     * This method creates an instance of {@link IStockRecord} for a new product.
     * @param product the product to use for this stock record
     * @return the stick record
     */
    IStockRecord makeStockRecord(IProduct product);


    /**
     * Creates an instance of {@link IShop}.
     * @return the shop
     */
    IShop makeShop();

    /**
     * @param customer the customer that made the order
     * @param date the date the order was made
     * @return a new customer order object
     */
    ICustomerOrder makeCustomerOrder(ICustomer customer, String date);

    /**
     * @param name the customers name
     * @return a new customer object
     */
    ICustomer makeCustomer(String name);

    /**
     * @param product the product ordered
     * @param order the order the product belongs too
     * @return a new order line object
     */
    IOrderLine makeOrderLine(IProduct product, ICustomerOrder order);
}
