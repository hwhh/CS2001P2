package Entity.impl;



import Entity.interfaces.*;


/**
 * This class implements a singleton factory.
 *
 */
public final class Factory implements IFactory {

    private static IFactory factoryInstance = null;

    /**
     * Method which returns an instance of the singleton Factory class.
     * @return the instance of the Factory
     */
    public static IFactory getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new Factory();
        }
        return factoryInstance;
    }

    /**
     *
     * @param barCode the bar code of the product
     * @param description the description of the product
     * @return a nde product object
     */

    @Override
    public IProduct makeProduct(String barCode, String description) {
        return new Product(barCode, description);
    }
    /**
     *
     * @param product the product to use for this stock record
     * @return a new stock record object
     */
    @Override
    public IStockRecord makeStockRecord(IProduct product) {
        return new StockRecord(product);
    }

    /**
     * @return a new shop object
     */
    @Override
    public IShop makeShop() {
        return new Shop();
    }

    /**
     * @param customer the customer that made the order
     * @param date the date the order was made
     * @return a new customer order object
     */
    @Override
    public ICustomerOrder makeCustomerOrder(ICustomer customer, String date) {
        return new CustomerOrder(customer, date);
    }

    /**
     * @param name the customers name
     * @return a new customer object
     */
    @Override
    public ICustomer makeCustomer(String name) {
        return new Customer(name);
    }

    /**
     *
     * @param product the product ordered
     * @param order the order the product belongs too
     * @return a new order line object
     */
    @Override
    public IOrderLine makeOrderLine(IProduct product, ICustomerOrder order) {
        return new OrderLine(product, order);
    }

}
