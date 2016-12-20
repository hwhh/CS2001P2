package Entity.interfaces;

public interface IOrderLine {

    /**
     * @return an integer representing the order ID
     */
    int getOrderID();

    /**
     * @return a product in the order
     */
    IProduct getProduct();

    /**
     * @return the customer whom made the order
     */
    ICustomerOrder getCustomerOrder();
}
