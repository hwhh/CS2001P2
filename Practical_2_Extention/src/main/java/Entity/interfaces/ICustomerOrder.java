package Entity.interfaces;

public interface ICustomerOrder {

    /**
     * @return the customer whom made the order
     */
    ICustomer getCustomer();

    /**
     * @return the date the order was made
     */
    String getDate();
}
